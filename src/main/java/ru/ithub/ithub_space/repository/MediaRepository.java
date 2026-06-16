package ru.ithub.ithub_space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ithub.ithub_space.model.Media;
import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByType(Media.MediaType type);
}
