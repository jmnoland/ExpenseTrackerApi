package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.Category;

import java.util.List;

public interface CategoryRepositoryInterface {


    List<Category> getAllCategoriesByClientId(String clientId);

    boolean categoryExists(String categoryId);

    void insert(Category category);

    void update(Category category);

    void delete(Category category);
}
