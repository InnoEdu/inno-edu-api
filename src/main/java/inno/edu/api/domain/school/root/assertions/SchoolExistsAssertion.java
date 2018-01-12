package inno.edu.api.domain.school.root.assertions;

import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.domain.school.root.exceptions.SchoolNotFoundException;
import inno.edu.api.domain.school.root.repositories.SchoolRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class SchoolExistsAssertion extends EntityExistsAssertion<SchoolRepository, SchoolNotFoundException, Function<UUID, SchoolNotFoundException>> {
    protected SchoolExistsAssertion(SchoolRepository repository) {
        super(repository, SchoolNotFoundException::new);
    }
}
