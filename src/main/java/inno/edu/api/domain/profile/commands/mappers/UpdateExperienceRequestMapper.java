package inno.edu.api.domain.profile.commands.mappers;

import inno.edu.api.domain.profile.commands.dtos.UpdateExperienceRequest;
import inno.edu.api.domain.profile.models.Experience;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateExperienceRequestMapper {
    void setExperience(UpdateExperienceRequest updateExperienceRequest, @MappingTarget Experience experience);
}
