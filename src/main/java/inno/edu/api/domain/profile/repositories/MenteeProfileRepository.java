package inno.edu.api.domain.profile.repositories;

import inno.edu.api.domain.profile.models.MenteeProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MenteeProfileRepository extends CrudRepository<MenteeProfile, UUID> {
    MenteeProfile findOneByMenteeId(UUID menteeId);
    boolean existsByMenteeId(UUID menteeId);

}