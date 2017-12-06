package inno.edu.api.domain.availability.commands.mappers;

import inno.edu.api.domain.availability.commands.dtos.CreateAvailabilityByMentorIdRequest;
import inno.edu.api.domain.availability.models.Availability;
import org.mapstruct.Mapper;

@Mapper
public interface CreateAvailabilityByMentorIdRequestMapper {
    Availability toAvailability(CreateAvailabilityByMentorIdRequest createAvailabilityByMentorIdRequest);
}
