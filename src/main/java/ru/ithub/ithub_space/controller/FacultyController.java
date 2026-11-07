package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.dto.FacultyRequest;
import ru.ithub.ithub_space.dto.FacultyResponse;
import ru.ithub.ithub_space.exception.NotFoundException;
import ru.ithub.ithub_space.model.FacultyEntity;
import ru.ithub.ithub_space.repository.FacultyRepository;
import java.util.List;

@RestController
@RequestMapping("/api/faculties")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyRepository facultyRepository;

    @GetMapping
    public ResponseEntity<List<FacultyResponse>> getAll() {
        return ResponseEntity.ok(facultyRepository.findAll().stream().map(FacultyResponse::from).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyResponse> getById(@PathVariable Long id) {
        FacultyEntity faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Факультет не найден"));
        return ResponseEntity.ok(FacultyResponse.from(faculty));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyResponse> create(@RequestBody FacultyRequest request) {
        FacultyEntity saved = facultyRepository.save(request.toEntity());
        return ResponseEntity.status(201).body(FacultyResponse.from(saved));
    }
}
