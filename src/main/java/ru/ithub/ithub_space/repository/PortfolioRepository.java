package ru.ithub.ithub_space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ithub.ithub_space.model.PortfolioEntity;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {}
