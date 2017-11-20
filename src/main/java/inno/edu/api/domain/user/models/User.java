package inno.edu.api.domain.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private UUID id;

    private String firstName;
    private String lastName;

    private String username;
    private String email;

    @JsonProperty(access = WRITE_ONLY)
    private String password;

    private String photoUrl;

    private Boolean isMentor;
}
