package ru.ithub.ithub_space.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ithub.ithub_space.model.Event;
import ru.ithub.ithub_space.repository.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> getAll() {
        return eventRepository.findAllByOrderByEventDateDesc();
    }

    public Event getById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено: " + id));
    }

    public Event create(Event event) {
        return eventRepository.save(event);
    }

    public Event update(Long id, Event updated) {
        Event event = getById(id);
        event.setTitle(updated.getTitle());
        event.setDescription(updated.getDescription());
        event.setEventDate(updated.getEventDate());
        event.setLocation(updated.getLocation());
        event.setImageUrl(updated.getImageUrl());
        return eventRepository.save(event);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
