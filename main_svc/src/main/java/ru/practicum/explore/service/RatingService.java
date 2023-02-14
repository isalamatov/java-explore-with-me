package ru.practicum.explore.service;

public interface RatingService {
    void addLike(long eventId, long userId);

    void addDislike(long eventId, long userId);

    void deleteLike(long eventId, long userId);

    void deleteDislike(long eventId, long userId);
}
