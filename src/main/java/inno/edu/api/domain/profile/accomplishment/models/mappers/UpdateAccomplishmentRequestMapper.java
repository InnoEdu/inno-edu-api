package inno.edu.api.domain.profile.accomplishment.models.mappers;

import inno.edu.api.domain.profile.accomplishment.models.dtos.UpdateAccomplishmentRequest;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateAccomplishmentRequestMapper {
    void setAccomplishment(UpdateAccomplishmentRequest updateAccomplishmentRequest, @MappingTarget Accomplishment accomplishment);
}
