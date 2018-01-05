package inno.edu.api.domain.profile.experience.commands.mappers;

import inno.edu.api.domain.profile.experience.commands.dtos.CreateExperienceRequest;
import inno.edu.api.domain.profile.experience.models.Experience;
import org.mapstruct.Mapper;

@Mapper
public interface CreateExperienceRequestMapper {
    Experience toExperience(CreateExperienceRequest createExperienceRequest);
}
