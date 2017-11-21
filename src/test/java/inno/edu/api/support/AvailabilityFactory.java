package inno.edu.api.support;

import inno.edu.api.domain.availability.commands.dtos.CreateAvailabilityRequest;
import inno.edu.api.domain.availability.commands.dtos.UpdateAvailabilityRequest;
import inno.edu.api.domain.availability.models.Availability;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class AvailabilityFactory {
    public static Availability availability() {
        return Availability.builder()
                .id(fromString("0c55130c-6e63-420b-b3ae-b2485caadc23"))
                .mentorProfileId(feiProfile().getId())
                .fromDateTime(LocalDateTime.of(2017, 11, 9, 12, 0, 1))
                .toDateTime(LocalDateTime.of(2017, 11, 10, 12, 0, 1))
                .build();
    }

    public static CreateAvailabilityRequest createAvailabilityRequest() {
        return CreateAvailabilityRequest.builder()
                .mentorProfileId(feiProfile().getId())
                .fromDateTime(LocalDateTime.of(2017, 11, 9, 12, 0, 1))
                .toDateTime(LocalDateTime.of(2017, 11, 10, 12, 0, 1))
                .build();
    }

    public static Availability otherAvailability() {
        return Availability.builder()
                .id(fromString("8c0af3b9-2883-40f3-bf85-26ee0f506fde"))
                .mentorProfileId(feiProfile().getId())
                .fromDateTime(LocalDateTime.of(2017, 12, 9, 12, 0, 1))
                .toDateTime(LocalDateTime.of(2017, 12, 10, 12, 0, 1))
                .build();
    }

    public static Availability updatedAvailability() {
        return availability().toBuilder()
                .fromDateTime(LocalDateTime.of(2018, 11, 9, 12, 0, 1))
                .toDateTime(LocalDateTime.of(2018, 11, 10, 12, 0, 1))
                .build();
    }

    public static UpdateAvailabilityRequest updateAvailabilityRequest() {
        return UpdateAvailabilityRequest.builder()
                .fromDateTime(LocalDateTime.of(2018, 11, 9, 12, 0, 1))
                .toDateTime(LocalDateTime.of(2018, 11, 10, 12, 0, 1))
                .build();
    }

    public static List<Availability> allAvailability() {
        return newArrayList(availability(), otherAvailability());
    }

    public static List<Availability> feiAvailability() {
        return singletonList(availability());
    }
}
