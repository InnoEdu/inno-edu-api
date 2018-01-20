package inno.edu.api.domain.profile.accomplishment.commands;

import inno.edu.api.domain.profile.accomplishment.models.mappers.UpdateAccomplishmentRequestMapper;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.accomplishment.queries.GetAccomplishmentByIdQuery;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiAccomplishment;
import static inno.edu.api.support.ProfileFactory.updateFeiAccomplishmentRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiAccomplishment;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateAccomplishmentCommandTest {
    @Mock
    private UpdateAccomplishmentRequestMapper updateAccomplishmentRequestMapper;

    @Mock
    private AccomplishmentRepository accomplishmentRepository;

    @Mock
    private GetAccomplishmentByIdQuery getAccomplishmentByIdQuery;

    @InjectMocks
    private UpdateAccomplishmentCommand updateAccomplishmentCommand;

    @Test
    public void shouldReturnUpdatedAccomplishment() {
        when(getAccomplishmentByIdQuery.run(feiAccomplishment().getId())).thenReturn(feiAccomplishment());
        when(accomplishmentRepository.save(feiAccomplishment())).thenReturn(updatedFeiAccomplishment());

        Accomplishment Accomplishment = updateAccomplishmentCommand.run(feiAccomplishment().getId(), updateFeiAccomplishmentRequest());

        verify(updateAccomplishmentRequestMapper).setAccomplishment(updateFeiAccomplishmentRequest(), feiAccomplishment());

        assertThat(Accomplishment, is(updatedFeiAccomplishment()));
    }
}