package inno.edu.api.domain.profile.association.repositories;

import inno.edu.api.domain.profile.association.models.ProfileAssociation;
import inno.edu.api.domain.profile.association.models.RequestStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProfileAssociationRepository extends CrudRepository<ProfileAssociation, UUID> {
    boolean existsOneByProfileIdAndStatus(UUID profileId, RequestStatus status);

    List<ProfileAssociation> findByProfileIdAndStatus(UUID profileId, RequestStatus status);
    List<ProfileAssociation> findByProfileId(UUID profileId);
}
