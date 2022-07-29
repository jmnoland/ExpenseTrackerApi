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

    public List<Category> getAllCategoriesByUserId(String userId) {
        return this.categoryDAO.findCategoriesByUserId(userId);
    }

    public boolean categoryExists(String categoryId) {
        return this.categoryDAO.existsById(categoryId);
    }

    public void insert(Category category) {
        this.categoryDAO.insert(category);
    }

    public void update(Category category) {
        this.categoryDAO.save(category);
    }

    public void delete(Category category) {
        this.categoryDAO.delete(category);
    }
}
