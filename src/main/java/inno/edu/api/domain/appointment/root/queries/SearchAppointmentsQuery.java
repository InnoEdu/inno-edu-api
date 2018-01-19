package inno.edu.api.domain.appointment.root.queries;

import com.querydsl.core.types.dsl.BooleanExpression;
import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.models.QAppointment;
import inno.edu.api.domain.appointment.root.queries.dtos.SearchAppointmentsRequest;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Query
public class SearchAppointmentsQuery {
    private final AppointmentRepository appointmentRepository;

    public SearchAppointmentsQuery(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> run(SearchAppointmentsRequest request) {
        QAppointment query = QAppointment.appointment;

        BooleanExpression predicate = query.isNotNull();

        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            predicate = predicate.and(query.status.in(request.getStatus()));
        }

        return newArrayList(appointmentRepository.findAll(predicate));
    }
}
