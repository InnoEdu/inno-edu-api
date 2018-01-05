package inno.edu.api.domain.profile.experience.repositories;

import inno.edu.api.domain.profile.experience.models.Experience;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ExperienceRepository extends CrudRepository<Experience, UUID> {
    List<Experience> findByProfileId(UUID profileId);
}
