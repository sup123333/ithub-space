package ru.ithub.ithub_space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ithub.ithub_space.model.MediaEntity;
import java.util.List;

public interface MediaRepository extends JpaRepository<MediaEntity, Long> {
    List<MediaEntity> findByType(MediaEntity.MediaType type);
}
