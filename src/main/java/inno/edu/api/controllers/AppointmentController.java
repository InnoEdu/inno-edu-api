package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AppointmentResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.appointment.commands.CreateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.DeleteAppointmentCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentStatusCommand;
import inno.edu.api.domain.appointment.exceptions.AppointmentNotFoundException;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentReason;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import inno.edu.api.domain.appointment.queries.GetAppointmentsByMenteeIdQuery;
import inno.edu.api.domain.appointment.queries.GetAppointmentsByMentorIdQuery;
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

import static inno.edu.api.domain.appointment.models.AppointmentStatus.ACCEPTED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.CANCELED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.DECLINED;
import static java.util.Optional.ofNullable;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/appointments", produces = "application/hal+json")
public class AppointmentController {
    private final ResourceBuilder resourceBuilder;

    private final AppointmentRepository appointmentRepository;

    private final CreateAppointmentCommand createAppointmentCommand;
    private final UpdateAppointmentCommand updateAppointmentCommand;
    private final DeleteAppointmentCommand deleteAppointmentCommand;

    private final GetAppointmentsByMentorIdQuery getAppointmentsByMentorIdQuery;
    private final GetAppointmentsByMenteeIdQuery getAppointmentsByMenteeIdQuery;

    private final UpdateAppointmentStatusCommand updateAppointmentStatusCommand;

    public AppointmentController(AppointmentRepository appointmentRepository, ResourceBuilder resourceBuilder, CreateAppointmentCommand createAppointmentCommand, UpdateAppointmentCommand updateAppointmentCommand, DeleteAppointmentCommand deleteAppointmentCommand, GetAppointmentsByMentorIdQuery getAppointmentsByMentorIdQuery, GetAppointmentsByMenteeIdQuery getAppointmentsByMenteeIdQuery, UpdateAppointmentStatusCommand updateAppointmentStatusCommand) {
        this.appointmentRepository = appointmentRepository;
        this.resourceBuilder = resourceBuilder;
        this.createAppointmentCommand = createAppointmentCommand;
        this.updateAppointmentCommand = updateAppointmentCommand;
        this.deleteAppointmentCommand = deleteAppointmentCommand;
        this.getAppointmentsByMentorIdQuery = getAppointmentsByMentorIdQuery;
        this.getAppointmentsByMenteeIdQuery = getAppointmentsByMenteeIdQuery;
        this.updateAppointmentStatusCommand = updateAppointmentStatusCommand;
    }

    @GetMapping
    public Resources<AppointmentResource> all() {
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        return resourceBuilder.from(appointments, AppointmentResource::new);
    }

    @GetMapping("/mentor/{mentorId}")
    public Resources<AppointmentResource> allByMentor(@PathVariable UUID mentorId,
                                                      @RequestParam(required = false) AppointmentStatus status) {
        List<Appointment> appointments = getAppointmentsByMentorIdQuery.run(mentorId, status);
        return resourceBuilder.from(appointments, AppointmentResource::new);
    }

    @GetMapping("/mentee/{menteeId}")
    public Resources<AppointmentResource> allByMentee(@PathVariable UUID menteeId,
                                                      @RequestParam(required = false) AppointmentStatus status) {
        List<Appointment> appointments = getAppointmentsByMenteeIdQuery.run(menteeId, status);
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

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable UUID id, @RequestBody AppointmentReason reason) {
        updateAppointmentStatusCommand.run(id, reason, CANCELED);
        return noContent().build();
    }

    @PutMapping("/{id}/decline")
    public ResponseEntity<?> decline(@PathVariable UUID id, AppointmentReason reason) {
        updateAppointmentStatusCommand.run(id, reason, DECLINED);
        return noContent().build();
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> accept(@PathVariable UUID id) {
        updateAppointmentStatusCommand.run(id, new AppointmentReason(), ACCEPTED);
        return noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteAppointmentCommand.run(id);
        return noContent().build();
    }
}
