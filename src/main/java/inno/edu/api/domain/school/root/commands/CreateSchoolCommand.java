package inno.edu.api.domain.school.root.commands;

import inno.edu.api.domain.school.root.commands.dtos.CreateSchoolRequest;
import inno.edu.api.domain.school.root.commands.mappers.CreateSchoolRequestMapper;
import inno.edu.api.domain.school.root.models.School;
import inno.edu.api.domain.school.root.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

@Command
public class CreateSchoolCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateSchoolRequestMapper createSchoolRequestMapper;
    private final SchoolRepository schoolRepository;

    public CreateSchoolCommand(UUIDGeneratorService uuidGeneratorService, CreateSchoolRequestMapper createSchoolRequestMapper, SchoolRepository schoolRepository) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createSchoolRequestMapper = createSchoolRequestMapper;
        this.schoolRepository = schoolRepository;
    }

    public School run(CreateSchoolRequest createSchoolRequest) {
        School school = createSchoolRequestMapper.toSchool(createSchoolRequest);
        school.setId(uuidGeneratorService.generate());
        return schoolRepository.save(school);
    }
}
