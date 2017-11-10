package inno.edu.api.factories;

import inno.edu.api.controllers.resources.AvailabilityResource;
import inno.edu.api.domain.availability.models.Availability;
import org.springframework.hateoas.Resources;

import java.time.LocalDateTime;
import java.util.List;

import static inno.edu.api.factories.UniversityFactory.stanford;
import static inno.edu.api.factories.UserFactory.fei;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class AvailabilityFactory {
    public static Availability availability() {
        return Availability.builder()
                .id(fromString("0c55130c-6e63-420b-b3ae-b2485caadc23"))
                .userId(fei().getId())
                .universityId(stanford().getId())
                .fromDateTime(LocalDateTime.of(2017, 11, 9, 12, 0, 1))
                .toDateTime(LocalDateTime.of(2017, 11, 10, 12, 0, 1))
                .build();
    }

    public static Availability otherAvailability() {
        return Availability.builder()
                .id(fromString("8c0af3b9-2883-40f3-bf85-26ee0f506fde"))
                .userId(fei().getId())
                .universityId(stanford().getId())
                .fromDateTime(LocalDateTime.of(2017, 12, 9, 12, 0, 1))
                .toDateTime(LocalDateTime.of(2017, 12, 10, 12, 0, 1))
                .build();
    }

    private static AvailabilityResource availabilityResource() {
        return new AvailabilityResource(availability());
    }

    public static List<Availability> allAvailability() {
        return singletonList(availability());
    }

    public static Resources<AvailabilityResource> availabilityResources() {
        return new Resources<>(singletonList(availabilityResource()));
    }
}
