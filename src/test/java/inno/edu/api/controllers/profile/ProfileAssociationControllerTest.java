package inno.edu.api.controllers.profile;

import inno.edu.api.domain.profile.association.models.resources.ProfileAssociationResource;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import inno.edu.api.domain.profile.association.commands.ApproveProfileAssociationCommand;
import inno.edu.api.domain.profile.association.commands.AssociateProfileCommand;
import inno.edu.api.domain.profile.association.commands.RejectProfileAssociationCommand;
import inno.edu.api.domain.profile.association.queries.GetAssociationsByProfileIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.ProfileFactory.approveRequest;
import static inno.edu.api.support.ProfileFactory.associations;
import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.ProfileFactory.gustavoProfileAssociation;
import static inno.edu.api.support.ProfileFactory.gustavoToBerkeleyRequest;
import static inno.edu.api.support.ProfileFactory.rejectRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class ProfileAssociationControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private GetAssociationsByProfileIdQuery getAssociationsByProfileIdQuery;

    @Mock
    private AssociateProfileCommand associateProfileCommand;

    @Mock
    private ApproveProfileAssociationCommand approveProfileAssociationCommand;

    @Mock
    private RejectProfileAssociationCommand rejectProfileAssociationCommand;

    @InjectMocks
    private ProfileAssociationController profileAssociationController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldAssociateProfileToSchool() {
        profileAssociationController.associate(gustavoProfile().getId(), gustavoToBerkeleyRequest());

        verify(associateProfileCommand).run(gustavoProfile().getId(), gustavoToBerkeleyRequest());
    }

    @Test
    public void shouldListAssociationsForProfile() {
        when(getAssociationsByProfileIdQuery.run(gustavoProfile().getId(), gustavoProfileAssociation().getStatus()))
                .thenReturn(associations());

        profileAssociationController.all(gustavoProfile().getId(), gustavoProfileAssociation().getStatus());

        verify(resourceBuilder).wrappedFrom(eq(associations()), any(), eq(ProfileAssociationResource.class));
    }

    @Test
    public void shouldApproveAssociation() {
        profileAssociationController.approve(gustavoProfileAssociation().getId(), approveRequest());

        verify(approveProfileAssociationCommand).run(gustavoProfileAssociation().getId(), approveRequest());
    }

    @Test
    public void shouldRejectAssociation() {
        profileAssociationController.reject(gustavoProfileAssociation().getId(), rejectRequest());

        verify(rejectProfileAssociationCommand).run(gustavoProfileAssociation().getId(), rejectRequest());
    }
}