package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.dto.ExcursionRequestDto;
import ru.ithub.ithub_space.repository.ExcursionRequestRepository;
import java.util.List;

@RestController
@RequestMapping("/api/excursions")
@RequiredArgsConstructor
public class ExcursionRequestController {

    private final ExcursionRequestRepository excursionRequestRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExcursionRequestDto>> getAll() {
        return ResponseEntity.ok(excursionRequestRepository.findAll().stream().map(ExcursionRequestDto::from).toList());
    }

    @PostMapping
    public ResponseEntity<ExcursionRequestDto> create(@RequestBody ExcursionRequestDto request) {
        var saved = excursionRequestRepository.save(request.toEntity());
        return ResponseEntity.status(201).body(ExcursionRequestDto.from(saved));
    }
}
