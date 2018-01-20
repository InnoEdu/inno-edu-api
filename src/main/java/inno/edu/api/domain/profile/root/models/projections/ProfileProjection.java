package inno.edu.api.domain.profile.root.models.projections;

import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.skill.models.ProfileSkill;
import inno.edu.api.domain.school.root.models.School;
import inno.edu.api.domain.user.models.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileProjection {
    @Id
    private UUID id;
    private String description;

    private BigDecimal rate;
    private String location;
    private String company;

    private boolean isMentor;

    private ApplicationUser user;
    private Attachment photo;
    private School school;
    private List<Service> services;
    private List<Interest> interests;
    private List<Accomplishment> accomplishments;
    private List<Experience> experiences;
    private List<ProfileAttachment> attachments;
    private List<ProfileSkill> skills;
}
