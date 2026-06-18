package ru.ithub.ithub_space.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ithub.ithub_space.exception.NotFoundException;
import ru.ithub.ithub_space.model.EventEntity;
import ru.ithub.ithub_space.repository.EventRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<EventEntity> getAll() {
        return eventRepository.findAllByOrderByEventDateDesc();
    }

    public EventEntity getById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Мероприятие не найдено: " + id));
    }

    public EventEntity create(EventEntity event) {
        log.info("Создание мероприятия: {}", event.getTitle());
        return eventRepository.save(event);
    }

    public EventEntity update(Long id, EventEntity updated) {
        log.info("Обновление мероприятия id: {}", id);
        EventEntity event = getById(id);
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
