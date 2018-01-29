package inno.edu.api.infrastructure.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureConfiguration {
    private boolean userBalanceValidation = true;
}
