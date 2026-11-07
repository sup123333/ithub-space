package ru.ithub.ithub_space.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime eventDate;

    private String location;

    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String program;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;
}