package ru.ithub.ithub_space.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ithub.ithub_space.model.Event;
import ru.ithub.ithub_space.repository.EventRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> getAll() {
        List<Event> events = eventRepository.findAllByOrderByEventDateDesc();
        log.debug("Получено мероприятий: {}", events.size());
        return events;
    }

    public Event getById(Long id) {
        log.debug("Поиск мероприятия по id: {}", id);
        return eventRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Мероприятие не найдено: {}", id);
                    return new RuntimeException("Мероприятие не найдено: " + id);
                });
    }

    public Event create(Event event) {
        log.info("Создание мероприятия: {}", event.getTitle());
        Event saved = eventRepository.save(event);
        log.debug("Мероприятие создано с id: {}", saved.getId());
        return saved;
    }

    public Event update(Long id, Event updated) {
        log.info("Обновление мероприятия id: {}", id);
        Event event = getById(id);
        event.setTitle(updated.getTitle());
        event.setDescription(updated.getDescription());
        event.setEventDate(updated.getEventDate());
        event.setLocation(updated.getLocation());
        event.setImageUrl(updated.getImageUrl());
        return eventRepository.save(event);
    }

    public void delete(Long id) {
        log.info("Удаление мероприятия id: {}", id);
        eventRepository.deleteById(id);
    }
}
