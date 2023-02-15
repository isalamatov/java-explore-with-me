package ru.practicum.explore.stats.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * EndpointHit
 */
@Entity(name = "hits")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@NamedNativeQuery(name = "getStats",
        query = "select h.app as app, h.uri as uri, count(h.ip) as hits from hits as h " +
                "where (h.timestamp between cast(?1 as TIMESTAMP) and cast(?2 as TIMESTAMP)) and (h.uri in ?3) " +
                "group by app, uri order by hits desc",
        resultSetMapping = "ViewStatsMapping")
@NamedNativeQuery(name = "getStatsDistinctIp",
        query = "select h.app as app, h.uri as uri, count(distinct h.ip) as hits from hits as h " +
                "where (h.timestamp between cast(?1 as TIMESTAMP) and cast(?2 as TIMESTAMP)) and (h.uri in ?3) " +
                "group by app, uri order by hits desc",
        resultSetMapping = "ViewStatsMapping")
@SqlResultSetMapping(name = "ViewStatsMapping",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "app", type = String.class),
                                @ColumnResult(name = "uri", type = String.class),
                                @ColumnResult(name = "hits", type = long.class),
                        },
                        targetClass = ViewStats.class
                )}
)
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hit_id")
    private Long id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri")
    private String uri;

    @Column(name = "ip")
    private String ip;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}