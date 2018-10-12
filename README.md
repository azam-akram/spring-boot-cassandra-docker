# spring-boot-cassandra-docker

This projects demonstrates how to setup the Spring Boot Microservice, store books information in Cassandra (NoSQL) database, and expose REST APIS to interact with the service.  
### Frameworks

* **Spring Boot:** "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run"." Read (https://spring.io/projects/spring-boot)
* **Apache Zookeeper:** "ZooKeeper is a centralized service for maintaining configuration information, naming, providing distributed synchronization, and providing group services." (https://zookeeper.apache.org/)
* **Docker-compose:** Docker Compose is a tool for defining and running multi-container Docker applications. Read more (https://docs.docker.com/compose/)

### Prerequisites

I used docker-compose to run Cassandra and Zookeeper containers, so no need to install them locally.

```docker-compose
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
    - "2181:2181"
  cassandra:
    image: cassandra:3.11.1
    ports:
    - "9042:9042"
    - "9160:9160"
    - "7199:7199"
```

### Walk through the code
Spring Boot data provides cassandra configuration properties,
```yml
server:
  servlet:
    context-path: /bookstore/${application.api.version}
  port: 8089

application:
  api:
    version: v1

spring:
  data:
    cassandra:
      port: 9042
      contact-points: 127.0.0.1
      keyspace-name: book_store
      schema-action: create_if_not_exists
      user-name: cassandra
      password: cassandra
      entity-base-package: com.demo.spring.boot.cassandra.docker.repository
```

Need to configure Cassandra,
```java
@Configuration
public class BookStoreCassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.port}")
    private int port;
    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;
    @Value("${spring.data.cassandra.keyspace-name}")
    private String keySpace;
    @Value("${spring.data.cassandra.user-name}")
    private String userName;
    @Value("${spring.data.cassandra.password}")
    private String password;
    @Value("${spring.data.cassandra.entity-base-package}")
    private String entityBasePackage;
    
    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }
    
    @Override
    public String[] getEntityBasePackages() {
        return new String[] {
                entityBasePackage
        };
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Bean
    public CassandraMappingContext mappingContext() {
        return new CassandraMappingContext();
    }

    @Bean
    public CassandraConverter converter() {
        return new MappingCassandraConverter(mappingContext());
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Collections.singletonList(CreateKeyspaceSpecification
                .createKeyspace(keySpace)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication());
    }

    @Bean
    @Override
    public CassandraCqlClusterFactoryBean cluster() {
        CassandraCqlClusterFactoryBean bean = new CassandraCqlClusterFactoryBean();
        bean.setKeyspaceCreations(getKeyspaceCreations());
        bean.setContactPoints(contactPoints);
        bean.setUsername(userName);
        bean.setPassword(password);
        return bean;
    }
}
```

BookStoreEntity class is a simple POJO,

```java
@Table("book_store_entity")
public class BookStoreEntity {

    @PrimaryKeyColumn(name = "uuid", type = PrimaryKeyType.PARTITIONED)
    private UUID uuid;

    @Column(value = "publishing_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime publishingDate;

    @Column(value = "title")
    private String title;

    @Column(value = "writer")
    private String writer;
}
```

Cassandra Repository

```java
@Repository
public interface BookStoreRepository extends CassandraRepository<BookStoreEntity, UUID> {
    Optional<BookStoreEntity> findByUuid(UUID uuid);
}   
```

Controller to expose REST APIs,
```java
@Slf4j
@RestController
@RequestMapping(path = "/books")
public class BookStoreController {

    @Autowired
    BookStoreService bookStoreService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getAllBooks() {
        log.trace("Reading all books");
        try {
            return ok(bookStoreService.getAllBookStores());
        } catch (Exception e) {
            log.error("Catch exception when reading books records.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getBookById(@PathVariable(value = "uuid") final UUID uuid) {
        log.trace("Reading book information by Id");
        Optional<BookStoreEntity> optionalBookStoreEntity = bookStoreService.getBookStoreById(uuid);
        if (optionalBookStoreEntity.isPresent()) {
            return new ResponseEntity<>(bookStoreService.getBookStoreById(uuid), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveBook(@Valid @RequestBody final BookStoreEntity bookStoreEntity) {
        log.trace("Saving book");
        return new ResponseEntity<>(bookStoreService.saveBookStore(bookStoreEntity), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateBook(@Valid @RequestBody final BookStoreEntity bookStoreEntity) {
        log.trace("Update book");
        return new ResponseEntity<>(bookStoreService.updateBookStore(bookStoreEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "uuid") final UUID uuid) {
        log.trace("Update book");
        bookStoreService.deleteBookStore(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
```

### Running the application

If you want to test application locally then run the docker compose by,

First run the docker compose

```bash
docker-compose up -d
```

Then run the BookStoreApplication to exchange the message.

Stop docker-compose (once you are done)
```bash
docker-compose down
```

### Dockerizing the application

In order to dockerize this application we need to add gradle-docker dependency in build.gradle file,

```gradle
buildscript {
    ext {
        springBootVersion = '2.0.5.RELEASE'
        gradleDockerVersion   = "1.2"
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
        // Dependency for docker
        classpath("se.transmode.gradle:gradle-docker:${gradleDockerVersion}")
    }
}
```

and docker plugin

```gradle
apply plugin: 'docker'
```
add Dockerfile

```Dockerfile
FROM java:8
EXPOSE 5557
VOLUME /tmp
ADD spring-boot-cassandra-docker.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
```

I added a gradle task in build.gradle file

```gradle
group 'spring-boot-cassandra-docker' // used for tag name of the image

// build docker image
task buildDocker(type: Docker, dependsOn: build) {
    push = false
    applicationName = jar.baseName
    dockerfile = file('Dockerfile')
    doFirst {
        copy {
            from jar
            into stageDir
        }
    }
}
```
just build image by running
```bash
gradlew build buildDocker
```

and now all set to run the docker image,
```
docker run -i spring-boot-cassandra-docker/spring-boot-cassandra-docker:1.0-SNAPSHOT
```
