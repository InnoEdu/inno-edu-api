package inno.edu.api.domain.profile.commands.mappers;

import inno.edu.api.domain.school.commands.dtos.UpdateSchoolRequest;
import inno.edu.api.domain.school.models.School;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateMentorProfileRequestToMentorProfileMapper {
    void updateSchoolRequestToSchool(UpdateSchoolRequest updateSchoolRequest, @MappingTarget School school);
}
