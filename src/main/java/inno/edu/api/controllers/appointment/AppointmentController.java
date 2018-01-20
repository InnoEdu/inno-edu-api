package inno.edu.api.controllers.appointment;

import inno.edu.api.domain.appointment.root.commands.CalculateAppointmentFeeCommand;
import inno.edu.api.domain.appointment.root.commands.CreateAppointmentCommand;
import inno.edu.api.domain.appointment.root.commands.DeleteAppointmentCommand;
import inno.edu.api.domain.appointment.root.commands.UpdateAppointmentCommand;
import inno.edu.api.domain.appointment.root.commands.UpdateAppointmentStatusCommand;
import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.models.AppointmentStatus;
import inno.edu.api.domain.appointment.root.models.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.appointment.root.models.dtos.CreateAppointmentRequest;
import inno.edu.api.domain.appointment.root.models.dtos.SearchAppointmentsRequest;
import inno.edu.api.domain.appointment.root.models.dtos.UpdateAppointmentRequest;
import inno.edu.api.domain.appointment.root.models.dtos.UpdateAppointmentStatusRequest;
import inno.edu.api.domain.appointment.root.models.resources.AppointmentResource;
import inno.edu.api.domain.appointment.root.models.resources.EstimationResource;
import inno.edu.api.domain.appointment.root.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.root.queries.GetAppointmentsByMenteeProfileIdQuery;
import inno.edu.api.domain.appointment.root.queries.GetAppointmentsByMentorProfileIdQuery;
import inno.edu.api.domain.appointment.root.queries.GetAppointmentsQuery;
import inno.edu.api.domain.appointment.root.queries.SearchAppointmentsQuery;
import inno.edu.api.presentation.resources.ResourceBuilder;
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

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/appointments", produces = "application/hal+json")
public class AppointmentController {
    private final ResourceBuilder resourceBuilder;

    private final CreateAppointmentCommand createAppointmentCommand;
    private final UpdateAppointmentCommand updateAppointmentCommand;
    private final DeleteAppointmentCommand deleteAppointmentCommand;
    private final UpdateAppointmentStatusCommand updateAppointmentStatusCommand;
    private final CalculateAppointmentFeeCommand calculateAppointmentFeeCommand;

    private final SearchAppointmentsQuery searchAppointmentsQuery;
    private final GetAppointmentsQuery getAppointmentsQuery;
    private final GetAppointmentByIdQuery getAppointmentByIdQuery;
    private final GetAppointmentsByMentorProfileIdQuery getAppointmentsByMentorProfileIdQuery;
    private final GetAppointmentsByMenteeProfileIdQuery getAppointmentsByMenteeProfileIdQuery;

    public AppointmentController(ResourceBuilder resourceBuilder, CreateAppointmentCommand createAppointmentCommand, UpdateAppointmentCommand updateAppointmentCommand, DeleteAppointmentCommand deleteAppointmentCommand, GetAppointmentsQuery getAppointmentsQuery, GetAppointmentByIdQuery getAppointmentByIdQuery, GetAppointmentsByMentorProfileIdQuery getAppointmentsByMentorProfileIdQuery, GetAppointmentsByMenteeProfileIdQuery getAppointmentsByMenteeProfileIdQuery, UpdateAppointmentStatusCommand updateAppointmentStatusCommand, CalculateAppointmentFeeCommand calculateAppointmentFeeCommand, SearchAppointmentsQuery searchAppointmentsQuery) {
        this.resourceBuilder = resourceBuilder;
        this.createAppointmentCommand = createAppointmentCommand;
        this.updateAppointmentCommand = updateAppointmentCommand;
        this.deleteAppointmentCommand = deleteAppointmentCommand;
        this.getAppointmentsQuery = getAppointmentsQuery;
        this.getAppointmentByIdQuery = getAppointmentByIdQuery;
        this.getAppointmentsByMentorProfileIdQuery = getAppointmentsByMentorProfileIdQuery;
        this.getAppointmentsByMenteeProfileIdQuery = getAppointmentsByMenteeProfileIdQuery;
        this.updateAppointmentStatusCommand = updateAppointmentStatusCommand;
        this.calculateAppointmentFeeCommand = calculateAppointmentFeeCommand;
        this.searchAppointmentsQuery = searchAppointmentsQuery;
    }

    @GetMapping
    public Resources<Object> all() {
        List<Appointment> appointments = getAppointmentsQuery.run();
        return resourceBuilder.wrappedFrom(appointments, AppointmentResource::new, AppointmentResource.class);
    }

    @GetMapping("/mentor/{profileId}")
    public Resources<Object> allByMentor(@PathVariable UUID profileId,
                                         @RequestParam(required = false) AppointmentStatus status) {
        List<Appointment> appointments = getAppointmentsByMentorProfileIdQuery.run(profileId, status);
        return resourceBuilder.wrappedFrom(appointments, AppointmentResource::new, AppointmentResource.class);
    }

    @GetMapping("/mentee/{profileId}")
    public Resources<Object> allByMentee(@PathVariable UUID profileId,
                                         @RequestParam(required = false) AppointmentStatus status) {
        List<Appointment> appointments = getAppointmentsByMenteeProfileIdQuery.run(profileId, status);
        return resourceBuilder.wrappedFrom(appointments, AppointmentResource::new, AppointmentResource.class);
    }

    @GetMapping("/{id}")
    public AppointmentResource get(@PathVariable UUID id) {
        return new AppointmentResource(getAppointmentByIdQuery.run(id));
    }

    @GetMapping("/search")
    public Resources<Object> search(SearchAppointmentsRequest request) {
        List<Appointment> appointments = searchAppointmentsQuery.run(request);
        return resourceBuilder.wrappedFrom(appointments, AppointmentResource::new, AppointmentResource.class);
    }

    @PostMapping
    public ResponseEntity<Appointment> post(@Valid @RequestBody CreateAppointmentRequest request) {
        AppointmentResource appointmentResource = new AppointmentResource(createAppointmentCommand.run(request));
        return appointmentResource.toCreated();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> put(@PathVariable UUID id, @Valid @RequestBody UpdateAppointmentRequest request) {
        AppointmentResource appointmentResource = new AppointmentResource(updateAppointmentCommand.run(id, request));
        return appointmentResource.toUpdated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteAppointmentCommand.run(id);
        return noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> status(@PathVariable UUID id, @Valid @RequestBody UpdateAppointmentStatusRequest request) {
        updateAppointmentStatusCommand.run(id, request);
        return noContent().build();
    }

    @GetMapping("/estimate")
    public EstimationResource estimate(CalculateAppointmentFeeRequest request) {
        BigDecimal fee = calculateAppointmentFeeCommand.run(request);
        return new EstimationResource(fee);
    }
}
