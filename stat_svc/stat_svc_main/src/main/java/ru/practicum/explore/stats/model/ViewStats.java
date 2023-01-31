package ru.practicum.explore.stats.model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * ViewStats
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
