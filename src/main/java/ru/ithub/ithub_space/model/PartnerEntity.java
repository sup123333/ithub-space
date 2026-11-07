package ru.ithub.ithub_space.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "partners")
public class PartnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    private String contactPerson;

    private String email;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String logoUrl;
}
