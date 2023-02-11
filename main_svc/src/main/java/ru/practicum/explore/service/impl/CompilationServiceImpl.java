package ru.practicum.explore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.dto.CompilationDto;
import ru.practicum.explore.dto.NewCompilationDto;
import ru.practicum.explore.exceptions.EntityDoesNotExistsException;
import ru.practicum.explore.mappers.CompilationMapper;
import ru.practicum.explore.model.Compilation;
import ru.practicum.explore.model.Event;
import ru.practicum.explore.model.UpdateCompilationRequest;
import ru.practicum.explore.repository.CompilationRepository;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.service.CompilationService;

import java.util.List;

@Service
@Slf4j
@Primary
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public List<CompilationDto> get(Boolean pinned, Integer from, Integer size) {
        log.debug("Get compilations request received in service {}", this.getClass());
        Pageable page = PageRequest.of(from / size, size);
        List<Compilation> compilations = compilationRepository.findAllByPinnedIs(pinned, page);
        List<CompilationDto> compilationDtos = compilationMapper.toDto(compilations);
        log.debug("Compilations retrieved successfully in service {}", this.getClass());
        return compilationDtos;
    }

    @Override
    public CompilationDto getById(Long compId) {
        log.debug("Get compilation by id request received in controller {}", this.getClass());
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityDoesNotExistsException(Compilation.class, compId));
        CompilationDto compilationDto = compilationMapper.toDto(compilation);
        log.debug("Compilation by id retrieved successfully in controller {}", this.getClass());
        return compilationDto;
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        log.debug("Create new compilation request received in srevice {}", this.getClass());
        List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
        Boolean pinned = newCompilationDto.getPinned();
        String title = newCompilationDto.getTitle();
        Compilation compilation = new Compilation().setEvents(events).setPinned(pinned).setTitle(title);
        compilationRepository.save(compilation);
        CompilationDto compilationDto = compilationMapper.toDto(compilation);
        log.debug("Create new compilation processed in service {}", this.getClass());
        return compilationDto;
    }

    @Override
    public void deleteCompilation(Long compId) {
        log.debug("Delete compilation request received in service {}", this.getClass());
        compilationRepository.deleteById(compId);
        log.debug("Delete compilation processed in service {}", this.getClass());
    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest request) {
        log.debug("Update compilation request received in service {}", this.getClass());
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityDoesNotExistsException(Compilation.class, compId));
        if (request.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(request.getEvents());
            compilation.setEvents(events);
        }
        if (request.getPinned() != null) {
            compilation.setPinned(request.getPinned());
        }
        if (request.getTitle() != null) {
            compilation.setTitle(request.getTitle());
        }
        compilationRepository.save(compilation);
        CompilationDto createdCompilation = compilationMapper.toDto(compilation);
        log.debug("Update compilation processed in service {}", this.getClass());
        return createdCompilation;
    }
}
