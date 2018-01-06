package inno.edu.api.domain.profile.service.queries;

import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

@Query
public class GetServicesByProfileIdQuery {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final ServiceRepository serviceRepository;

    public GetServicesByProfileIdQuery(ProfileExistsAssertion profileExistsAssertion, ServiceRepository serviceRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.serviceRepository = serviceRepository;
    }

    public List<Service> run(UUID profileId) {
        profileExistsAssertion.run(profileId);
        return serviceRepository.findByProfileId(profileId);
    }
}
