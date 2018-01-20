package inno.edu.api.domain.profile.service.commands;

import inno.edu.api.domain.profile.service.models.dtos.UpdateServiceRequest;
import inno.edu.api.domain.profile.service.models.dtos.mappers.UpdateServiceRequestMapper;
import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.service.queries.GetServiceByIdQuery;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateServiceCommand {
    private final UpdateServiceRequestMapper updateServiceRequestMapper;

    private final GetServiceByIdQuery getServiceByIdQuery;
    private final ServiceRepository serviceRepository;

    public UpdateServiceCommand(UpdateServiceRequestMapper updateServiceRequestMapper, GetServiceByIdQuery getServiceByIdQuery, ServiceRepository serviceRepository) {
        this.updateServiceRequestMapper = updateServiceRequestMapper;
        this.getServiceByIdQuery = getServiceByIdQuery;
        this.serviceRepository = serviceRepository;
    }

    public Service run(UUID id, UpdateServiceRequest updateServiceRequest) {
        Service currentService = getServiceByIdQuery.run(id);
        updateServiceRequestMapper.setService(updateServiceRequest, currentService);
        return serviceRepository.save(currentService);
    }
}
