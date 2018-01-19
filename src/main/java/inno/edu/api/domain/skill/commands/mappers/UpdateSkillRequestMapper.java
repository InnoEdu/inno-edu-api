package inno.edu.api.domain.skill.commands.mappers;

import inno.edu.api.domain.skill.commands.dtos.UpdateSkillRequest;
import inno.edu.api.domain.skill.models.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateSkillRequestMapper {
    void setSkill(UpdateSkillRequest updateSkillRequest, @MappingTarget Skill school);
}
