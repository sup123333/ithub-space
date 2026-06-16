package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.model.Media;
import ru.ithub.ithub_space.repository.MediaRepository;
import java.util.List;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaRepository mediaRepository;

    @GetMapping
    public ResponseEntity<List<Media>> getAll(
            @RequestParam(required = false) String type) {
        if (type != null) {
            Media.MediaType mediaType = Media.MediaType.valueOf(type.toUpperCase());
            return ResponseEntity.ok(mediaRepository.findByType(mediaType));
        }
        return ResponseEntity.ok(mediaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Media> create(@RequestBody Media media) {
        return ResponseEntity.status(201).body(mediaRepository.save(media));
    }
}
