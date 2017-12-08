package inno.edu.api.domain.profile.queries.mappers;

import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.queries.dtos.MentorProfileUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface MentorProfileUserMapper {
    @Mappings({
            @Mapping(source = "user.firstName", target = "firstName"),
            @Mapping(source = "user.lastName", target = "lastName"),
    })
    MentorProfileUser toResponse(MentorProfile mentorProfile);
}
