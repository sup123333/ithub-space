package ru.ithub.ithub_space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ithub.ithub_space.model.Event;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOrderByEventDateDesc();
}
