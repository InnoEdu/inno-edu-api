package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.SchoolResource;
import inno.edu.api.domain.profile.root.queries.GetProfilesBySchoolIdQuery;
import inno.edu.api.domain.school.commands.CreateSchoolCommand;
import inno.edu.api.domain.school.commands.DeleteSchoolCommand;
import inno.edu.api.domain.school.commands.UpdateSchoolCommand;
import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.queries.GetSchoolByIdQuery;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.ProfileFactory.profiles;
import static inno.edu.api.support.SchoolFactory.createStanfordRequest;
import static inno.edu.api.support.SchoolFactory.schools;
import static inno.edu.api.support.SchoolFactory.stanford;
import static inno.edu.api.support.SchoolFactory.updateStanfordRequest;
import static inno.edu.api.support.SchoolFactory.updatedStanford;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class SchoolControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private CreateSchoolCommand createSchoolCommand;

    @Mock
    private UpdateSchoolCommand updateSchoolCommand;

    @Mock
    private DeleteSchoolCommand deleteSchoolCommand;

    @Mock
    private GetProfilesBySchoolIdQuery getProfilesBySchoolIdQuery;

    @Mock
    private GetSchoolByIdQuery getSchoolByIdQuery;

    @InjectMocks
    private SchoolController schoolController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetSchoolById() {
        when(getSchoolByIdQuery.run(eq(stanford().getId()))).thenReturn(stanford());

        SchoolResource schoolResource = schoolController.get(stanford().getId());

        assertThat(schoolResource.getSchool(), is(stanford()));
    }

    @Test
    public void shouldListUniversities() {
        when(schoolRepository.findAll()).thenReturn(schools());

        schoolController.all();

        verify(resourceBuilder).wrappedFrom(eq(schools()), any(), eq(SchoolResource.class));
    }

    @Test
    public void shouldListMentorsBySchool() {
        when(schoolRepository.exists(stanford().getId())).thenReturn(true);
        when(getProfilesBySchoolIdQuery.run(stanford().getId())).thenReturn(profiles());

        schoolController.allMentorsProfile(stanford().getId());

        verify(resourceBuilder).wrappedFrom(eq(profiles()), any(), eq(ProfileResource.class));
    }

    @Test
    public void shouldCreateNewSchool() {
        when(createSchoolCommand.run(createStanfordRequest())).thenReturn(stanford());

        ResponseEntity<School> entity = schoolController.post(createStanfordRequest());

        assertThat(entity.getBody(), is(stanford()));
    }

    @Test
    public void shouldUpdateSchool() {
        when(updateSchoolCommand.run(stanford().getId(), updateStanfordRequest())).thenReturn(updatedStanford());

        ResponseEntity<School> entity = schoolController.put(stanford().getId(), updateStanfordRequest());

        assertThat(entity.getBody(), is(updatedStanford()));
    }

    @Test
    public void shouldDeleteSchool() {
        schoolController.delete(stanford().getId());

        verify(deleteSchoolCommand).run(stanford().getId());
    }
}