package ru.practicum.explore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.dto.CategoryDto;
import ru.practicum.explore.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/categories")
@Slf4j
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.debug("Add category request {} received in controller {}", categoryDto, this.getClass());
        CategoryDto createdCategoryDto = categoryService.addCategory(categoryDto);
        log.debug("Add category request {} processed successfully in controller {}", categoryDto, this.getClass());
        return createdCategoryDto;
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        log.debug("Delete category request {} received in controller {}", catId, this.getClass());
        categoryService.deleteCategory(catId);
        log.debug("Delete category request {} processed successfully in controller {}", catId, this.getClass());
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable Long catId, @Valid @RequestBody CategoryDto categoryDto) {
        log.debug("Update category request {} received in controller {}", categoryDto, this.getClass());
        CategoryDto updatedCategoryDto = categoryService.updateCategory(catId, categoryDto);
        log.debug("Update category request {} processed successfully in controller {}", categoryDto, this.getClass());
        return updatedCategoryDto;
    }
}
