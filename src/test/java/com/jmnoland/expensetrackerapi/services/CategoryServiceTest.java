package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.CategoryRepositoryInterface;
import com.jmnoland.expensetrackerapi.mapping.CategoryMapper;
import com.jmnoland.expensetrackerapi.models.dtos.CategoryDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.entities.Category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    private CategoryService classUnderTest;
    @Mock
    private CategoryRepositoryInterface categoryRepositoryInterface;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private DateProviderInterface dateProviderInterface;

    @Before
    public void Setup() {
        classUnderTest = new CategoryService(categoryRepositoryInterface, categoryMapper, dateProviderInterface);
    }

    // insert tests
    @Test
    public void InsertCategory_ShouldNotBeSuccessful_WhenTheCategoryExists() {
        List<Category> existing = new ArrayList<>();
        existing.add(new Category("1", "1", "test", new Date(1)));
        CategoryDto request = new CategoryDto("1", "1", "test", null);
        Category outputCategory = CategoryMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepositoryInterface.getAllCategoriesByClientId(request.clientId)).thenReturn(existing);

        ServiceResponse<CategoryDto> response = classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(categoryRepositoryInterface, never()).insert(outputCategory);
    }
    @Test
    public void InsertCategory_ShouldNotBeSuccessful_WhenCategoryNameIsNULL() {
        List<Category> existing = new ArrayList<>();
        CategoryDto request = new CategoryDto("1", "1", null, null);
        Category outputCategory = CategoryMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepositoryInterface.getAllCategoriesByClientId(request.clientId)).thenReturn(existing);

        ServiceResponse<CategoryDto> response = classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(categoryRepositoryInterface, never()).insert(outputCategory);
    }
    @Test
    public void InsertCategory_ShouldBeSuccessful_WhenCategoryDoesNotExist() {
        List<Category> existing = new ArrayList<>();
        CategoryDto request = new CategoryDto("1", "1", "test", null);
        Category outputCategory = CategoryMapper.INSTANCE.dtoToEntity(request);
        when(categoryMapper.dtoToEntity(request)).thenReturn(outputCategory);
        when(categoryRepositoryInterface.getAllCategoriesByClientId(request.clientId)).thenReturn(existing);

        ServiceResponse<CategoryDto> response = classUnderTest.insert(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(0, response.validationErrors.size());
        verify(categoryRepositoryInterface, times(1)).insert(outputCategory);
    }

    // update tests
    @Test
    public void UpdateCategory_ShouldNotBeSuccessful_WhenTheCategoryNameAlreadyExists() {
        List<Category> existing = new ArrayList<>();
        existing.add(new Category("1", "1", "test", new Date(1)));
        CategoryDto request = new CategoryDto("1", "1", "test", null);
        Category outputCategory = CategoryMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepositoryInterface.getAllCategoriesByClientId(request.clientId)).thenReturn(existing);

        ServiceResponse<CategoryDto> response = classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(categoryRepositoryInterface, never()).update(outputCategory);
    }
    @Test
    public void UpdateCategory_ShouldNotBeSuccessful_WhenCategoryNameIsNULL() {
        List<Category> existing = new ArrayList<>();
        existing.add(new Category("1", "1", "test", new Date(1)));
        CategoryDto request = new CategoryDto("1", "1", null, null);
        Category outputCategory = CategoryMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepositoryInterface.getAllCategoriesByClientId(request.clientId)).thenReturn(existing);

        ServiceResponse<CategoryDto> response = classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(categoryRepositoryInterface, never()).update(outputCategory);
    }
    @Test
    public void UpdateCategory_ShouldBeSuccessful_WhenCategoryIsValid() {
        List<Category> existing = new ArrayList<>();
        existing.add(new Category("1", "1", "test", new Date(1)));
        CategoryDto request = new CategoryDto("1", "1", "test2", null);
        Category outputCategory = CategoryMapper.INSTANCE.dtoToEntity(request);
        when(categoryMapper.dtoToEntity(request)).thenReturn(outputCategory);
        when(categoryRepositoryInterface.getAllCategoriesByClientId(request.clientId)).thenReturn(existing);

        ServiceResponse<CategoryDto> response = classUnderTest.update(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(0, response.validationErrors.size());
        verify(categoryRepositoryInterface, times(1)).update(outputCategory);
    }
    @Test
    public void UpdateCategory_ShouldNotBeSuccessful_WhenCategoryDoesNotExist() {
        List<Category> existing = new ArrayList<>();
        CategoryDto request = new CategoryDto("1", "1", "test", null);
        Category outputCategory = CategoryMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepositoryInterface.getAllCategoriesByClientId(request.clientId)).thenReturn(existing);

        ServiceResponse<CategoryDto> response = classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(categoryRepositoryInterface, never()).update(outputCategory);
    }
}
