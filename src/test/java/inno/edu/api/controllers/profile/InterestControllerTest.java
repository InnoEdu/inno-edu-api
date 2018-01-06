package inno.edu.api.controllers.profile;

import inno.edu.api.controllers.profile.resources.InterestResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.interest.commands.CreateInterestCommand;
import inno.edu.api.domain.profile.interest.commands.DeleteInterestCommand;
import inno.edu.api.domain.profile.interest.commands.UpdateInterestCommand;
import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.interest.queries.GetInterestByIdQuery;
import inno.edu.api.domain.profile.interest.queries.GetInterestsByProfileIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.ProfileFactory.createFeiInterestRequest;
import static inno.edu.api.support.ProfileFactory.feiInterest;
import static inno.edu.api.support.ProfileFactory.feiInterests;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.updateFeiInterestRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiInterest;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class InterestControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private GetInterestByIdQuery getInterestByIdQuery;

    @Mock
    private GetInterestsByProfileIdQuery getInterestsByProfileIdQuery;

    @Mock
    private CreateInterestCommand createInterestCommand;

    @Mock
    private UpdateInterestCommand updateInterestCommand;

    @Mock
    private DeleteInterestCommand deleteInterestCommand;

    @InjectMocks
    private InterestController interestController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldListInterestsForProfile() {
        when(getInterestsByProfileIdQuery.run(feiProfile().getId())).thenReturn(feiInterests());

        interestController.all(feiProfile().getId());

        verify(resourceBuilder).wrappedFrom(eq(feiInterests()), any(), eq(InterestResource.class));
    }

    @Test
    public void shouldGetInterestById() {
        when(getInterestByIdQuery.run(eq(feiInterest().getId()))).thenReturn(feiInterest());

        InterestResource interestResource = interestController.get(feiInterest().getId());

        assertThat(interestResource.getInterest(), is(feiInterest()));
    }

    @Test
    public void shouldCreateNewInterest() {
        when(createInterestCommand.run(feiProfile().getId(), createFeiInterestRequest())).thenReturn(feiInterest());

        ResponseEntity<Interest> entity = interestController.post(feiProfile().getId(), createFeiInterestRequest());

        assertThat(entity.getBody(), is(feiInterest()));
    }

    @Test
    public void shouldUpdateInterest() {
        when(updateInterestCommand.run(feiInterest().getId(), updateFeiInterestRequest())).thenReturn(updatedFeiInterest());

        ResponseEntity<Interest> entity = interestController.put(feiInterest().getId(), updateFeiInterestRequest());

        assertThat(entity.getBody(), is(updatedFeiInterest()));
    }

    @Test
    public void shouldDeleteInterest() {
        interestController.delete(feiInterest().getId());

        verify(deleteInterestCommand).run(feiInterest().getId());
    }
}