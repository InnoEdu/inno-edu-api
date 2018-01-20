package inno.edu.api.domain.appointment.root.models.dtos;

import inno.edu.api.domain.appointment.root.models.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SearchAppointmentsRequest {
    List<AppointmentStatus> status;

}
