package inno.edu.api.domain.profile.service.commands.mappers;

import inno.edu.api.domain.profile.service.commands.dtos.UpdateServiceRequest;
import inno.edu.api.domain.profile.service.models.Service;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateServiceRequestMapper {
    void setService(UpdateServiceRequest updateServiceRequest, @MappingTarget Service experience);
}
