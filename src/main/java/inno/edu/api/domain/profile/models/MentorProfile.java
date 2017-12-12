package inno.edu.api.domain.profile.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inno.edu.api.domain.user.models.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MentorProfile {
    @Id
    private UUID id;

    private UUID mentorId;
    private UUID schoolId;
    private String email;
    private String description;

    @Enumerated
    private ProfileStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mentorId", updatable = false, insertable = false)
    @JsonIgnore
    private ApplicationUser user;
}
