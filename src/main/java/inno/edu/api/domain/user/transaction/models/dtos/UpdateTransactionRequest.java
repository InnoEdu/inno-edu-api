package inno.edu.api.domain.user.transaction.models.dtos;

import inno.edu.api.domain.user.transaction.models.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTransactionRequest {
    @NotNull
    private BigDecimal value;

    @NotNull
    private TransactionType type;
}
