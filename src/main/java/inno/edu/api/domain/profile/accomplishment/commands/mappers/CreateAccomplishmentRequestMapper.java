package inno.edu.api.domain.profile.accomplishment.commands.mappers;

import inno.edu.api.domain.profile.accomplishment.commands.dtos.CreateAccomplishmentRequest;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAccomplishmentRequestMapper {
    Accomplishment toAccomplishment(CreateAccomplishmentRequest createAccomplishmentRequest);
}
