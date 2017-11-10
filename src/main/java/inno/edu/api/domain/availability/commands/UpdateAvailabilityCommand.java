package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.exceptions.AvailabilityNotFoundException;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Component
public class UpdateAvailabilityCommand {
    private final AvailabilityRepository availabilityRepository;

    public UpdateAvailabilityCommand(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public Availability run(UUID id, Availability availability) {
        Availability currentAvailability = ofNullable(availabilityRepository.findOne(id))
                .orElseThrow(() -> new AvailabilityNotFoundException(id));

        currentAvailability.setId(id);
        currentAvailability.setFromDateTime(availability.getFromDateTime());
        currentAvailability.setToDateTime(availability.getToDateTime());

        return availabilityRepository.save(currentAvailability);
    }
}
