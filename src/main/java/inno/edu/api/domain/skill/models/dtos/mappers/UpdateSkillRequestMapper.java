package inno.edu.api.domain.skill.models.dtos.mappers;

import inno.edu.api.domain.skill.models.dtos.UpdateSkillRequest;
import inno.edu.api.domain.skill.models.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateSkillRequestMapper {
    void setSkill(UpdateSkillRequest updateSkillRequest, @MappingTarget Skill school);
}
