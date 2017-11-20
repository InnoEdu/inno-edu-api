package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.queries.GetMenteeProfileByUserIdQuery;
import inno.edu.api.domain.user.assertions.UserIsMenteeAssertion;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Query
public class GetAppointmentsByMenteeIdQuery {
    private final UserIsMenteeAssertion userIsMenteeAssertion;

    private final AppointmentRepository appointmentRepository;
    private final GetMenteeProfileByUserIdQuery getMenteeProfileByUserIdQuery;

    public GetAppointmentsByMenteeIdQuery(UserIsMenteeAssertion userIsMenteeAssertion, AppointmentRepository appointmentRepository, GetMenteeProfileByUserIdQuery getMenteeProfileByUserIdQuery) {
        this.userIsMenteeAssertion = userIsMenteeAssertion;
        this.appointmentRepository = appointmentRepository;
        this.getMenteeProfileByUserIdQuery = getMenteeProfileByUserIdQuery;
    }

    public List<Appointment> run(UUID menteeId, AppointmentStatus status) {
        userIsMenteeAssertion.run(menteeId);

        MenteeProfile menteeProfile = getMenteeProfileByUserIdQuery.run(menteeId);

        if (nonNull(status)) {
            return appointmentRepository.findByMenteeProfileIdAndStatus(menteeProfile.getId(), status);
        }
        return appointmentRepository.findByMenteeProfileId(menteeProfile.getId());
    }
}
