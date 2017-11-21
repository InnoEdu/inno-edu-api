package inno.edu.api.domain.school.commands;

import inno.edu.api.domain.school.commands.dtos.UpdateSchoolRequest;
import inno.edu.api.domain.school.commands.mappers.UpdateSchoolRequestMapper;
import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.queries.GetSchoolByIdQuery;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateSchoolCommand {
    private final UpdateSchoolRequestMapper updateSchoolRequestMapper;

    private final SchoolRepository schoolRepository;
    private final GetSchoolByIdQuery getSchoolByIdQuery;

    public UpdateSchoolCommand(UpdateSchoolRequestMapper updateSchoolRequestMapper, SchoolRepository schoolRepository, GetSchoolByIdQuery getSchoolByIdQuery) {
        this.updateSchoolRequestMapper = updateSchoolRequestMapper;
        this.schoolRepository = schoolRepository;
        this.getSchoolByIdQuery = getSchoolByIdQuery;
    }

    public School run(UUID id, UpdateSchoolRequest updateSchoolRequest) {
        School currentSchool = getSchoolByIdQuery.run(id);
        updateSchoolRequestMapper.setSchool(updateSchoolRequest, currentSchool);
        return schoolRepository.save(currentSchool);
    }
}
