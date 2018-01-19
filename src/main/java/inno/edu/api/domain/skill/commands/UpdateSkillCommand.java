package inno.edu.api.domain.skill.commands;

import inno.edu.api.domain.skill.commands.dtos.UpdateSkillRequest;
import inno.edu.api.domain.skill.commands.mappers.UpdateSkillRequestMapper;
import inno.edu.api.domain.skill.models.Skill;
import inno.edu.api.domain.skill.queries.GetSkillByIdQuery;
import inno.edu.api.domain.skill.repositories.SkillRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateSkillCommand {
    private final UpdateSkillRequestMapper updateSkillRequestMapper;

    private final SkillRepository skillRepository;
    private final GetSkillByIdQuery getSkillByIdQuery;

    public UpdateSkillCommand(UpdateSkillRequestMapper updateSkillRequestMapper, SkillRepository skillRepository, GetSkillByIdQuery getSkillByIdQuery) {
        this.updateSkillRequestMapper = updateSkillRequestMapper;
        this.skillRepository = skillRepository;
        this.getSkillByIdQuery = getSkillByIdQuery;
    }

    public Skill run(UUID id, UpdateSkillRequest updateSkillRequest) {
        Skill currentSkill = getSkillByIdQuery.run(id);
        updateSkillRequestMapper.setSkill(updateSkillRequest, currentSkill);
        return skillRepository.save(currentSkill);
    }
}
