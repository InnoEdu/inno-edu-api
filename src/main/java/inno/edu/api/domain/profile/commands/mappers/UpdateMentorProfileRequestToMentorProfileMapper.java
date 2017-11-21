package inno.edu.api.domain.profile.commands.mappers;

import inno.edu.api.domain.profile.commands.dtos.UpdateMentorProfileRequest;
import inno.edu.api.domain.profile.models.MentorProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateMentorProfileRequestToMentorProfileMapper {
    void updateMentorProfileRequestToMentorProfile(UpdateMentorProfileRequest updateMentorProfileRequest, @MappingTarget MentorProfile menteeProfile);
}
