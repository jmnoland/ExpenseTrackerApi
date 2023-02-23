package com.jmnoland.expensetrackerapi.models.dtos;

import com.mongodb.lang.Nullable;

import java.util.Date;

public class CategoryDto {

    public String categoryId;
    public String clientId;
    public String name;
    @Nullable
    public Date createdAt;

    public CategoryDto(String categoryId, String clientId, String name, @Nullable Date createdAt) {
        this.categoryId = categoryId;
        this.clientId = clientId;
        this.name = name;
        this.createdAt = createdAt;
    }
}
