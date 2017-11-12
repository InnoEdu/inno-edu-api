package inno.edu.api.domain.user.repositories;

import inno.edu.api.domain.user.models.MentorProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MentorProfileRepository extends CrudRepository<MentorProfile, UUID> {
    MentorProfile findOneMentorProfileByMentorIdAndIsActive(UUID mentorId, boolean isActive);
    boolean existsMentorProfileByMentorId(UUID mentorId);

}