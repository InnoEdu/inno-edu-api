package inno.edu.api.domain.user.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
public class User {
    @Id
    private long id;

    private String firstName;
    private String lastName;
}
