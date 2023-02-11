package ru.practicum.explore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.model.ParticipationRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByRequesterId(Long id);
    boolean existsByRequester_IdAndEvent_Id(Long userId, Long eventId);

    List<ParticipationRequest> findAllByIdIn(List<Long> ids);
}