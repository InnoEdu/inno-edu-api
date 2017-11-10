package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AppointmentResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.appointment.commands.CreateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentCommand;
import inno.edu.api.domain.appointment.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.collect.Streams.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/api/appointments", produces = "application/hal+json")
public class AppointmentController {
    private final AppointmentRepository availabilityRepository;

    private final ResourceBuilder resourceBuilder;
    private CreateAppointmentCommand createAppointmentCommand;
    private UpdateAppointmentCommand updateAppointmentCommand;

    public AppointmentController(AppointmentRepository availabilityRepository, ResourceBuilder resourceBuilder, CreateAppointmentCommand createAppointmentCommand, UpdateAppointmentCommand updateAppointmentCommand) {
        this.availabilityRepository = availabilityRepository;
        this.resourceBuilder = resourceBuilder;
        this.createAppointmentCommand = createAppointmentCommand;
        this.updateAppointmentCommand = updateAppointmentCommand;
    }

    @GetMapping
    public Resources<AppointmentResource> all() {
        List<AppointmentResource> availability = stream(availabilityRepository.findAll())
                .map(AppointmentResource::new)
                .collect(toList());

        return resourceBuilder.fromResources(availability);
    }

    @GetMapping("/{id}")
    public AppointmentResource get(@PathVariable UUID id) {
        Optional<Appointment> availability = ofNullable(availabilityRepository.findOne(id));
        return new AppointmentResource(availability.orElseThrow(() -> new AppointmentNotFoundException(id)));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Appointment availability) {
        AppointmentResource availabilityResource = new AppointmentResource(createAppointmentCommand.run(availability));
        return availabilityResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable UUID id, @RequestBody Appointment availability) {
        AppointmentResource availabilityResource = new AppointmentResource(updateAppointmentCommand.run(id, availability));
        return availabilityResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if (!availabilityRepository.exists(id)) {
            throw new AppointmentNotFoundException(id);
        }
        availabilityRepository.delete(id);

        return ResponseEntity.noContent().build();
    }
}
