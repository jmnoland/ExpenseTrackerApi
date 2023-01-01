package com.jmnoland.expensetrackerapi.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("categories")
public class Category {

    @Id
    private final String categoryId;
    private final String clientId;
    private String name;
    private final Date createdAt;

    public Category(String categoryId, String clientId, String name, Date createdAt) {
        this.categoryId = categoryId;
        this.clientId = clientId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
