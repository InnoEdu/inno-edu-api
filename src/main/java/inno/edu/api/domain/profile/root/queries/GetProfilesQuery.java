package inno.edu.api.domain.profile.root.queries;

import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Query
public class GetProfilesQuery {
    private final ProfileRepository profileRepository;

    public GetProfilesQuery(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> run() {
        return newArrayList(profileRepository.findAll());
    }
}
