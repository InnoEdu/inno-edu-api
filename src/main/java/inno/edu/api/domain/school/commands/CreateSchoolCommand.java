package inno.edu.api.domain.school.commands;

import inno.edu.api.domain.school.commands.dtos.CreateSchoolRequest;
import inno.edu.api.domain.school.commands.mappers.CreateSchoolRequestToSchoolMapper;
import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateSchoolCommand {
    private final CreateSchoolRequestToSchoolMapper createSchoolRequestToSchoolMapper;
    private final SchoolRepository schoolRepository;

    public CreateSchoolCommand(CreateSchoolRequestToSchoolMapper createSchoolRequestToSchoolMapper, SchoolRepository schoolRepository) {
        this.createSchoolRequestToSchoolMapper = createSchoolRequestToSchoolMapper;
        this.schoolRepository = schoolRepository;
    }

    public School run(CreateSchoolRequest createSchoolRequest) {
        School school = createSchoolRequestToSchoolMapper.createSchoolRequestToSchool(createSchoolRequest);
        school.setId(randomUUID());
        return schoolRepository.save(school);
    }
}
