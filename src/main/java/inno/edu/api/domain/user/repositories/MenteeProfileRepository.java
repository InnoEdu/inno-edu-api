package inno.edu.api.domain.user.repositories;

import inno.edu.api.domain.user.models.MenteeProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MenteeProfileRepository extends CrudRepository<MenteeProfile, UUID> {
    MenteeProfile findOneMenteeProfileByMenteeId(UUID menteeId);
}