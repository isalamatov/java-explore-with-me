package ru.practicum.explore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.practicum.explore.enums.EventStateEnum;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;
    @Column(name = "annotation")
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private Integer confirmedRequests = 0;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    @OneToOne
    private User initiator;
    @Embedded
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private EventStateEnum state;
    private String title;
    private Long views = 0L;
    @OneToMany(mappedBy = "event")
    private List<ParticipationRequest> requests;
    @ManyToMany(mappedBy = "likedEvents")
    private List<User> likedBy;
    @ManyToMany(mappedBy = "dislikedEvents")
    private List<User> dislikedBy;
    private Long rating = 0L;

    public void incrementRating() {
        this.rating++;
    }

    public void decrementRating() {
        this.rating--;
    }
}
