package inno.edu.api.domain.appointment.root.assertions;

import inno.edu.api.domain.appointment.root.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class AppointmentExistsAssertion extends EntityExistsAssertion<AppointmentRepository, AppointmentNotFoundException, Function<UUID, AppointmentNotFoundException>> {
    protected AppointmentExistsAssertion(AppointmentRepository repository) {
        super(repository, AppointmentNotFoundException::new);
    }
}
