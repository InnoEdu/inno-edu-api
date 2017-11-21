package inno.edu.api.domain.profile.commands.mappers;

import inno.edu.api.domain.profile.commands.dtos.CreateMentorProfileRequest;
import inno.edu.api.domain.school.models.School;
import org.mapstruct.Mapper;

@Mapper
public interface CreateMentorProfileRequestToMentorProfileMapper {
    School createMentorProfileRequestToMentorProfile(CreateMentorProfileRequest createMentorProfileRequest);
}
