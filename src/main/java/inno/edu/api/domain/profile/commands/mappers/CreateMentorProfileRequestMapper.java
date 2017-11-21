package inno.edu.api.domain.profile.commands.mappers;

import inno.edu.api.domain.profile.commands.dtos.CreateMentorProfileRequest;
import inno.edu.api.domain.profile.models.MentorProfile;
import org.mapstruct.Mapper;

@Mapper
public interface CreateMentorProfileRequestMapper {
    MentorProfile toMentorProfile(CreateMentorProfileRequest createMentorProfileRequest);
}
