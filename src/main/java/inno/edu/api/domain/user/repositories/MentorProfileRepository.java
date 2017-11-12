package inno.edu.api.domain.user.repositories;

import inno.edu.api.domain.user.models.MentorProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MentorProfileRepository extends CrudRepository<MentorProfile, UUID> {
    List<MentorProfile> findByUniversityIdAndIsActiveIsTrue(UUID universityId);
    MentorProfile findOneByMentorIdAndIsActiveIsTrue(UUID mentorId);
}