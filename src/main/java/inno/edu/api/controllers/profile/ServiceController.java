package inno.edu.api.controllers.profile;

import inno.edu.api.domain.profile.service.models.resources.ServiceResource;
import inno.edu.api.presentation.resources.ResourceBuilder;
import inno.edu.api.domain.profile.service.commands.CreateServiceCommand;
import inno.edu.api.domain.profile.service.commands.DeleteServiceCommand;
import inno.edu.api.domain.profile.service.commands.UpdateServiceCommand;
import inno.edu.api.domain.profile.service.models.dtos.CreateServiceRequest;
import inno.edu.api.domain.profile.service.models.dtos.UpdateServiceRequest;
import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.service.queries.GetServiceByIdQuery;
import inno.edu.api.domain.profile.service.queries.GetServicesByProfileIdQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/profiles", produces = "application/hal+json")
public class ServiceController {
    private final ResourceBuilder resourceBuilder;

    private final GetServiceByIdQuery getServiceByIdQuery;
    private final GetServicesByProfileIdQuery getServicesByProfileIdQuery;

    private final CreateServiceCommand createServiceCommand;
    private final UpdateServiceCommand updateServiceCommand;
    private final DeleteServiceCommand deleteServiceCommand;

    @Autowired
    public ServiceController(ResourceBuilder resourceBuilder, GetServiceByIdQuery getServiceByIdQuery, GetServicesByProfileIdQuery getServicesByProfileIdQuery, CreateServiceCommand createServiceCommand, UpdateServiceCommand updateServiceCommand, DeleteServiceCommand deleteServiceCommand) {
        this.resourceBuilder = resourceBuilder;
        this.getServiceByIdQuery = getServiceByIdQuery;
        this.getServicesByProfileIdQuery = getServicesByProfileIdQuery;
        this.createServiceCommand = createServiceCommand;
        this.updateServiceCommand = updateServiceCommand;
        this.deleteServiceCommand = deleteServiceCommand;
    }

    @GetMapping("/{id}/services")
    public Resources<Object> all(@PathVariable UUID id) {
        Iterable<Service> services = getServicesByProfileIdQuery.run(id);
        return resourceBuilder.wrappedFrom(services, ServiceResource::new, ServiceResource.class);
    }

    @GetMapping("/services/{id}")
    public ServiceResource get(@PathVariable UUID id) {
        return new ServiceResource(getServiceByIdQuery.run(id));
    }

    @PostMapping("/{id}/services")
    public ResponseEntity<Service> post(@PathVariable UUID id, @Valid @RequestBody CreateServiceRequest request) {
        ServiceResource serviceResource = new ServiceResource(createServiceCommand.run(id, request));
        return serviceResource.toCreated();
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<Service> put(@PathVariable UUID id, @Valid @RequestBody UpdateServiceRequest request) {
        ServiceResource serviceResource = new ServiceResource(updateServiceCommand.run(id, request));
        return serviceResource.toUpdated();
    }

    @DeleteMapping("/services/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteServiceCommand.run(id);
        return noContent().build();
    }
}
