package inno.edu.api.domain.profile.service.models.dtos.mappers;

import inno.edu.api.domain.profile.service.models.dtos.UpdateServiceRequest;
import inno.edu.api.domain.profile.service.models.Service;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateServiceRequestMapper {
    void setService(UpdateServiceRequest updateServiceRequest, @MappingTarget Service experience);
}
