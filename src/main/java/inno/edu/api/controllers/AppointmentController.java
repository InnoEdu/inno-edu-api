package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AppointmentResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.appointment.commands.CreateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.DeleteAppointmentCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentCommand;
import inno.edu.api.domain.appointment.commands.UpdateAppointmentStatusCommand;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentReason;
import inno.edu.api.domain.appointment.models.AppointmentStatus;
import inno.edu.api.domain.appointment.queries.GetAppointmentByIdQuery;
import inno.edu.api.domain.appointment.queries.GetAppointmentsByMenteeIdQuery;
import inno.edu.api.domain.appointment.queries.GetAppointmentsByMentorIdQuery;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
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
import java.util.UUID;

import static inno.edu.api.domain.appointment.models.AppointmentStatus.ACCEPTED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.CANCELED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.DECLINED;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/appointments", produces = "application/hal+json")
public class AppointmentController {
    private final ResourceBuilder resourceBuilder;

    private final AppointmentRepository appointmentRepository;

    private final CreateAppointmentCommand createAppointmentCommand;
    private final UpdateAppointmentCommand updateAppointmentCommand;
    private final DeleteAppointmentCommand deleteAppointmentCommand;

    private final GetAppointmentByIdQuery getAppointmentByIdQuery;
    private final GetAppointmentsByMentorIdQuery getAppointmentsByMentorIdQuery;
    private final GetAppointmentsByMenteeIdQuery getAppointmentsByMenteeIdQuery;

    private final UpdateAppointmentStatusCommand updateAppointmentStatusCommand;

    public AppointmentController(AppointmentRepository appointmentRepository, ResourceBuilder resourceBuilder, CreateAppointmentCommand createAppointmentCommand, UpdateAppointmentCommand updateAppointmentCommand, DeleteAppointmentCommand deleteAppointmentCommand, GetAppointmentByIdQuery getAppointmentByIdQuery, GetAppointmentsByMentorIdQuery getAppointmentsByMentorIdQuery, GetAppointmentsByMenteeIdQuery getAppointmentsByMenteeIdQuery, UpdateAppointmentStatusCommand updateAppointmentStatusCommand) {
        this.appointmentRepository = appointmentRepository;
        this.resourceBuilder = resourceBuilder;
        this.createAppointmentCommand = createAppointmentCommand;
        this.updateAppointmentCommand = updateAppointmentCommand;
        this.deleteAppointmentCommand = deleteAppointmentCommand;
        this.getAppointmentByIdQuery = getAppointmentByIdQuery;
        this.getAppointmentsByMentorIdQuery = getAppointmentsByMentorIdQuery;
        this.getAppointmentsByMenteeIdQuery = getAppointmentsByMenteeIdQuery;
        this.updateAppointmentStatusCommand = updateAppointmentStatusCommand;
    }

    @GetMapping
    @ApiOperation(value = "Find all appointments", notes = "Return all appointments.", response = Appointment.class, responseContainer = "List")
    public Resources<AppointmentResource> all() {
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        return resourceBuilder.from(appointments, AppointmentResource::new);
    }

    @GetMapping("/mentor/{mentorId}")
    @ApiOperation(value = "Find all appointments by mentor and status", notes = "Return all appointments for the specific mentor and also optionally filtered by the appointment status. ", response = Appointment.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid mentor ID supplied."),
    })
    public Resources<AppointmentResource> allByMentor(@PathVariable UUID mentorId,
                                                      @RequestParam(required = false) AppointmentStatus status) {
        List<Appointment> appointments = getAppointmentsByMentorIdQuery.run(mentorId, status);
        return resourceBuilder.from(appointments, AppointmentResource::new);
    }

    @GetMapping("/mentee/{menteeId}")
    @ApiOperation(value = "Find all appointments by mentee and status", notes = "Return all appointments for the specific mentee and also optionally filtered by the appointment status. ", response = Appointment.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid mentee ID supplied."),
    })
    public Resources<AppointmentResource> allByMentee(@PathVariable UUID menteeId,
                                                      @RequestParam(required = false) AppointmentStatus status) {
        List<Appointment> appointments = getAppointmentsByMenteeIdQuery.run(menteeId, status);
        return resourceBuilder.from(appointments, AppointmentResource::new);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an appointment", notes = "Get an appointment by ID.", response = Appointment.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "Appointment not found."),
    })
    public AppointmentResource get(@PathVariable UUID id) {
        return new AppointmentResource(getAppointmentByIdQuery.run(id));
    }

    @PostMapping
    @ApiOperation(value = "Create a new appointment", notes = "Creates a new appointment for a Mentee Profile, Mentor Profile and School combination.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "New appointment successfully created.", responseHeaders = @ResponseHeader(name = "Location", description = "Link to the new resource created.", response = String.class)),
            @ApiResponse(code = 400, message = "Invalid Mentor Profile or Mentee Profile supplied."),
    })
    public ResponseEntity<?> post(@RequestBody Appointment appointment) {
        AppointmentResource appointmentResource = new AppointmentResource(createAppointmentCommand.run(appointment));
        return appointmentResource.toCreated();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an appointment", notes = "Update an appointment. You cannot change the original profiles that created the appointment.", response = Appointment.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "New appointment successfully updated.", responseHeaders = @ResponseHeader(name = "Location", description = "Link to the updated resource.", response = String.class)),
            @ApiResponse(code = 404, message = "Appointment not found."),
    })
    public ResponseEntity<?> put(@PathVariable UUID id, @RequestBody Appointment appointment) {
        AppointmentResource appointmentResource = new AppointmentResource(updateAppointmentCommand.run(id, appointment));
        return appointmentResource.toUpdated();
    }

    @PutMapping("/{id}/cancel")
    @ApiOperation(value = "Cancel an appointment", notes = "Cancel an appointment because of a specific reason. This operation should be performed by a mentee.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Appointment successfully canceled."),
            @ApiResponse(code = 400, message = "Reason not provided."),
            @ApiResponse(code = 404, message = "Appointment not found."),
    })
    public ResponseEntity<?> cancel(@PathVariable UUID id, @RequestBody AppointmentReason reason) {
        updateAppointmentStatusCommand.run(id, reason, CANCELED);
        return noContent().build();
    }

    @PutMapping("/{id}/decline")
    @ApiOperation(value = "Decline an appointment", notes = "Decline an appointment because of a specific reason. This operation should be performed by a mentor.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Appointment successfully declined."),
            @ApiResponse(code = 400, message = "Reason not provided."),
            @ApiResponse(code = 404, message = "Appointment not found."),
    })
    public ResponseEntity<?> decline(@PathVariable UUID id, @RequestBody AppointmentReason reason) {
        updateAppointmentStatusCommand.run(id, reason, DECLINED);
        return noContent().build();
    }

    @PutMapping("/{id}/accept")
    @ApiOperation(value = "Accept an appointment", notes = "Accept an appointment. This operation should be performed by a mentor.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Appointment successfully accepted."),
            @ApiResponse(code = 404, message = "Appointment not found."),
    })
    public ResponseEntity<?> accept(@PathVariable UUID id) {
        updateAppointmentStatusCommand.run(id, new AppointmentReason(), ACCEPTED);
        return noContent().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an appointment", notes = "Delete an appointment, this operation cannot be undone.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Appointment successfully deleted."),
            @ApiResponse(code = 404, message = "Appointment not found.")
    })
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteAppointmentCommand.run(id);
        return noContent().build();
    }
}
