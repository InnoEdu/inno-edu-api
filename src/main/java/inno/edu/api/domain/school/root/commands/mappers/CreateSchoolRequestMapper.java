package inno.edu.api.domain.school.root.commands.mappers;

import inno.edu.api.domain.school.root.commands.dtos.CreateSchoolRequest;
import inno.edu.api.domain.school.root.models.School;
import org.mapstruct.Mapper;

@Mapper
public interface CreateSchoolRequestMapper {
    School toSchool(CreateSchoolRequest createSchoolRequest);
}
