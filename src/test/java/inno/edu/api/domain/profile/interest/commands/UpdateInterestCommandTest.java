package inno.edu.api.domain.profile.interest.commands;

import inno.edu.api.domain.profile.interest.commands.mappers.UpdateInterestRequestMapper;
import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.interest.queries.GetInterestByIdQuery;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiInterest;
import static inno.edu.api.support.ProfileFactory.updateFeiInterestRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiInterest;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateInterestCommandTest {
    @Mock
    private UpdateInterestRequestMapper updateInterestRequestMapper;

    @Mock
    private InterestRepository experienceRepository;

    @Mock
    private GetInterestByIdQuery getInterestByIdQuery;

    @InjectMocks
    private UpdateInterestCommand updateInterestCommand;

    @Test
    public void shouldReturnUpdatedInterest() {
        when(getInterestByIdQuery.run(feiInterest().getId())).thenReturn(feiInterest());
        when(experienceRepository.save(feiInterest())).thenReturn(updatedFeiInterest());

        Interest Interest = updateInterestCommand.run(feiInterest().getId(), updateFeiInterestRequest());

        verify(updateInterestRequestMapper).setInterest(updateFeiInterestRequest(), feiInterest());

        assertThat(Interest, is(updatedFeiInterest()));
    }
}