package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UniversityResource;
import inno.edu.api.domain.university.commands.CreateUniversityCommand;
import inno.edu.api.domain.university.commands.UpdateUniversityCommand;
import inno.edu.api.domain.university.exceptions.UniversityNotFoundException;
import inno.edu.api.domain.university.models.University;
import inno.edu.api.domain.university.repositories.UniversityRepository;
import inno.edu.api.domain.user.queries.GetMentorProfilesByUniversityIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.factories.UniversityFactory.stanford;
import static inno.edu.api.factories.UniversityFactory.universities;
import static inno.edu.api.factories.UserFactory.mentorProfiles;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class UniversityControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private UniversityRepository universityRepository;

    @Mock
    private CreateUniversityCommand createUniversityCommand;

    @Mock
    private UpdateUniversityCommand updateUniversityCommand;

    @Mock
    private GetMentorProfilesByUniversityIdQuery getMentorProfilesByUniversityIdQuery;

    @InjectMocks
    private UniversityController universityController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetUniversityById() {
        when(universityRepository.findOne(eq(stanford().getId()))).thenReturn(stanford());

        UniversityResource universityResource = universityController.get(stanford().getId());

        assertThat(universityResource.getUniversity(), is(stanford()));
    }

    @Test(expected = UniversityNotFoundException.class)
    public void shouldRaiseExceptionIfUniversityNotFound() {
        when(universityRepository.findOne(any())).thenReturn(null);

        universityController.get(stanford().getId());
    }

    @Test
    public void shouldListUniversities() {
        when(universityRepository.findAll()).thenReturn(universities());

        universityController.all();

        verify(resourceBuilder).from(eq(universities()), any());
    }

    @Test
    public void shouldListMentorsByUniversity() {
        when(getMentorProfilesByUniversityIdQuery.run(stanford().getId())).thenReturn(mentorProfiles());

        universityController.allMentorsProfile(stanford().getId());

        verify(resourceBuilder).from(eq(mentorProfiles()), any());
    }

    @Test
    public void shouldCreateNewUniversity() {
        ArgumentCaptor<University> argumentCaptor = forClass(University.class);
        when(createUniversityCommand.run(argumentCaptor.capture())).thenReturn(stanford());

        universityController.post(stanford());

        verify(createUniversityCommand).run(argumentCaptor.capture());
    }

    @Test
    public void shouldUpdateUniversity() {
        when(updateUniversityCommand.run(stanford().getId(), stanford())).thenReturn(stanford());

        universityController.put(stanford().getId(), stanford());

        verify(updateUniversityCommand).run(stanford().getId(), stanford());
    }

    @Test
    public void shouldUDeleteUniversity() {
        when(universityRepository.exists(stanford().getId())).thenReturn(true);

        universityController.delete(stanford().getId());

        verify(universityRepository).delete(stanford().getId());
    }
}