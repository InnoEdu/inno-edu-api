package inno.edu.api.domain.appointment.root.models.projections.mappers.decorators;

import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.models.projections.AppointmentProjection;
import inno.edu.api.domain.appointment.root.models.projections.mappers.AppointmentProjectionMapper;
import inno.edu.api.domain.appointment.root.models.resources.AppointmentProjectionResource;
import inno.edu.api.domain.appointment.root.models.resources.FeedbackResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class AppointmentProjectionMapperDecorator implements AppointmentProjectionMapper {

    @Autowired
    @Qualifier("delegate")
    private AppointmentProjectionMapper delegate;

    @Override
    public AppointmentProjection toAppointmentProjection(Appointment appointment) {
        AppointmentProjection projection = delegate.toAppointmentProjection(appointment);

        List<FeedbackResource> feedbacks = new ArrayList<>();
        if (appointment.getFeedbacks() != null) {
            feedbacks = appointment.getFeedbacks().stream()
                    .map(FeedbackResource::new)
                    .collect(toList());
        }

        projection.setFeedbacks(feedbacks);

        return projection;
    }

    @Override
    public AppointmentProjectionResource toAppointmentProjectionResource(Appointment appointment) {
        return new AppointmentProjectionResource(toAppointmentProjection(appointment));
    }

    @Override
    public List<AppointmentProjection> toAppointmentProjections(List<Appointment> appointments) {
        if (appointments == null) {
            return null;
        }
        return appointments.stream()
                .map(this::toAppointmentProjection)
                .collect(toList());
    }
}