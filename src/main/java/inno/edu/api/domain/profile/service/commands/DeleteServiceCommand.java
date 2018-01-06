package inno.edu.api.domain.profile.service.commands;

import inno.edu.api.domain.profile.service.assertions.ServiceExistsAssertion;
import inno.edu.api.domain.profile.service.models.ServicePrimaryKey;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteServiceCommand {
    private final ServiceExistsAssertion experienceExistsAssertion;
    private final ServiceRepository experienceRepository;

    public DeleteServiceCommand(ServiceExistsAssertion experienceExistsAssertion, ServiceRepository experienceRepository) {
        this.experienceExistsAssertion = experienceExistsAssertion;
        this.experienceRepository = experienceRepository;
    }

    public void run(UUID id) {
        experienceExistsAssertion.run(id);
        experienceRepository.delete(new ServicePrimaryKey(id, id));
    }
}
