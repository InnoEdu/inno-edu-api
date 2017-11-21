package inno.edu.api.domain.school.commands;

import inno.edu.api.domain.school.commands.dtos.CreateSchoolRequest;
import inno.edu.api.domain.school.commands.mappers.CreateSchoolRequestMapper;
import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateSchoolCommand {
    private final CreateSchoolRequestMapper createSchoolRequestMapper;
    private final SchoolRepository schoolRepository;

    public CreateSchoolCommand(CreateSchoolRequestMapper createSchoolRequestMapper, SchoolRepository schoolRepository) {
        this.createSchoolRequestMapper = createSchoolRequestMapper;
        this.schoolRepository = schoolRepository;
    }

    public School run(CreateSchoolRequest createSchoolRequest) {
        School school = createSchoolRequestMapper.toSchool(createSchoolRequest);
        school.setId(randomUUID());
        return schoolRepository.save(school);
    }
}
