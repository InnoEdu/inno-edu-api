package inno.edu.api.domain.profile.experience.commands.mappers;

import inno.edu.api.domain.profile.experience.commands.dtos.UpdateExperienceRequest;
import inno.edu.api.domain.profile.experience.models.Experience;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateExperienceRequestMapper {
    void setExperience(UpdateExperienceRequest updateExperienceRequest, @MappingTarget Experience experience);
}
