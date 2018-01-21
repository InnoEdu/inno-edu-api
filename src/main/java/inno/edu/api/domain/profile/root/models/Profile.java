package inno.edu.api.domain.profile.root.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.skill.models.ProfileSkill;
import inno.edu.api.domain.school.root.models.School;
import inno.edu.api.domain.user.root.models.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

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
    private UUID photoId;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "userId", updatable = false, insertable = false)
    private ApplicationUser user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "photoId", updatable = false, insertable = false)
    private Attachment photo;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "schoolId", updatable = false, insertable = false)
    private School school;

    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "profileId", updatable = false, insertable = false)
    @JsonIgnore
    private List<Service> services;

    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "profileId", updatable = false, insertable = false)
    @JsonIgnore
    private List<Interest> interests;

    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "profileId", updatable = false, insertable = false)
    @JsonIgnore
    private List<Accomplishment> accomplishments;

    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "profileId", updatable = false, insertable = false)
    @JsonIgnore
    private List<Experience> experiences;

    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "profileId", updatable = false, insertable = false)
    @JsonIgnore
    private List<ProfileAttachment> attachments;

    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "profileId", updatable = false, insertable = false)
    @JsonIgnore
    private List<ProfileSkill> skills;
}
