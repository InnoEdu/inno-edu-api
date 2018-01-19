package inno.edu.api.domain.skill.commands;

import inno.edu.api.domain.skill.assertions.SkillExistsAssertion;
import inno.edu.api.domain.skill.repositories.SkillRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteSkillCommand {
    private final SkillExistsAssertion skillExistsAssertion;
    private final SkillRepository skillRepository;

    public DeleteSkillCommand(SkillExistsAssertion skillExistsAssertion, SkillRepository skillRepository) {
        this.skillExistsAssertion = skillExistsAssertion;
        this.skillRepository = skillRepository;
    }

    public void run(UUID id) {
        skillExistsAssertion.run(id);
        skillRepository.delete(id);
    }
}
