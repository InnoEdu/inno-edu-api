package inno.edu.api.domain.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Table(name = "user")
@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser {
    @Id
    private UUID id;

    private String firstName;
    private String lastName;

    private String username;
    private String email;

    @JsonProperty(access = WRITE_ONLY)
    private String password;
}
