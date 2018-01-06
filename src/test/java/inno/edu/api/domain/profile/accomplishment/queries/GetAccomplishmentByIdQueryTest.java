package inno.edu.api.domain.profile.accomplishment.queries;

import inno.edu.api.domain.profile.accomplishment.exceptions.AccomplishmentNotFoundException;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiAccomplishment;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAccomplishmentByIdQueryTest {
    @Mock
    private AccomplishmentRepository accomplishmentRepository;

    @InjectMocks
    private GetAccomplishmentByIdQuery getAccomplishmentByIdQuery;

    @Test(expected = AccomplishmentNotFoundException.class)
    public void shouldThrowExceptionIfAccomplishmentDoesNotExist() {
        when(accomplishmentRepository.findOne(feiAccomplishment().getId())).thenReturn(null);

        getAccomplishmentByIdQuery.run(feiAccomplishment().getId());
    }

    @Test
    public void shouldReturnAccomplishment() {
        when(accomplishmentRepository.findOne(feiAccomplishment().getId())).thenReturn(feiAccomplishment());

        Accomplishment accomplishment = getAccomplishmentByIdQuery.run(feiAccomplishment().getId());

        assertThat(accomplishment, is(feiAccomplishment()));
    }
}