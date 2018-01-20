package inno.edu.api.domain.appointment.root.models.projections.mappers;

import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.models.resources.AppointmentProjectionResource;
import inno.edu.api.domain.appointment.root.models.projections.AppointmentProjection;
import inno.edu.api.domain.appointment.root.models.projections.mappers.decorators.AppointmentProjectionMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(AppointmentProjectionMapperDecorator.class)
public interface AppointmentProjectionMapper {
    @Mapping(target = "mentorFirstName", source = "mentorProfile.user.firstName")
    @Mapping(target = "mentorLastName", source = "mentorProfile.user.lastName")
    @Mapping(target = "menteeFirstName", source = "menteeProfile.user.firstName")
    @Mapping(target = "menteeLastName", source = "menteeProfile.user.lastName")
    @Mapping(target = "mentorPhotoUrl", source = "mentorProfile.photo.url")
    @Mapping(target = "menteePhotoUrl", source = "menteeProfile.photo.url")
    AppointmentProjection toAppointmentProjection(Appointment appointment);

    AppointmentProjectionResource toAppointmentProjectionResource(Appointment appointment);
}
