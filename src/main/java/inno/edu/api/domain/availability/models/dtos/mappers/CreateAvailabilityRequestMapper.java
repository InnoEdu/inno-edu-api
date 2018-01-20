package inno.edu.api.domain.availability.models.dtos.mappers;

import inno.edu.api.domain.availability.models.dtos.CreateAvailabilityRequest;
import inno.edu.api.domain.availability.models.Availability;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAvailabilityRequestMapper {
    Availability toAvailability(CreateAvailabilityRequest createAvailabilityRequest);
}
