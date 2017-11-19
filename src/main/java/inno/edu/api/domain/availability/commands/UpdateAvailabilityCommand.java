package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.queries.GetAvailabilityByIdQuery;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateAvailabilityCommand {
    private final GetAvailabilityByIdQuery getAvailabilityByIdQuery;
    private final AvailabilityRepository availabilityRepository;

    public UpdateAvailabilityCommand(GetAvailabilityByIdQuery getAvailabilityByIdQuery, AvailabilityRepository availabilityRepository) {
        this.getAvailabilityByIdQuery = getAvailabilityByIdQuery;
        this.availabilityRepository = availabilityRepository;
    }

    public Availability run(UUID id, Availability availability) {
        Availability currentAvailability = getAvailabilityByIdQuery.run(id);

        currentAvailability.setFromDateTime(availability.getFromDateTime());
        currentAvailability.setToDateTime(availability.getToDateTime());

        return availabilityRepository.save(currentAvailability);
    }
}
