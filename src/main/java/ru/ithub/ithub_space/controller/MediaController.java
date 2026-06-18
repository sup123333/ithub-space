package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.dto.MediaRequest;
import ru.ithub.ithub_space.dto.MediaResponse;
import ru.ithub.ithub_space.model.MediaEntity;
import ru.ithub.ithub_space.repository.MediaRepository;
import java.util.List;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaRepository mediaRepository;

    @GetMapping
    public ResponseEntity<List<MediaResponse>> getAll(
            @RequestParam(required = false) String type) {
        List<MediaEntity> media;
        if (type != null) {
            media = mediaRepository.findByType(MediaEntity.MediaType.valueOf(type.toUpperCase()));
        } else {
            media = mediaRepository.findAll();
        }
        return ResponseEntity.ok(media.stream().map(MediaResponse::from).toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MediaResponse> create(@RequestBody MediaRequest request) {
        MediaEntity saved = mediaRepository.save(request.toEntity());
        return ResponseEntity.status(201).body(MediaResponse.from(saved));
    }
}
