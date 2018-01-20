package inno.edu.api.domain.skill.commands;

import inno.edu.api.domain.skill.models.dtos.CreateSkillRequest;
import inno.edu.api.domain.skill.models.dtos.mappers.CreateSkillRequestMapper;
import inno.edu.api.domain.skill.models.Skill;
import inno.edu.api.domain.skill.repositories.SkillRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

@Command
public class CreateSkillCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateSkillRequestMapper createSkillRequestMapper;
    private final SkillRepository skillRepository;

    public CreateSkillCommand(UUIDGeneratorService uuidGeneratorService, CreateSkillRequestMapper createSkillRequestMapper, SkillRepository skillRepository) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createSkillRequestMapper = createSkillRequestMapper;
        this.skillRepository = skillRepository;
    }

    public Skill run(CreateSkillRequest createSkillRequest) {
        Skill skill = createSkillRequestMapper.toSkill(createSkillRequest);
        skill.setId(uuidGeneratorService.generate());
        return skillRepository.save(skill);
    }
}
