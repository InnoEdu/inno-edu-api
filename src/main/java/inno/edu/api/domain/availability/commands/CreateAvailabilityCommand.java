package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
public class CreateAvailabilityCommand {
    private final AvailabilityRepository availabilityRepository;
    private final MentorProfileRepository mentorProfileRepository;

    public CreateAvailabilityCommand(AvailabilityRepository availabilityRepository, UserRepository userRepository, SchoolRepository schoolRepository, MentorProfileRepository mentorProfileRepository) {
        this.availabilityRepository = availabilityRepository;
        this.mentorProfileRepository = mentorProfileRepository;
    }

    public Availability run(Availability availability) {
        if (!mentorProfileRepository.exists(availability.getMentorProfileId())) {
            throw new ProfileNotFoundException(availability.getMentorProfileId());
        }

        availability.setId(randomUUID());
        return availabilityRepository.save(availability);
    }
}
