package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.user.queries.GetMentorActiveProfileByUserIdQuery;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Query
public class GetAppointmentsByMentorIdQuery {
    private final GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery;
    private final AppointmentRepository appointmentRepository;

    public GetAppointmentsByMentorIdQuery(GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery, AppointmentRepository appointmentRepository) {
        this.getMentorActiveProfileByUserIdQuery = getMentorActiveProfileByUserIdQuery;
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> run(UUID mentorId, AppointmentStatus status) {
        MentorProfile activeProfile = getMentorActiveProfileByUserIdQuery.run(mentorId);
        if (nonNull(status)) {
            return appointmentRepository.findByMentorProfileIdAndStatus(activeProfile.getId(), status);
        }
        return appointmentRepository.findByMentorProfileId(activeProfile.getId());
    }
}
