package com.jmnoland.expensetrackerapi.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("categories")
public class Category {

    @Id
    private final String categoryId;
    private final String userId;
    private String name;
    private final Date createdAt;

    public String getCategoryId() {
        return categoryId;
    }
    public String getName() {
        return name;
    }
    public Date getCreatedAt() { return createdAt; }
    public String getUserId() { return userId; }

    public void setName( String name) {
        this.name = name;
    }

    public Category(String categoryId, String userId, String name, Date createdAt) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.name = name;
        this.createdAt = createdAt;
    }
}
