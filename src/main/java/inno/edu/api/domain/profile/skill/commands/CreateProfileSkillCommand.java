package inno.edu.api.domain.profile.skill.commands;

import inno.edu.api.domain.skill.commands.CreateSkillCommand;
import inno.edu.api.domain.skill.commands.dtos.CreateSkillRequest;
import inno.edu.api.domain.skill.models.Skill;
import inno.edu.api.domain.profile.skill.models.ProfileSkill;
import inno.edu.api.domain.profile.skill.repositories.ProfileSkillRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class CreateProfileSkillCommand {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final CreateSkillCommand createSkillCommand;
    private final ProfileSkillRepository profileSkillRepository;

    public CreateProfileSkillCommand(ProfileExistsAssertion profileExistsAssertion, CreateSkillCommand createSkillCommand, ProfileSkillRepository profileSkillRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.createSkillCommand = createSkillCommand;
        this.profileSkillRepository = profileSkillRepository;
    }

    public Skill run(UUID profileId, CreateSkillRequest request) {
        profileExistsAssertion.run(profileId);

        Skill skill = createSkillCommand.run(request);

        ProfileSkill profileSkill = ProfileSkill.builder()
                .profileId(profileId)
                .skillId(skill.getId())
                .build();

        profileSkillRepository.save(profileSkill);

        return skill;
    }
}
