package ru.practicum.explore.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.explore.enums.EventStateEnum;
import ru.practicum.explore.exceptions.ConflictException;
import ru.practicum.explore.model.Category;
import ru.practicum.explore.model.Event;
import ru.practicum.explore.model.User;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.repository.UserRepository;
import ru.practicum.explore.service.impl.RatingServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = {RatingServiceImpl.class})
public class RatingServiceTests {
    @Autowired
    private RatingService ratingService;
    @MockBean
    private EventRepository eventRepository;
    @MockBean
    private UserRepository userRepository;

    private Category walking = new Category(1L, "Walking");
    private User john;
    private User jane;
    private Event event = new Event();

    @BeforeEach
    void setEnvironment() {
        john = new User(1L, "john@doe.com", "John", null, new ArrayList<>(), new ArrayList<>());
        jane = new User(2L, "jane@doe.com", "Jane", null, new ArrayList<>(), new ArrayList<>());
        event.setId(1L).setState(EventStateEnum.PUBLISHED).setInitiator(john).setLikedBy(new ArrayList<>()).setDislikedBy(new ArrayList<>());
    }

    @Test
    void addLike() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(jane));
        Mockito.when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        ratingService.addLike(event.getId(), jane.getId());
        assertThat(event.getLikedBy(), contains(jane));
        assertThat(jane.getLikedEvents(), contains(event));
        assertThat(event.getRating(), equalTo(1L));
    }

    @Test
    void addDislikeAfterLike() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(jane));
        Mockito.when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        ratingService.addLike(event.getId(), jane.getId());
        assertThat(event.getLikedBy(), contains(jane));
        assertThat(jane.getLikedEvents(), contains(event));
        assertThat(event.getRating(), equalTo(1L));
        ratingService.addDislike(event.getId(), jane.getId());
        assertThat(event.getLikedBy(), hasSize(0));
        assertThat(jane.getLikedEvents(), hasSize(0));
        assertThat(event.getDislikedBy(), contains(jane));
        assertThat(jane.getDislikedEvents(), contains(event));
        assertThat(event.getRating(), equalTo(0L));
    }

    @Test
    void addLikeByInitiatorShouldThrowException() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(john));
        Mockito.when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        assertThrows(ConflictException.class, () -> ratingService.addLike(event.getId(), john.getId()));
    }

    @Test
    void addDislike() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(jane));
        Mockito.when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        ratingService.addDislike(event.getId(), jane.getId());
        assertThat(event.getDislikedBy(), contains(jane));
        assertThat(jane.getDislikedEvents(), contains(event));
        assertThat(event.getRating(), equalTo(-1L));
    }

    @Test
    void addlikeAfterDislike() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(jane));
        Mockito.when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        ratingService.addDislike(event.getId(), jane.getId());
        assertThat(event.getDislikedBy(), contains(jane));
        assertThat(jane.getDislikedEvents(), contains(event));
        assertThat(event.getRating(), equalTo(-1L));
        ratingService.addLike(event.getId(), jane.getId());
        assertThat(event.getLikedBy(), contains(jane));
        assertThat(jane.getLikedEvents(), contains(event));
        assertThat(event.getDislikedBy(), hasSize(0));
        assertThat(jane.getDislikedEvents(), hasSize(0));
        assertThat(event.getRating(), equalTo(0L));
    }

    @Test
    void addDislikeByInitiatorShouldThrowException() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(john));
        Mockito.when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        assertThrows(ConflictException.class, () -> ratingService.addDislike(event.getId(), john.getId()));
    }

    @Test
    void deleteLike() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(jane));
        Mockito.when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        List<User> likedBy = new ArrayList<>();
        likedBy.add(jane);
        List<Event> likedEvents = new ArrayList<>();
        likedEvents.add(event);
        event.setLikedBy(likedBy);
        event.incrementRating();
        jane.setLikedEvents(likedEvents);
        ratingService.deleteLike(1L, 2L);
        assertThat(event.getLikedBy(), hasSize(0));
        assertThat(jane.getLikedEvents(), hasSize(0));
        assertThat(event.getRating(), equalTo(0L));
    }

    @Test
    void deleteDislike() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(jane));
        Mockito.when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        List<User> dislikedBy = new ArrayList<>();
        dislikedBy.add(jane);
        List<Event> dislikedEvents = new ArrayList<>();
        dislikedEvents.add(event);
        event.setDislikedBy(dislikedBy);
        event.decrementRating();
        jane.setDislikedEvents(dislikedEvents);
        ratingService.deleteDislike(1L, 2L);
        assertThat(event.getDislikedBy(), hasSize(0));
        assertThat(jane.getDislikedEvents(), hasSize(0));
        assertThat(event.getRating(), equalTo(0L));
    }
}
