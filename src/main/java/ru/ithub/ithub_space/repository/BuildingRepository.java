package ru.ithub.ithub_space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ithub.ithub_space.model.Building;
import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    List<Building> findByNameContainingIgnoreCase(String name);
    List<Building> findByAddressContainingIgnoreCase(String address);
}
