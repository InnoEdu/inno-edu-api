package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetMenteeProfileByIdQuery {
    private final MenteeProfileRepository profileRepository;

    public GetMenteeProfileByIdQuery(MenteeProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public MenteeProfile run(UUID id) {
        return ofNullable(profileRepository.findOne(id))
                .orElseThrow(() -> new ProfileNotFoundException(id));
    }
}
