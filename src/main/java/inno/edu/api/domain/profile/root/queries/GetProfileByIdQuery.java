package inno.edu.api.domain.profile.root.queries;

import inno.edu.api.domain.profile.root.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
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
