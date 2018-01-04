package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.assertions.CheckPendingAssociationsAssertion;
import inno.edu.api.domain.profile.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.models.ProfileAssociation;
import inno.edu.api.domain.profile.repositories.ProfileAssociationRepository;
import inno.edu.api.domain.school.assertions.SchoolExistsAssertion;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.ProfileFactory.gustavoProfileAssociation;
import static inno.edu.api.support.ProfileFactory.gustavoToStanfordRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssociateProfileCommandTest {
    @Mock
    private ProfileAssociationRepository profileAssociationRepository;

    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private SchoolExistsAssertion schoolExistsAssertion;

    @Mock
    private CheckPendingAssociationsAssertion checkPendingAssociationsAssertion;

    @InjectMocks
    private AssociateProfileCommand associateProfileCommand;

    @Test
    public void shouldSaveProfileAssociation() {
        ProfileAssociation expectedAssociation = gustavoProfileAssociation();

        when(uuidGeneratorService.generate()).thenReturn(expectedAssociation.getId());
        when(profileAssociationRepository.save(any(ProfileAssociation.class))).then((answer) -> answer.getArguments()[0]);

        ProfileAssociation profileAssociation = associateProfileCommand.run(gustavoProfile().getId(), gustavoToStanfordRequest());
        assertThat(profileAssociation, is(expectedAssociation));
    }

    @Test
    public void shouldRunAllAssertions() {
        associateProfileCommand.run(gustavoProfile().getId(), gustavoToStanfordRequest());

        verify(profileExistsAssertion).run(gustavoProfile().getId());
        verify(checkPendingAssociationsAssertion).run(gustavoProfile().getId());

        verify(schoolExistsAssertion).run(gustavoProfile().getSchoolId());
    }

}