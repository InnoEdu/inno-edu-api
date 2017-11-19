package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetMentorProfileByIdQuery {
    private final MentorProfileRepository profileRepository;

    public GetMentorProfileByIdQuery(MentorProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public MentorProfile run(UUID id) {
        return ofNullable(profileRepository.findOne(id))
                .orElseThrow(() -> new ProfileNotFoundException(id));
    }
}
