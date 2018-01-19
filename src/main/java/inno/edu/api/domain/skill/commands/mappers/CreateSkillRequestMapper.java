package inno.edu.api.domain.skill.commands.mappers;

import inno.edu.api.domain.skill.commands.dtos.CreateSkillRequest;
import inno.edu.api.domain.skill.models.Skill;
import org.mapstruct.Mapper;

@Mapper
public interface CreateSkillRequestMapper {
    Skill toSkill(CreateSkillRequest createSkillRequest);
}
