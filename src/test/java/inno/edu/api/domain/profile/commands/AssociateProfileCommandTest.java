package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.ProfileAssociation;
import inno.edu.api.domain.profile.repositories.ProfileAssociationRepository;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.ProfileFactory.gustavoProfileAssociation;
import static inno.edu.api.support.ProfileFactory.gustavoToStanfordRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssociateProfileCommandTest {
    @Mock
    private ProfileAssociationRepository profileAssociationRepository;

    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @InjectMocks
    private AssociateProfileCommand associateProfileCommand;

    @Test
    public void shouldSaveProfileAssociation() {
        ProfileAssociation expectedAssociation = gustavoProfileAssociation();

        when(uuidGeneratorService.generate()).thenReturn(expectedAssociation.getId());
        when(profileAssociationRepository.save(any(ProfileAssociation.class))).then((answer) -> answer.getArguments()[0]);

        ProfileAssociation profileAssociation = associateProfileCommand.run(gustavoProfile().getId(), gustavoToStanfordRequest());
        assertThat(profileAssociation, is(expectedAssociation));
    }

}