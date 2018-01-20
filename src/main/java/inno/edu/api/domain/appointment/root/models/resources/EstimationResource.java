package inno.edu.api.domain.appointment.root.models.resources;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

@Getter
public class EstimationResource extends ResourceSupport {
    private final BigDecimal fee;

    public EstimationResource(BigDecimal fee) {
        this.fee = fee;
    }
}
