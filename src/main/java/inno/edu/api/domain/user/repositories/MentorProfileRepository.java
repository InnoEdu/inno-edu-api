package inno.edu.api.domain.user.repositories;

import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.models.ProfileStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MentorProfileRepository extends CrudRepository<MentorProfile, UUID> {
    List<MentorProfile> findByUniversityIdAndStatus(UUID universityId, ProfileStatus status);
    MentorProfile findOneByMentorIdAndStatus(UUID mentorId, ProfileStatus status);
}