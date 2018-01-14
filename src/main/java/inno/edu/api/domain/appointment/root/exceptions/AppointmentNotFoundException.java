package inno.edu.api.domain.appointment.root.exceptions;

import java.util.UUID;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(UUID appointmentId) {
        super("Could not find appointment '" + appointmentId.toString() + "'.");
    }
}