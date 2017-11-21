package inno.edu.api.domain.availability.commands.mappers;

import inno.edu.api.domain.availability.commands.dtos.CreateAvailabilityRequest;
import inno.edu.api.domain.availability.models.Availability;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAvailabilityRequestMapper {
    Availability toAvailability(CreateAvailabilityRequest createAvailabilityRequest);
}
