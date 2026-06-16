package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.model.Portfolio;
import ru.ithub.ithub_space.repository.PortfolioRepository;
import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioRepository portfolioRepository;

    @GetMapping
    public ResponseEntity<List<Portfolio>> getAll() {
        return ResponseEntity.ok(portfolioRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Portfolio> create(@RequestBody Portfolio portfolio) {
        return ResponseEntity.status(201).body(portfolioRepository.save(portfolio));
    }
}
