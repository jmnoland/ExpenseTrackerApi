package com.jmnoland.expensetrackerapi.models.dtos;

import java.util.Date;

public class CategoryDto {

    public String categoryId;
    public String userId;
    public String name;
    public Date createdAt;

    public CategoryDto(String categoryId, String userId, String name, Date createdAt) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.name = name;
        this.createdAt = createdAt;
    }
}
