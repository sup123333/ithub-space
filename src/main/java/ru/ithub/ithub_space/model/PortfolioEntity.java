package ru.ithub.ithub_space.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "portfolios")
public class PortfolioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String projectUrl;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private UserEntity student;
}
