package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetProfileByIdQuery {
    private final ProfileRepository profileRepository;

    public GetProfileByIdQuery(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile run(UUID id) {
        return ofNullable(profileRepository.findOne(id))
                .orElseThrow(() -> new ProfileNotFoundException(id));
    }
}
