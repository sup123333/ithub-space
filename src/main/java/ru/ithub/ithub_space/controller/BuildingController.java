package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.dto.BuildingRequest;
import ru.ithub.ithub_space.dto.BuildingResponse;
import ru.ithub.ithub_space.exception.NotFoundException;
import ru.ithub.ithub_space.model.BuildingEntity;
import ru.ithub.ithub_space.repository.BuildingRepository;
import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingRepository buildingRepository;

    @GetMapping
    public ResponseEntity<List<BuildingResponse>> getAll() {
        return ResponseEntity.ok(buildingRepository.findAll().stream().map(BuildingResponse::from).toList());
    }

    @GetMapping("/search")
    public ResponseEntity<List<BuildingResponse>> search(@RequestParam String q) {
        List<BuildingEntity> byName = buildingRepository.findByNameContainingIgnoreCase(q);
        List<BuildingEntity> byAddress = buildingRepository.findByAddressContainingIgnoreCase(q);
        byName.addAll(byAddress);
        return ResponseEntity.ok(byName.stream().distinct().map(BuildingResponse::from).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingResponse> getById(@PathVariable Long id) {
        BuildingEntity building = buildingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Корпус не найден"));
        return ResponseEntity.ok(BuildingResponse.from(building));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BuildingResponse> create(@RequestBody BuildingRequest request) {
        BuildingEntity saved = buildingRepository.save(request.toEntity());
        return ResponseEntity.status(201).body(BuildingResponse.from(saved));
    }
}
