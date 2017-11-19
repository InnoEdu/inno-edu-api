package inno.edu.api.domain.school.assertions;

import inno.edu.api.domain.school.exceptions.SchoolNotFoundException;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class SchoolExistsAssertion {
    private final SchoolRepository schoolRepository;

    public SchoolExistsAssertion(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public void run(UUID id) {
        if (!schoolRepository.exists(id)) {
                throw new SchoolNotFoundException(id);
        }
    }
}
