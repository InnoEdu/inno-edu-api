package inno.edu.api.domain.appointment.assertions;

import inno.edu.api.domain.appointment.exceptions.FeedbackNotFoundException;
import inno.edu.api.domain.appointment.repositories.FeedbackRepository;
import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class FeedbackExistsAssertion extends EntityExistsAssertion<FeedbackRepository, FeedbackNotFoundException, Function<UUID, FeedbackNotFoundException>> {
    protected FeedbackExistsAssertion(FeedbackRepository repository) {
        super(repository, FeedbackNotFoundException::new);
    }
}
