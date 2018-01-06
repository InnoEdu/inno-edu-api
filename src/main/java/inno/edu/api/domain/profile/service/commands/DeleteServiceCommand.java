package inno.edu.api.domain.profile.service.commands;

import inno.edu.api.domain.profile.service.assertions.ServiceExistsAssertion;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteServiceCommand {
    private final ServiceExistsAssertion serviceExistsAssertion;
    private final ServiceRepository serviceRepository;

    public DeleteServiceCommand(ServiceExistsAssertion serviceExistsAssertion, ServiceRepository serviceRepository) {
        this.serviceExistsAssertion = serviceExistsAssertion;
        this.serviceRepository = serviceRepository;
    }

    public void run(UUID id) {
        serviceExistsAssertion.run(id);
        serviceRepository.delete(id);
    }
}
