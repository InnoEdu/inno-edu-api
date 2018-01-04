package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.assertions.ProfileAssociationExistsAssertion;
import inno.edu.api.domain.profile.models.ProfileAssociation;
import inno.edu.api.domain.profile.repositories.ProfileAssociationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.profile.models.RequestStatus.REJECTED;
import static inno.edu.api.support.ProfileFactory.gustavoProfileAssociation;
import static inno.edu.api.support.ProfileFactory.rejectRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RejectProfileAssociationCommandTest {
    @Mock
    private ProfileAssociationExistsAssertion profileAssociationExistsAssertion;

    @Mock
    private ProfileAssociationRepository profileAssociationRepository;

    @InjectMocks
    private RejectProfileAssociationCommand rejectProfileAssociationCommand;

    @Before
    public void setUp() {
        when(profileAssociationRepository.findOne(gustavoProfileAssociation().getId()))
                .thenReturn(gustavoProfileAssociation());
    }

    @Test
    public void shouldUpdateAssociation() {
        when(profileAssociationRepository.save(any(ProfileAssociation.class)))
                .then((answer) -> answer.getArguments()[0]);

        ProfileAssociation expectedProfileAssociation = gustavoProfileAssociation().toBuilder()
                .status(REJECTED)
                .description(rejectRequest().getDescription())
                .build();

        ProfileAssociation profileAssociation = rejectProfileAssociationCommand.run(gustavoProfileAssociation().getId(), rejectRequest());

        assertThat(profileAssociation, is(expectedProfileAssociation));
    }

    @Test
    public void shouldRunAllAssertions() {
        rejectProfileAssociationCommand.run(gustavoProfileAssociation().getId(), rejectRequest());

        verify(profileAssociationExistsAssertion).run(gustavoProfileAssociation().getId());
    }
}