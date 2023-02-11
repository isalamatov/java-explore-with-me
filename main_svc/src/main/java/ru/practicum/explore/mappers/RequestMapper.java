package ru.practicum.explore.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explore.dto.ParticipationRequestDto;
import ru.practicum.explore.model.ParticipationRequest;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    ParticipationRequestDto toDto(ParticipationRequest request);

    List<ParticipationRequestDto> toDto(List<ParticipationRequest> requests);
}
