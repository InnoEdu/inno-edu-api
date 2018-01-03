package inno.edu.api.domain.profile.repositories;

import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.models.ProfileStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProfileRepository  extends CrudRepository<Profile, UUID> {
    boolean existsByUserId(UUID userId);
    Profile findOneByUserId(UUID userId);
    List<Profile> findBySchoolIdAndStatus(UUID schoolId, ProfileStatus status);
}
