package inno.edu.api.domain.profile.accomplishment.assertions;

import inno.edu.api.domain.profile.accomplishment.exceptions.AccomplishmentNotFoundException;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class AccomplishmentExistsAssertion {
    private final AccomplishmentRepository accomplishmentRepository;

    public AccomplishmentExistsAssertion(AccomplishmentRepository accomplishmentRepository) {
        this.accomplishmentRepository = accomplishmentRepository;
    }

    public void run(UUID id) {
        if (!accomplishmentRepository.exists(id)) {
            throw new AccomplishmentNotFoundException(id);
        }
    }
}
