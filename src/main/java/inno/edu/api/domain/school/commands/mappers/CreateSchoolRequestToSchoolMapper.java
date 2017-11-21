package inno.edu.api.domain.school.commands.mappers;

import inno.edu.api.domain.school.commands.dtos.CreateSchoolRequest;
import inno.edu.api.domain.school.models.School;
import org.mapstruct.Mapper;

@Mapper
public interface CreateSchoolRequestToSchoolMapper {
    School createSchoolRequestToSchool(CreateSchoolRequest createSchoolRequest);
}
