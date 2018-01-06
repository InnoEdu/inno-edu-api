package inno.edu.api.domain.profile.service.assertions;

import inno.edu.api.domain.profile.service.exceptions.ServiceNotFoundException;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;

@Assertion
public class ServiceExistsAssertion {
    private final ServiceRepository experienceRepository;

    public ServiceExistsAssertion(ServiceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public void run(UUID id) {
        if (!experienceRepository.exists(id)) {
            throw new ServiceNotFoundException(id);
        }
    }
}
