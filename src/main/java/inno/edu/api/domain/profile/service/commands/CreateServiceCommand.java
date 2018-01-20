package inno.edu.api.domain.profile.service.commands;

import inno.edu.api.domain.profile.service.models.dtos.CreateServiceRequest;
import inno.edu.api.domain.profile.service.models.dtos.mappers.CreateServiceRequestMapper;
import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import java.util.UUID;

@Command
public class CreateServiceCommand {
    private final UUIDGeneratorService uuidGeneratorService;

    private final CreateServiceRequestMapper createServiceRequestMapper;
    private final ProfileExistsAssertion profileExistsAssertion;

    private final ServiceRepository serviceRepository;

    public CreateServiceCommand(UUIDGeneratorService uuidGeneratorService, CreateServiceRequestMapper createServiceRequestMapper, ProfileExistsAssertion profileExistsAssertion, ServiceRepository serviceRepository) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createServiceRequestMapper = createServiceRequestMapper;
        this.profileExistsAssertion = profileExistsAssertion;
        this.serviceRepository = serviceRepository;
    }

    public Service run(UUID profileId, CreateServiceRequest createServiceRequest) {
        profileExistsAssertion.run(profileId);

        Service service = createServiceRequestMapper.toService(createServiceRequest);
        service.setId(uuidGeneratorService.generate());
        service.setProfileId(profileId);

        return serviceRepository.save(service);
    }
}
