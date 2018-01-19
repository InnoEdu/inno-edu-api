package inno.edu.api.domain.profile.skill.commands;

import inno.edu.api.domain.skill.assertions.SkillExistsAssertion;
import inno.edu.api.domain.skill.commands.DeleteSkillCommand;
import inno.edu.api.domain.profile.skill.models.ProfileSkillPrimaryKey;
import inno.edu.api.domain.profile.skill.repositories.ProfileSkillRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteProfileSkillCommand {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final SkillExistsAssertion skillExistsAssertion;
    private final ProfileSkillRepository profileSkillRepository;
    private final DeleteSkillCommand deleteSkillCommand;

    public DeleteProfileSkillCommand(ProfileExistsAssertion profileExistsAssertion, SkillExistsAssertion skillExistsAssertion, ProfileSkillRepository profileSkillRepository, DeleteSkillCommand deleteSkillCommand) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.skillExistsAssertion = skillExistsAssertion;
        this.profileSkillRepository = profileSkillRepository;
        this.deleteSkillCommand = deleteSkillCommand;
    }

    public void run(UUID profileId, UUID id) {
        profileExistsAssertion.run(profileId);
        skillExistsAssertion.run(id);

        ProfileSkillPrimaryKey profileSkillPrimaryKey =
                ProfileSkillPrimaryKey.builder()
                        .profileId(profileId)
                        .skillId(id)
                        .build();

        profileSkillRepository.delete(profileSkillPrimaryKey);

        deleteSkillCommand.run(id);
    }
}
