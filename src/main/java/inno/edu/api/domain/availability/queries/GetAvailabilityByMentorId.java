package inno.edu.api.domain.availability.queries;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.queries.GetMentorActiveProfileByUserIdQuery;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

@Query
public class GetAvailabilityByMentorId {
    private final AvailabilityRepository availabilityRepository;
    private final GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery;

    public GetAvailabilityByMentorId(AvailabilityRepository availabilityRepository, GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery) {
        this.availabilityRepository = availabilityRepository;
        this.getMentorActiveProfileByUserIdQuery = getMentorActiveProfileByUserIdQuery;
    }

    public List<Availability> run(UUID mentorId) {
        MentorProfile profile = getMentorActiveProfileByUserIdQuery.run(mentorId);
        return availabilityRepository.findByMentorProfileId(profile.getId());
    }
}
