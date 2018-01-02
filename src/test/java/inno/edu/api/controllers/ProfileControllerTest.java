package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.ProfileFactory.profiles;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class ProfileControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileController profileController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldListProfiles() {
        when(profileRepository.findAll()).thenReturn(profiles());

        profileController.all();

        verify(resourceBuilder).wrappedFrom(eq(profiles()), any(), eq(ProfileResource.class));
    }
}