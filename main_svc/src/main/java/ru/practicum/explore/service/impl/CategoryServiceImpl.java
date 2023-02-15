package ru.practicum.explore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.dto.CategoryDto;
import ru.practicum.explore.exceptions.EntityDoesNotExistsException;
import ru.practicum.explore.mappers.CategoryMapper;
import ru.practicum.explore.model.Category;
import ru.practicum.explore.repository.CategoryRepository;
import ru.practicum.explore.service.CategoryService;

import java.util.List;

@Service
@Slf4j
@Primary
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        log.debug("Add category request {} received in service {}", categoryDto, this.getClass());
        Category category = categoryMapper.toEntity(categoryDto);
        categoryRepository.save(category);
        CategoryDto createdCategoryDto = categoryMapper.toDto(category);
        log.debug("Add category request {} processed successfully in service {}", categoryDto, this.getClass());
        return createdCategoryDto;
    }

    @Override
    public void deleteCategory(Long catId) {
        log.debug("Delete category request {} received in service {}", catId, this.getClass());
        categoryRepository.deleteById(catId);
        log.debug("Delete category request {} processed successfully in service {}", catId, this.getClass());
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        log.debug("Update category request {} received in service {}", categoryDto, this.getClass());
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new EntityDoesNotExistsException(Category.class, catId));
        category.setName(categoryDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        CategoryDto updatedCategoryDto = categoryMapper.toDto(updatedCategory);
        log.debug("Update category request {} processed successfully in service {}", categoryDto, this.getClass());
        return updatedCategoryDto;
    }

    @Override
    public List<CategoryDto> getAllCategories(Integer from, Integer size) {
        log.debug("Get categories request received in service {}", this.getClass());
        Pageable page = PageRequest.of(from / size, size);
        List<Category> categories = categoryRepository.findAll(page).toList();
        List<CategoryDto> categoryDtos = categoryMapper.toDto(categories);
        log.debug("Get categories request processed in service {}", this.getClass());
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        log.debug("Get categories request received in service {}", this.getClass());
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new EntityDoesNotExistsException(Category.class, catId));
        CategoryDto categoryDto = categoryMapper.toDto(category);
        log.debug("Get categories request processed in service {}", this.getClass());
        return categoryDto;
    }

    public Category map(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new EntityDoesNotExistsException(Category.class, catId));
    }
}
