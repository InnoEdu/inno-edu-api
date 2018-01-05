package inno.edu.api.domain.profile.commands.mappers;

import inno.edu.api.domain.profile.commands.dtos.CreateExperienceRequest;
import inno.edu.api.domain.profile.models.Experience;
import org.mapstruct.Mapper;

@Mapper
public interface CreateExperienceRequestMapper {
    Experience toExperience(CreateExperienceRequest createExperienceRequest);
}
