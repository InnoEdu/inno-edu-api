package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AppointmentResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.appointment.commands.CreateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentCommand;
import inno.edu.api.domain.appointment.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import inno.edu.api.domain.appointment.queries.GetAppointmentsByUniversityIdQuery;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping(value = "/api/appointments", produces = "application/hal+json")
public class AppointmentController {
    private final ResourceBuilder resourceBuilder;

    private final AppointmentRepository appointmentRepository;

    private final CreateAppointmentCommand createAppointmentCommand;
    private final UpdateAppointmentCommand updateAppointmentCommand;

    private final GetAppointmentsByUniversityIdQuery getAppointmentsByUniversityIdQuery;

    public AppointmentController(AppointmentRepository appointmentRepository, ResourceBuilder resourceBuilder, CreateAppointmentCommand createAppointmentCommand, UpdateAppointmentCommand updateAppointmentCommand, GetAppointmentsByUniversityIdQuery getAppointmentsByUniversityIdQuery) {
        this.appointmentRepository = appointmentRepository;
        this.resourceBuilder = resourceBuilder;
        this.createAppointmentCommand = createAppointmentCommand;
        this.updateAppointmentCommand = updateAppointmentCommand;
        this.getAppointmentsByUniversityIdQuery = getAppointmentsByUniversityIdQuery;
    }

    @GetMapping
    public Resources<AppointmentResource> all() {
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        return resourceBuilder.from(appointments, AppointmentResource::new);
    }

    @GetMapping("/university/{universityId}")
    public Resources<AppointmentResource> allByUniversity(@PathVariable UUID universityId,
                                                          @RequestParam(required = false) AppointmentStatus status) {
        List<Appointment> appointments = getAppointmentsByUniversityIdQuery.run(universityId, status);
        return resourceBuilder.from(appointments, AppointmentResource::new);
    }

    @GetMapping("/{id}")
    public AppointmentResource get(@PathVariable UUID id) {
        Optional<Appointment> appointment = ofNullable(appointmentRepository.findOne(id));
        return new AppointmentResource(appointment.orElseThrow(() -> new AppointmentNotFoundException(id)));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Appointment appointment) {
        AppointmentResource appointmentResource = new AppointmentResource(createAppointmentCommand.run(appointment));
        return appointmentResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable UUID id, @RequestBody Appointment appointment) {
        AppointmentResource appointmentResource = new AppointmentResource(updateAppointmentCommand.run(id, appointment));
        return appointmentResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if (!appointmentRepository.exists(id)) {
            throw new AppointmentNotFoundException(id);
        }
        appointmentRepository.delete(id);

        return ResponseEntity.noContent().build();
    }
}
