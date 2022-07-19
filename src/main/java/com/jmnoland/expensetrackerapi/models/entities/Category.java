package com.jmnoland.expensetrackerapi.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("categories")
public class Category {

    @Id
    private final String categoryId;
    private String name;
    private final Date createdAt;

    public String getCategoryId() {
        return categoryId;
    }
    public String getName() {
        return name;
    }
    public Date getCreatedAt() { return createdAt; }

    public void setName( String name) {
        this.name = name;
    }

    public Category(String categoryId, String name, Date createdAt) {
        this.categoryId = categoryId;
        this.name = name;
        this.createdAt = createdAt;
    }
}
