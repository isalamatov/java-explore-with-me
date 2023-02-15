package ru.practicum.explore.mappers;

import org.mapstruct.Mapper;
import ru.practicum.explore.dto.CategoryDto;
import ru.practicum.explore.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    List<CategoryDto> toDto(List<Category> category);

    Category toEntity(CategoryDto categoryDto);
}
