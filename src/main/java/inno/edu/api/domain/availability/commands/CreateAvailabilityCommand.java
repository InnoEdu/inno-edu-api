package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.university.exceptions.UniversityNotFoundException;
import inno.edu.api.domain.university.repositories.UniversityRepository;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;

@Component
public class CreateAvailabilityCommand {
    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;

    public CreateAvailabilityCommand(AvailabilityRepository availabilityRepository, UserRepository userRepository, UniversityRepository universityRepository) {
        this.availabilityRepository = availabilityRepository;
        this.userRepository = userRepository;
        this.universityRepository = universityRepository;
    }

    public Availability run(Availability availability) {
        if (!userRepository.exists(availability.getUserId())) {
            throw new UserNotFoundException(availability.getUserId());
        }
        if (!universityRepository.exists(availability.getUniversityId())) {
            throw new UniversityNotFoundException(availability.getUserId());
        }

        availability.setId(randomUUID());
        return availabilityRepository.save(availability);
    }
}
