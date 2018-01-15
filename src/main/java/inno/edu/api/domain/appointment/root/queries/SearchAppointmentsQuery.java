package inno.edu.api.domain.appointment.root.queries;

import com.querydsl.core.types.Predicate;
import inno.edu.api.domain.appointment.root.models.QAppointment;
import inno.edu.api.domain.appointment.root.queries.dto.SearchAppointmentsRequest;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import inno.edu.api.infrastructure.annotations.Query;

@Query
public class SearchAppointmentsQuery {
    private final AppointmentRepository appointmentRepository;

    public SearchAppointmentsQuery(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void run(SearchAppointmentsRequest request) {
        QAppointment query = QAppointment.appointment;

        Predicate booleanExpression = query.status.in(request.getStatus());

        appointmentRepository.findAll(booleanExpression);
    }
}
