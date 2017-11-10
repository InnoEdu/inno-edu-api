package inno.edu.api.factories;

import inno.edu.api.domain.availability.models.Availability;

import static java.util.UUID.fromString;

public class AvailabilityFactory {
    public static Availability availability() {
        return Availability.builder()
                .id(fromString("0c55130c-6e63-420b-b3ae-b2485caadc23"))
                .build();
    }

    public static Availability otherAvailability() {
        return Availability.builder()
                .id(fromString("8c0af3b9-2883-40f3-bf85-26ee0f506fde"))
                .build();
    }

    public static String userPayload() {

        return "{\"firstName\": \"%s\", \"lastName\": \"%s\"}";
    }
}
