package inno.edu.api.domain.profile.accomplishment.repositories;

import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.interest.models.Interest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AccomplishmentRepository extends CrudRepository<Accomplishment, UUID> {
    List<Accomplishment> findByProfileId(UUID profileId);
}
