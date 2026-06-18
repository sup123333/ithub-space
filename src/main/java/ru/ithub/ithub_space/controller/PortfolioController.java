package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.dto.PortfolioRequest;
import ru.ithub.ithub_space.dto.PortfolioResponse;
import ru.ithub.ithub_space.model.PortfolioEntity;
import ru.ithub.ithub_space.model.UserEntity;
import ru.ithub.ithub_space.repository.PortfolioRepository;
import ru.ithub.ithub_space.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioRepository portfolioRepository;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PortfolioResponse>> getAll() {
        return ResponseEntity.ok(portfolioRepository.findAll().stream().map(PortfolioResponse::from).toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<PortfolioResponse> create(@RequestBody PortfolioRequest request,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity student = userService.findByEmail(userDetails.getUsername());
        PortfolioEntity portfolio = new PortfolioEntity();
        portfolio.setTitle(request.title());
        portfolio.setDescription(request.description());
        portfolio.setProjectUrl(request.projectUrl());
        portfolio.setImageUrl(request.imageUrl());
        portfolio.setStudent(student);
        PortfolioEntity saved = portfolioRepository.save(portfolio);
        return ResponseEntity.status(201).body(PortfolioResponse.from(saved));
    }
}
