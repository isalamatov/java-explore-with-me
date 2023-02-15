package ru.practicum.explore.stats.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore.stats.dto.ViewStatsDto;
import ru.practicum.explore.stats.model.ViewStats;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ViewStatsMapper {
    ViewStatsDto toDto(ViewStats viewStats);

    List<ViewStatsDto> toEntityList(List<ViewStats> viewStats);
}
