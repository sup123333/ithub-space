package ru.ithub.ithub_space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ithub.ithub_space.model.BuildingEntity;
import java.util.List;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {
    List<BuildingEntity> findByNameContainingIgnoreCase(String name);
    List<BuildingEntity> findByAddressContainingIgnoreCase(String address);
}
