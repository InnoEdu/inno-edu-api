package inno.edu.api.domain.school.root.models.dtos.mappers;

import inno.edu.api.domain.school.root.models.dtos.UpdateSchoolRequest;
import inno.edu.api.domain.school.root.models.School;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateSchoolRequestMapper {
    void setSchool(UpdateSchoolRequest updateSchoolRequest, @MappingTarget School school);
}
