package inno.edu.api.controllers.profile.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.profile.ServiceController;
import inno.edu.api.controllers.profile.ProfileController;
import inno.edu.api.domain.profile.service.models.Service;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class ServiceResource extends ResourceSupport {
    @JsonUnwrapped
    private final Service service;

    public ServiceResource(Service service) {
        this.service = service;

        add(linkTo(methodOn(ServiceController.class).get(service.getId())).withSelfRel());
        add(linkTo(methodOn(ProfileController.class).get(service.getProfileId())).withRel("profile"));
    }

    public ResponseEntity<Service> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(service);
    }

    public ResponseEntity<Service> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(service);
    }
}
