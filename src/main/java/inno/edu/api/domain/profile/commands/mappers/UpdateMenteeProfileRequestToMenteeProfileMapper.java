package inno.edu.api.domain.profile.commands.mappers;

import inno.edu.api.domain.profile.commands.dtos.UpdateMenteeProfileRequest;
import inno.edu.api.domain.profile.models.MenteeProfile;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateMenteeProfileRequestToMenteeProfileMapper {
    void updateMenteeProfileRequestToMenteeProfile(UpdateMenteeProfileRequest updateMenteeProfileRequest, @MappingTarget MenteeProfile menteeProfile);
}
