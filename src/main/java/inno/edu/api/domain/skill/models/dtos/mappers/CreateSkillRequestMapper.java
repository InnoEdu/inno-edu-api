package inno.edu.api.domain.skill.models.dtos.mappers;

import inno.edu.api.domain.skill.models.dtos.CreateSkillRequest;
import inno.edu.api.domain.skill.models.Skill;
import org.mapstruct.Mapper;

@Mapper
public interface CreateSkillRequestMapper {
    Skill toSkill(CreateSkillRequest createSkillRequest);
}
