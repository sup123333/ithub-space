package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.model.Partner;
import ru.ithub.ithub_space.repository.PartnerRepository;
import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerRepository partnerRepository;

    @GetMapping
    public ResponseEntity<List<Partner>> getAll() {
        return ResponseEntity.ok(partnerRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Partner> create(@RequestBody Partner partner) {
        return ResponseEntity.status(201).body(partnerRepository.save(partner));
    }
}
