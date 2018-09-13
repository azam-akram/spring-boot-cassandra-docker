package com.demo.spring.boot.cassandra.docker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Table("book_store_entity")
public class BookStoreEntity {

    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
    private Integer id;

    @Column("title")
    private String title;

    @Column("writer")
    private String writer;
}