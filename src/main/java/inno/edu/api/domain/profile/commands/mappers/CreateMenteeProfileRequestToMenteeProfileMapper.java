package inno.edu.api.domain.profile.commands.mappers;

import inno.edu.api.domain.profile.commands.dtos.CreateMenteeProfileRequest;
import inno.edu.api.domain.school.models.School;
import org.mapstruct.Mapper;

@Mapper
public interface CreateMenteeProfileRequestToMenteeProfileMapper {
    School createMenteeProfileRequestToMenteeProfile(CreateMenteeProfileRequest createMenteeProfileRequest);
}
