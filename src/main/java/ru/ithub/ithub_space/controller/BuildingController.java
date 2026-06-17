package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.model.Building;
import ru.ithub.ithub_space.repository.BuildingRepository;
import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingRepository buildingRepository;

    @GetMapping
    public ResponseEntity<List<Building>> getAll() {
        return ResponseEntity.ok(buildingRepository.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Building>> search(@RequestParam String q) {
        List<Building> byName = buildingRepository.findByNameContainingIgnoreCase(q);
        List<Building> byAddress = buildingRepository.findByAddressContainingIgnoreCase(q);
        byName.addAll(byAddress);
        return ResponseEntity.ok(byName.stream().distinct().toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Building> getById(@PathVariable Long id) {
        return ResponseEntity.ok(buildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Корпус не найден")));
    }

    @PostMapping
    public ResponseEntity<Building> create(@RequestBody Building building) {
        return ResponseEntity.status(201).body(buildingRepository.save(building));
    }
}
