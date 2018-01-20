package inno.edu.api.controllers.profile;

import inno.edu.api.domain.profile.accomplishment.models.resources.AccomplishmentResource;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import inno.edu.api.domain.profile.accomplishment.commands.CreateAccomplishmentCommand;
import inno.edu.api.domain.profile.accomplishment.commands.DeleteAccomplishmentCommand;
import inno.edu.api.domain.profile.accomplishment.commands.UpdateAccomplishmentCommand;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.accomplishment.queries.GetAccomplishmentByIdQuery;
import inno.edu.api.domain.profile.accomplishment.queries.GetAccomplishmentsByProfileIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.ProfileFactory.createFeiAccomplishmentRequest;
import static inno.edu.api.support.ProfileFactory.feiAccomplishment;
import static inno.edu.api.support.ProfileFactory.feiAccomplishments;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.updateFeiAccomplishmentRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiAccomplishment;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class AccomplishmentControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private GetAccomplishmentByIdQuery getAccomplishmentByIdQuery;

    @Mock
    private GetAccomplishmentsByProfileIdQuery getAccomplishmentsByProfileIdQuery;

    @Mock
    private CreateAccomplishmentCommand createAccomplishmentCommand;

    @Mock
    private UpdateAccomplishmentCommand updateAccomplishmentCommand;

    @Mock
    private DeleteAccomplishmentCommand deleteAccomplishmentCommand;

    @InjectMocks
    private AccomplishmentController accomplishmentController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldListAccomplishmentsForProfile() {
        when(getAccomplishmentsByProfileIdQuery.run(feiProfile().getId())).thenReturn(feiAccomplishments());

        accomplishmentController.all(feiProfile().getId());

        verify(resourceBuilder).wrappedFrom(eq(feiAccomplishments()), any(), eq(AccomplishmentResource.class));
    }

    @Test
    public void shouldGetAccomplishmentById() {
        when(getAccomplishmentByIdQuery.run(eq(feiAccomplishment().getId()))).thenReturn(feiAccomplishment());

        AccomplishmentResource accomplishmentResource = accomplishmentController.get(feiAccomplishment().getId());

        assertThat(accomplishmentResource.getAccomplishment(), is(feiAccomplishment()));
    }

    @Test
    public void shouldCreateNewAccomplishment() {
        when(createAccomplishmentCommand.run(feiProfile().getId(), createFeiAccomplishmentRequest())).thenReturn(feiAccomplishment());

        ResponseEntity<Accomplishment> entity = accomplishmentController.post(feiProfile().getId(), createFeiAccomplishmentRequest());

        assertThat(entity.getBody(), is(feiAccomplishment()));
    }

    @Test
    public void shouldUpdateAccomplishment() {
        when(updateAccomplishmentCommand.run(feiAccomplishment().getId(), updateFeiAccomplishmentRequest())).thenReturn(updatedFeiAccomplishment());

        ResponseEntity<Accomplishment> entity = accomplishmentController.put(feiAccomplishment().getId(), updateFeiAccomplishmentRequest());

        assertThat(entity.getBody(), is(updatedFeiAccomplishment()));
    }

    @Test
    public void shouldDeleteAccomplishment() {
        accomplishmentController.delete(feiAccomplishment().getId());

        verify(deleteAccomplishmentCommand).run(feiAccomplishment().getId());
    }
}