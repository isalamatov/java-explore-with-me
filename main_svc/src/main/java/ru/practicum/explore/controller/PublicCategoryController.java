package ru.practicum.explore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.dto.CategoryDto;
import ru.practicum.explore.service.CategoryService;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Slf4j
@RequiredArgsConstructor
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAllCategories(@RequestParam(required = false, defaultValue = "0")
                                              @PositiveOrZero Integer from,
                                              @RequestParam(required = false, defaultValue = "25")
                                              @PositiveOrZero Integer size) {
        log.debug("Get categories request received in controller {}", this.getClass());
        List<CategoryDto> categoryDtos = categoryService.getAllCategories(from, size);
        log.debug("Get categories request processed in controller {}", this.getClass());
        return categoryDtos;
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        log.debug("Get categories request received in controller {}", this.getClass());
        CategoryDto categoryDto = categoryService.getCategoryById(catId);
        log.debug("Get categories request processed in controller {}", this.getClass());
        return categoryDto;
    }
}
