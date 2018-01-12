package inno.edu.api.domain.school.root.queries;

import inno.edu.api.domain.school.root.models.School;
import inno.edu.api.domain.school.root.repositories.SchoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.SchoolFactory.schools;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetSchoolsQueryTest {
    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private GetSchoolsQuery getSchoolsQuery;

    @Test
    public void shouldReturnSchools() {
        when(schoolRepository.findAll()).thenReturn(schools());

        List<School> schools = getSchoolsQuery.run();

        assertThat(schools, is(schools()));
    }
}