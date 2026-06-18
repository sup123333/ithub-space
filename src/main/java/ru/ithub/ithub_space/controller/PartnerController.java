package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.dto.PartnerRequest;
import ru.ithub.ithub_space.dto.PartnerResponse;
import ru.ithub.ithub_space.model.PartnerEntity;
import ru.ithub.ithub_space.repository.PartnerRepository;
import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerRepository partnerRepository;

    @GetMapping
    public ResponseEntity<List<PartnerResponse>> getAll() {
        return ResponseEntity.ok(partnerRepository.findAll().stream().map(PartnerResponse::from).toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PartnerResponse> create(@RequestBody PartnerRequest request) {
        PartnerEntity saved = partnerRepository.save(request.toEntity());
        return ResponseEntity.status(201).body(PartnerResponse.from(saved));
    }
}
