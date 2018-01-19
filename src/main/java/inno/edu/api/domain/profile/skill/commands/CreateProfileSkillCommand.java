package inno.edu.api.domain.profile.skill.commands;

import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.skill.models.ProfileSkill;
import inno.edu.api.domain.profile.skill.repositories.ProfileSkillRepository;
import inno.edu.api.domain.skill.assertions.SkillExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class CreateProfileSkillCommand {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final SkillExistsAssertion skillExistsAssertion;
    private final ProfileSkillRepository profileSkillRepository;

    public CreateProfileSkillCommand(ProfileExistsAssertion profileExistsAssertion, SkillExistsAssertion skillExistsAssertion, ProfileSkillRepository profileSkillRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.skillExistsAssertion = skillExistsAssertion;
        this.profileSkillRepository = profileSkillRepository;
    }

    public ProfileSkill run(UUID profileId, UUID skillId) {
        profileExistsAssertion.run(profileId);
        skillExistsAssertion.run(skillId);

        ProfileSkill profileSkill = ProfileSkill.builder()
                .profileId(profileId)
                .skillId(skillId)
                .build();

        return profileSkillRepository.save(profileSkill);
    }
}
