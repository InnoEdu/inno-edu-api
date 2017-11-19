package inno.edu.api.domain.school.commands;

import inno.edu.api.domain.school.assertions.SchoolExistsAssertion;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteSchoolCommand {
    private final SchoolExistsAssertion schoolExistsAssertion;
    private final SchoolRepository schoolRepository;

    public DeleteSchoolCommand(SchoolExistsAssertion schoolExistsAssertion, SchoolRepository schoolRepository) {
        this.schoolExistsAssertion = schoolExistsAssertion;
        this.schoolRepository = schoolRepository;
    }

    public void run(UUID id) {
        schoolExistsAssertion.run(id);
        schoolRepository.delete(id);
    }
}
