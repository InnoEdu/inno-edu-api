package inno.edu.api.domain.school.commands;

import inno.edu.api.domain.school.exceptions.SchoolNotFoundException;
import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Command
public class UpdateSchoolCommand {
    private final SchoolRepository schoolRepository;

    public UpdateSchoolCommand(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public School run(UUID id, School school) {
        School currentSchool = ofNullable(schoolRepository.findOne(id))
                .orElseThrow(() -> new SchoolNotFoundException(id));

        currentSchool.setId(id);
        currentSchool.setName(school.getName());
        currentSchool.setDescription(school.getDescription());
        currentSchool.setPhotoUrl(school.getPhotoUrl());

        return schoolRepository.save(currentSchool);
    }
}
