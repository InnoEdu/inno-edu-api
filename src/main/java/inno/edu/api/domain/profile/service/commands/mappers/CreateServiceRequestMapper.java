package inno.edu.api.domain.profile.service.commands.mappers;

import inno.edu.api.domain.profile.service.commands.dtos.CreateServiceRequest;
import inno.edu.api.domain.profile.service.models.Service;
import org.mapstruct.Mapper;

@Mapper
public interface CreateServiceRequestMapper {
    Service toService(CreateServiceRequest createServiceRequest);
}
