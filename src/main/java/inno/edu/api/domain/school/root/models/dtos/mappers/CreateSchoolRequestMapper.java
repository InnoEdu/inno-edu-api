package inno.edu.api.domain.school.root.models.dtos.mappers;

import inno.edu.api.domain.school.root.models.dtos.CreateSchoolRequest;
import inno.edu.api.domain.school.root.models.School;
import org.mapstruct.Mapper;

@Mapper
public interface CreateSchoolRequestMapper {
    School toSchool(CreateSchoolRequest createSchoolRequest);
}
