package ru.practicum.explore.stats.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explore.stats.dto.EndpointHitDto;
import ru.practicum.explore.stats.model.EndpointHit;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {
    @Mapping(target = "timestamp", source = "timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    EndpointHit toEntity(EndpointHitDto endpointHitDto);
}
