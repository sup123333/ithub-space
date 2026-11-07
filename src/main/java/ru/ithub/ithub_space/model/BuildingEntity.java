package ru.ithub.ithub_space.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "buildings")
public class BuildingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String mapUrl;

    private String imageUrl;
}
