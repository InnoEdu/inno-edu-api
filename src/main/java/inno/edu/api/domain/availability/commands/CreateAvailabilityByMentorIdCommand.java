package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.commands.dtos.CreateAvailabilityByMentorIdRequest;
import inno.edu.api.domain.availability.commands.mappers.CreateAvailabilityByMentorIdRequestMapper;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.queries.GetMentorActiveProfileByUserIdQuery;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Command
public class CreateAvailabilityByMentorIdCommand {
    private final CreateAvailabilityByMentorIdRequestMapper createAvailabilityRequestMapper;
    private final GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery;

    private final AvailabilityRepository availabilityRepository;

    public CreateAvailabilityByMentorIdCommand(CreateAvailabilityByMentorIdRequestMapper createAvailabilityRequestMapper, GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery, AvailabilityRepository availabilityRepository) {
        this.createAvailabilityRequestMapper = createAvailabilityRequestMapper;
        this.getMentorActiveProfileByUserIdQuery = getMentorActiveProfileByUserIdQuery;
        this.availabilityRepository = availabilityRepository;
    }

    public Availability run(UUID mentorId, CreateAvailabilityByMentorIdRequest createAvailabilityRequest) {
        MentorProfile mentorProfile = getMentorActiveProfileByUserIdQuery.run(mentorId);

        Availability availability = createAvailabilityRequestMapper.toAvailability(createAvailabilityRequest);
        availability.setMentorProfileId(mentorProfile.getId());
        availability.setId(randomUUID());

        return availabilityRepository.save(availability);
    }
}
