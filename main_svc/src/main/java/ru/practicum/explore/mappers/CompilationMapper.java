package ru.practicum.explore.mappers;

import org.mapstruct.Mapper;
import ru.practicum.explore.dto.CompilationDto;
import ru.practicum.explore.model.Compilation;

import java.util.List;

@Mapper(componentModel = "spring", uses = EventMapper.class)
public interface CompilationMapper {
    CompilationDto toDto(Compilation compilation);

    List<CompilationDto> toDto(List<Compilation> compilations);
}
