package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.models.dtos.UpdateAvailabilityRequest;
import inno.edu.api.domain.availability.models.dtos.mappers.UpdateAvailabilityRequestMapper;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.queries.GetAvailabilityByIdQuery;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateAvailabilityCommand {
    private final UpdateAvailabilityRequestMapper updateAvailabilityRequestMapper;
    private final GetAvailabilityByIdQuery getAvailabilityByIdQuery;
    private final AvailabilityRepository availabilityRepository;

    public UpdateAvailabilityCommand(UpdateAvailabilityRequestMapper updateAvailabilityRequestMapper, GetAvailabilityByIdQuery getAvailabilityByIdQuery, AvailabilityRepository availabilityRepository) {
        this.updateAvailabilityRequestMapper = updateAvailabilityRequestMapper;
        this.getAvailabilityByIdQuery = getAvailabilityByIdQuery;
        this.availabilityRepository = availabilityRepository;
    }

    public Availability run(UUID id, UpdateAvailabilityRequest updateAvailabilityRequest) {
        Availability currentAvailability = getAvailabilityByIdQuery.run(id);
        updateAvailabilityRequestMapper.setAvailability(updateAvailabilityRequest, currentAvailability);
        return availabilityRepository.save(currentAvailability);
    }
}
