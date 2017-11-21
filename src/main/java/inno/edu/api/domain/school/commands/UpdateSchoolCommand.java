package inno.edu.api.domain.school.commands;

import inno.edu.api.domain.school.commands.dtos.UpdateSchoolRequest;
import inno.edu.api.domain.school.commands.mappers.UpdateSchoolRequestToSchoolMapper;
import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.queries.GetSchoolByIdQuery;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateSchoolCommand {
    private final UpdateSchoolRequestToSchoolMapper updateSchoolRequestToSchoolMapper;

    private final SchoolRepository schoolRepository;
    private final GetSchoolByIdQuery getSchoolByIdQuery;

    public UpdateSchoolCommand(UpdateSchoolRequestToSchoolMapper updateSchoolRequestToSchoolMapper, SchoolRepository schoolRepository, GetSchoolByIdQuery getSchoolByIdQuery) {
        this.updateSchoolRequestToSchoolMapper = updateSchoolRequestToSchoolMapper;
        this.schoolRepository = schoolRepository;
        this.getSchoolByIdQuery = getSchoolByIdQuery;
    }

    public School run(UUID id, UpdateSchoolRequest updateSchoolRequest) {
        School currentSchool = getSchoolByIdQuery.run(id);
        updateSchoolRequestToSchoolMapper.updateSchoolRequestToSchool(updateSchoolRequest, currentSchool);
        return schoolRepository.save(currentSchool);
    }
}
