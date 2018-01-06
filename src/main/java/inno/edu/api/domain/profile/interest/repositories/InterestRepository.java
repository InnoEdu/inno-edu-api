package inno.edu.api.domain.profile.interest.repositories;

import inno.edu.api.domain.profile.interest.models.Interest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface InterestRepository extends CrudRepository<Interest, UUID> {
    List<Interest> findByProfileId(UUID profileId);
}
