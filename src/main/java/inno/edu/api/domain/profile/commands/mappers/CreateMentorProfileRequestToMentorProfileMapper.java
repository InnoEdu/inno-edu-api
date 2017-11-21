package inno.edu.api.domain.profile.commands.mappers;

import inno.edu.api.domain.profile.commands.dtos.CreateMentorProfileRequest;
import inno.edu.api.domain.profile.models.MentorProfile;
import org.mapstruct.Mapper;

@Mapper
public interface CreateMentorProfileRequestToMentorProfileMapper {
    MentorProfile createMentorProfileRequestToMentorProfile(CreateMentorProfileRequest createMentorProfileRequest);
}
