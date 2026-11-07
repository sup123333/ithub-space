package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ithub.ithub_space.repository.BuildingRepository;
import ru.ithub.ithub_space.repository.EventRepository;
import ru.ithub.ithub_space.repository.FacultyRepository;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {

    private final FacultyRepository facultyRepository;
    private final EventRepository eventRepository;
    private final BuildingRepository buildingRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", "UP");
        body.put("service", "IT Hub Space API");
        body.put("timestamp", Instant.now().toString());
        body.put("faculties", facultyRepository.count());
        body.put("events", eventRepository.count());
        body.put("buildings", buildingRepository.count());
        return ResponseEntity.ok(body);
    }
}
