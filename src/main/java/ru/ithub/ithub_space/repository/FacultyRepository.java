package ru.ithub.ithub_space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ithub.ithub_space.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {}
