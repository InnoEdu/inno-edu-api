package inno.edu.api.domain.profile.commands.mappers;

import inno.edu.api.domain.profile.commands.dtos.CreateMenteeProfileRequest;
import inno.edu.api.domain.profile.models.MenteeProfile;
import org.mapstruct.Mapper;

@Mapper
public interface CreateMenteeProfileRequestToMenteeProfileMapper {
    MenteeProfile createMenteeProfileRequestToMenteeProfile(CreateMenteeProfileRequest createMenteeProfileRequest);
}
