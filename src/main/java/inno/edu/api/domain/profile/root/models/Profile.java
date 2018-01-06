package inno.edu.api.domain.profile.root.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    private UUID id;
    private UUID userId;
    private String description;

    private UUID schoolId;
    private BigDecimal rate;
    private String location;
    private String company;

    private UUID profileReferenceId;
}
