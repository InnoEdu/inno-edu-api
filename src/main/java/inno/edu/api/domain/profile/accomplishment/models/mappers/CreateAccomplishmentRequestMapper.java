package inno.edu.api.domain.profile.accomplishment.models.mappers;

import inno.edu.api.domain.profile.accomplishment.models.dtos.CreateAccomplishmentRequest;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAccomplishmentRequestMapper {
    Accomplishment toAccomplishment(CreateAccomplishmentRequest createAccomplishmentRequest);
}
