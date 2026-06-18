package ru.ithub.ithub_space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ithub.ithub_space.model.FacultyEntity;

public interface FacultyRepository extends JpaRepository<FacultyEntity, Long> {}
