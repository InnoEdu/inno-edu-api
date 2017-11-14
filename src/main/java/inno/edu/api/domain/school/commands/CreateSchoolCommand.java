package inno.edu.api.domain.school.commands;

import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateSchoolCommand {
    private final SchoolRepository schoolRepository;

    public CreateSchoolCommand(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public School run(School school) {
        school.setId(randomUUID());
        return schoolRepository.save(school);
    }
}
