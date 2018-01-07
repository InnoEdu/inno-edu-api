package inno.edu.api.domain.profile.service.assertions;

import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.domain.profile.service.exceptions.ServiceNotFoundException;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class ServiceExistsAssertion extends EntityExistsAssertion<ServiceRepository, ServiceNotFoundException, Function<UUID, ServiceNotFoundException>> {
    protected ServiceExistsAssertion(ServiceRepository repository) {
        super(repository, ServiceNotFoundException::new);
    }
}