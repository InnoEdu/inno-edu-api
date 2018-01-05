package inno.edu.api.domain.profile.association.queries;

import inno.edu.api.domain.profile.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.association.queries.GetAssociationsByProfileIdQuery;
import inno.edu.api.domain.profile.association.models.ProfileAssociation;
import inno.edu.api.domain.profile.association.repositories.ProfileAssociationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.domain.profile.association.models.RequestStatus.PENDING;
import static inno.edu.api.support.ProfileFactory.associations;
import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.ProfileFactory.gustavoProfileAssociation;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAssociationsByProfileIdQueryTest {
    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private ProfileAssociationRepository profileAssociationRepository;

    @InjectMocks
    private GetAssociationsByProfileIdQuery getAssociationsByProfileIdQuery;

    @Before
    public void setUp() {
        when(profileAssociationRepository.findByProfileId(gustavoProfile().getId()))
                .thenReturn(associations());

        when(profileAssociationRepository.findByProfileIdAndStatus(gustavoProfile().getId(), gustavoProfileAssociation().getStatus()))
                .thenReturn(associations());
    }

    @Test
    public void shouldCallRepositoryForProfileId() {
        List<ProfileAssociation> expected = getAssociationsByProfileIdQuery.run(gustavoProfile().getId(), null);

        verify(profileAssociationRepository).findByProfileId(gustavoProfile().getId());

        assertThat(expected, is(associations()));
    }

    @Test
    public void shouldCallRepositoryForProfileIdAndStatus() {
        List<ProfileAssociation> expected = getAssociationsByProfileIdQuery.run(gustavoProfile().getId(), PENDING);

        verify(profileAssociationRepository).findByProfileIdAndStatus(gustavoProfile().getId(), PENDING);

        assertThat(expected, is(associations()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getAssociationsByProfileIdQuery.run(gustavoProfile().getId(), PENDING);

        verify(profileExistsAssertion).run(gustavoProfile().getId());
    }
}