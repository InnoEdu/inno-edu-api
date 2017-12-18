package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.commands.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.infrastructure.annotations.Command;

import java.math.BigDecimal;

@Command
public class CalculateAppointmentFeeCommand {
    public BigDecimal run(CalculateAppointmentFeeRequest request) {
        return BigDecimal.ONE;
    }
}
