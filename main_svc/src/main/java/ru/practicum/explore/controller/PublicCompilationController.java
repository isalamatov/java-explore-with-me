package ru.practicum.explore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.dto.CompilationDto;
import ru.practicum.explore.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/compilations")
@Slf4j
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> get(@RequestParam(required = false, defaultValue = "true") Boolean pinned,
                                    @RequestParam(required = false, defaultValue = "0")
                                    @PositiveOrZero Integer from,
                                    @RequestParam(required = false, defaultValue = "10")
                                    @Positive Integer size) {
        log.debug("Get compilations request received in controller {}", this.getClass());
        List<CompilationDto> compilationDtos = compilationService.get(pinned, from, size);
        log.debug("Compilations retrieved successfully in controller {}", this.getClass());
        return compilationDtos;
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable Long compId) {
        log.debug("Get compilation by id request received in controller {}", this.getClass());
        CompilationDto compilationDto = compilationService.getById(compId);
        log.debug("Compilation by id retrieved successfully in controller {}", this.getClass());
        return compilationDto;
    }
}
