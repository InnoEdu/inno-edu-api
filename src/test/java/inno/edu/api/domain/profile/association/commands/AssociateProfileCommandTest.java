package inno.edu.api.domain.profile.association.commands;

import inno.edu.api.domain.profile.association.assertions.CheckPendingAssociationsAssertion;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.association.models.ProfileAssociation;
import inno.edu.api.domain.profile.association.repositories.ProfileAssociationRepository;
import inno.edu.api.domain.school.root.assertions.SchoolExistsAssertion;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.ProfileFactory.gustavoProfileAssociation;
import static inno.edu.api.support.ProfileFactory.gustavoToBerkeleyRequest;
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

        ProfileAssociation profileAssociation = associateProfileCommand.run(gustavoProfile().getId(), gustavoToBerkeleyRequest());
        assertThat(profileAssociation, is(expectedAssociation));
    }

    @Test
    public void shouldRunAllAssertions() {
        associateProfileCommand.run(gustavoProfile().getId(), gustavoToBerkeleyRequest());

        verify(profileExistsAssertion).run(gustavoProfile().getId());
        verify(checkPendingAssociationsAssertion).run(gustavoProfile().getId());

        verify(schoolExistsAssertion).run(gustavoToBerkeleyRequest().getSchoolId());
    }

}