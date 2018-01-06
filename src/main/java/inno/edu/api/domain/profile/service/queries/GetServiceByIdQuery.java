package inno.edu.api.domain.profile.service.queries;

import inno.edu.api.domain.profile.service.exceptions.ServiceNotFoundException;
import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Query
public class GetServiceByIdQuery {
    private final ServiceRepository experienceRepository;

    public GetServiceByIdQuery(ServiceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public Service run(UUID id) {
        return ofNullable(experienceRepository.findOne(id))
                .orElseThrow(() -> new ServiceNotFoundException(id));
    }
}
