package inno.edu.api.domain.profile.root.queries;

import inno.edu.api.domain.profile.root.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetProfileByUserIdQuery {
    private final ProfileRepository profileRepository;

    public GetProfileByUserIdQuery(ProfileRepository ProfileRepository) {
        this.profileRepository = ProfileRepository;
    }

    public Profile run(UUID userId) {
        return ofNullable(profileRepository.findOneByUserId(userId))
                .orElseThrow(() -> new UserProfileNotFoundException(userId));
    }
}
