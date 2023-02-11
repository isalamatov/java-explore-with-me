package ru.practicum.explore.mappers;

import org.mapstruct.Mapper;
import ru.practicum.explore.dto.EventFullDto;
import ru.practicum.explore.dto.EventShortDto;
import ru.practicum.explore.dto.NewEventDto;
import ru.practicum.explore.model.Event;
import ru.practicum.explore.service.impl.CategoryServiceImpl;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = CategoryServiceImpl.class)
public interface EventMapper {

    Event fromNewEventDto(NewEventDto newEventDto);

    EventFullDto toFullDto(Event event);

    List<EventFullDto> toFullDto(Iterable<Event> events);

    EventShortDto toShortDto(Event event);

    List<EventShortDto> toShortDto(List<Event> events);
}
