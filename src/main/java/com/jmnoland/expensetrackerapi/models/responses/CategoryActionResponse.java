package com.jmnoland.expensetrackerapi.models.responses;

import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.mongodb.lang.Nullable;

import java.util.Date;
import java.util.List;

public class CategoryActionResponse {
    public String categoryId;
    public String clientId;
    public String name;
    @Nullable
    public Date createdAt;

    public List<ValidationError> validationErrors;
}
