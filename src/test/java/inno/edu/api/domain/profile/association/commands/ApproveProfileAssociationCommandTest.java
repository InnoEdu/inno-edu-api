package inno.edu.api.domain.profile.association.commands;

import inno.edu.api.domain.profile.association.assertions.ProfileAssociationExistsAssertion;
import inno.edu.api.domain.profile.association.commands.ApproveProfileAssociationCommand;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.association.models.ProfileAssociation;
import inno.edu.api.domain.profile.association.repositories.ProfileAssociationRepository;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.profile.association.models.RequestStatus.APPROVED;
import static inno.edu.api.support.ProfileFactory.approveRequest;
import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.ProfileFactory.gustavoProfileAssociation;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApproveProfileAssociationCommandTest {
    @Mock
    private ProfileAssociationExistsAssertion profileAssociationExistsAssertion;

    @Mock
    private ProfileAssociationRepository profileAssociationRepository;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ApproveProfileAssociationCommand approveProfileAssociationCommand;

    @Before
    public void setUp() {
        when(profileAssociationRepository.findOne(gustavoProfileAssociation().getId()))
                .thenReturn(gustavoProfileAssociation());

        when(profileRepository.findOne(gustavoProfileAssociation().getProfileId()))
                .thenReturn(gustavoProfile());

        when(profileAssociationRepository.save(any(ProfileAssociation.class)))
                .then((answer) -> answer.getArguments()[0]);
    }

    @Test
    public void shouldUpdateAssociation() {
        ProfileAssociation expectedProfileAssociation = gustavoProfileAssociation().toBuilder()
                .status(APPROVED)
                .build();

        ProfileAssociation profileAssociation = approveProfileAssociationCommand.run(gustavoProfileAssociation().getId(), approveRequest());

        assertThat(profileAssociation, is (expectedProfileAssociation));
    }

    @Test
    public void shouldUpdateProfile() {
        ArgumentCaptor<Profile> argumentCaptor = ArgumentCaptor.forClass(Profile.class);

        when(profileRepository.save(argumentCaptor.capture())).thenReturn(gustavoProfile());

        approveProfileAssociationCommand.run(gustavoProfileAssociation().getId(), approveRequest());

        Profile expectedProfile = gustavoProfile().toBuilder()
                .rate(approveRequest().getRate())
                .schoolId(gustavoProfileAssociation().getSchoolId())
                .build();

        assertThat(argumentCaptor.getValue(), is (expectedProfile));
    }

    @Test
    public void shouldRunAllAssertions() {
        approveProfileAssociationCommand.run(gustavoProfileAssociation().getId(), approveRequest());

        verify(profileAssociationExistsAssertion).run(gustavoProfileAssociation().getId());
    }
}