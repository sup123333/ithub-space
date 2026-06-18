package ru.ithub.ithub_space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ithub.ithub_space.model.ExcursionRequestEntity;

public interface ExcursionRequestRepository extends JpaRepository<ExcursionRequestEntity, Long> {}
