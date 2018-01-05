package inno.edu.api.domain.profile.association.assertions;

import inno.edu.api.domain.profile.association.assertions.CheckPendingAssociationsAssertion;
import inno.edu.api.domain.profile.association.exceptions.PendingAssociationExistsException;
import inno.edu.api.domain.profile.association.repositories.ProfileAssociationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.profile.association.models.RequestStatus.PENDING;
import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckPendingAssociationsAssertionTest {
    @Mock
    private ProfileAssociationRepository profileAssociationRepository;

    @InjectMocks
    private CheckPendingAssociationsAssertion checkPendingAssociationsAssertion;

    @Test
    public void shouldNotThrowExceptionIfNoPendingAssociationExist() {
        when(profileAssociationRepository.existsOneByProfileIdAndStatus(gustavoProfile().getId(), PENDING))
                .thenReturn(false);

        checkPendingAssociationsAssertion.run(gustavoProfile().getId());
    }

    @Test(expected = PendingAssociationExistsException.class)
    public void shouldThrowExceptionIfPendingAssociationExist() {
        when(profileAssociationRepository.existsOneByProfileIdAndStatus(gustavoProfile().getId(), PENDING))
                .thenReturn(true);

        checkPendingAssociationsAssertion.run(gustavoProfile().getId());
    }


}