package inno.edu.api.domain.appointment.root.models.dtos;

import inno.edu.api.domain.appointment.root.models.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppointmentStatusRequest {
    @Size(max = 255)
    private String reason;

    @NotNull
    private AppointmentStatus status;
}
