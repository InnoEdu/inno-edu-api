package inno.edu.api.domain.school.commands;

import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.queries.GetSchoolByIdQuery;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateSchoolCommand {
    private final SchoolRepository schoolRepository;
    private final GetSchoolByIdQuery getSchoolByIdQuery;

    public UpdateSchoolCommand(SchoolRepository schoolRepository, GetSchoolByIdQuery getSchoolByIdQuery) {
        this.schoolRepository = schoolRepository;
        this.getSchoolByIdQuery = getSchoolByIdQuery;
    }

    public School run(UUID id, School school) {
        School currentSchool = getSchoolByIdQuery.run(id);

        currentSchool.setName(school.getName());
        currentSchool.setDescription(school.getDescription());
        currentSchool.setPhotoUrl(school.getPhotoUrl());

        return schoolRepository.save(currentSchool);
    }
}
