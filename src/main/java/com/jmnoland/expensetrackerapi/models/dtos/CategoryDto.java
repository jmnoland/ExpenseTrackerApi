package com.jmnoland.expensetrackerapi.models.dtos;

import java.util.Date;

public class CategoryDto {

    public String categoryId;
    public String clientId;
    public String name;
    public Date createdAt;

    public CategoryDto(String categoryId, String clientId, String name, Date createdAt) {
        this.categoryId = categoryId;
        this.clientId = clientId;
        this.name = name;
        this.createdAt = createdAt;
    }
}
