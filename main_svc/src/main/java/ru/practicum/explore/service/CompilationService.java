package ru.practicum.explore.service;

import ru.practicum.explore.dto.CompilationDto;
import ru.practicum.explore.dto.NewCompilationDto;
import ru.practicum.explore.model.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> get(Boolean pinned, Integer from, Integer size);

    CompilationDto getById(Long compId);

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long compId);

    CompilationDto updateCompilation(Long compId, UpdateCompilationRequest request);
}
