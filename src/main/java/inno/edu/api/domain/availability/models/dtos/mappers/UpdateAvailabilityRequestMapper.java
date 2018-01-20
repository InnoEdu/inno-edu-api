package inno.edu.api.domain.availability.models.dtos.mappers;

import inno.edu.api.domain.availability.models.dtos.UpdateAvailabilityRequest;
import inno.edu.api.domain.availability.models.Availability;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateAvailabilityRequestMapper {
    void setAvailability(UpdateAvailabilityRequest updateAvailabilityRequest, @MappingTarget Availability availability);
}
