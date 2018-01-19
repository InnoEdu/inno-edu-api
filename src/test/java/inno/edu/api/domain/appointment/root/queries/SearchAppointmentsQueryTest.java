package inno.edu.api.domain.appointment.root.queries;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import inno.edu.api.domain.appointment.root.models.QAppointment;
import inno.edu.api.domain.appointment.root.queries.dtos.SearchAppointmentsRequest;
import inno.edu.api.domain.appointment.root.repositories.AppointmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.collect.Lists.newArrayList;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.CANCELED;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.support.AppointmentFactory.appointments;
import static inno.edu.api.support.AppointmentFactory.searchAppointmentsByStatus;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchAppointmentsQueryTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private SearchAppointmentsQuery searchAppointmentsQuery;

    @Before
    public void setUp() {
        // The important part of the class is the predicate building, not the returned value
        when(appointmentRepository.findAll(any(Predicate.class)))
                .thenReturn(appointments());
    }

    @Test
    public void shouldSearchByEmptyQuery() {
        SearchAppointmentsRequest request = SearchAppointmentsRequest.builder().build();
        searchAppointmentsQuery.run(request);

        BooleanExpression expectedPredicate = QAppointment.appointment.isNotNull();
        verify(appointmentRepository).findAll(expectedPredicate);
    }

    @Test
    public void shouldSearchByStatus() {
        searchAppointmentsQuery.run(searchAppointmentsByStatus());

        BooleanExpression expectedPredicate = QAppointment.appointment.isNotNull()
                .and(QAppointment.appointment.status.in(newArrayList(PROPOSED, CANCELED)));

        verify(appointmentRepository).findAll(expectedPredicate);
    }
}