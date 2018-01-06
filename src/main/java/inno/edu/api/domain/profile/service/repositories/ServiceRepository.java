package inno.edu.api.domain.profile.service.repositories;

import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.service.models.ServicePrimaryKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceRepository extends CrudRepository<Service, ServicePrimaryKey> {
    List<Service> findByProfileId(UUID profileId);
}
