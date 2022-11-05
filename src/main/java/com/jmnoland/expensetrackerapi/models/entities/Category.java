package com.jmnoland.expensetrackerapi.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document("categories")
public class Category {

    @Id
    private final String categoryId;
    private final String userId;
    private String name;
    private final Date createdAt;

    public Category(String categoryId, String userId, String name, Date createdAt) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.name = name;
        this.createdAt = createdAt;
    }
}
