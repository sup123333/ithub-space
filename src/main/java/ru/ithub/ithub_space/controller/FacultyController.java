package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.model.Faculty;
import ru.ithub.ithub_space.repository.FacultyRepository;
import java.util.List;

@RestController
@RequestMapping("/api/faculties")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyRepository facultyRepository;

    @GetMapping
    public ResponseEntity<List<Faculty>> getAll() {
        return ResponseEntity.ok(facultyRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Факультет не найден")));
    }

    @PostMapping
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        return ResponseEntity.status(201).body(facultyRepository.save(faculty));
    }
}
