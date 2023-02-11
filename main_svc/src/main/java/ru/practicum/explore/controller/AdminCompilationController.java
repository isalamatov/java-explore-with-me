package ru.practicum.explore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.dto.CompilationDto;
import ru.practicum.explore.dto.NewCompilationDto;
import ru.practicum.explore.model.UpdateCompilationRequest;
import ru.practicum.explore.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@Slf4j
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.debug("Create new compilation request received in controller {}", this.getClass());
        CompilationDto createdCompilation = compilationService.createCompilation(newCompilationDto);
        log.debug("Create new compilation processed in controller {}", this.getClass());
        return createdCompilation;
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.debug("Delete compilation request received in controller {}", this.getClass());
        compilationService.deleteCompilation(compId);
        log.debug("Delete compilation processed in controller {}", this.getClass());
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                            @RequestBody @Valid UpdateCompilationRequest request) {
        log.debug("Update compilation request received in controller {}", this.getClass());
        CompilationDto createdCompilation = compilationService.updateCompilation(compId, request);
        log.debug("Update compilation processed in controller {}", this.getClass());
        return createdCompilation;
    }
}
