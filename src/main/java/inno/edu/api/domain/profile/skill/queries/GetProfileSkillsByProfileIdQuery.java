package inno.edu.api.domain.profile.skill.queries;

import inno.edu.api.domain.skill.models.Skill;
import inno.edu.api.domain.profile.skill.models.ProfileSkill;
import inno.edu.api.domain.profile.skill.repositories.ProfileSkillRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Query
public class GetProfileSkillsByProfileIdQuery {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final ProfileSkillRepository profileSkillRepository;

    public GetProfileSkillsByProfileIdQuery(ProfileExistsAssertion profileExistsAssertion, ProfileSkillRepository profileSkillRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.profileSkillRepository = profileSkillRepository;
    }

    public List<Skill> run(UUID profileId) {
        profileExistsAssertion.run(profileId);

        return profileSkillRepository
                .findByProfileId(profileId)
                .stream()
                .map(ProfileSkill::getSkill)
                .collect(toList());
    }
}
