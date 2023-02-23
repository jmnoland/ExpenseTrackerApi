package com.jmnoland.expensetrackerapi.models.requests;

import com.mongodb.lang.Nullable;

public class CreateUpdateCategoryRequest {
    public String name;
    @Nullable
    public String categoryId;
}
