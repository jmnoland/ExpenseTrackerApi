package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.database.mongodb.CategoryDAO;
import com.jmnoland.expensetrackerapi.interfaces.repositories.CategoryRepositoryInterface;
import com.jmnoland.expensetrackerapi.models.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository implements CategoryRepositoryInterface {

    private final CategoryDAO categoryDAO;

    @Autowired
    public CategoryRepository(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }

    @Override
    public boolean categoryExists(String categoryId) {
        return false;
    }

    @Override
    public void insert(Category category) {

    }

    @Override
    public void update(Category category) {

    }

    @Override
    public void delete(Category category) {

    }
}
