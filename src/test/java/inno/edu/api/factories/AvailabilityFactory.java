package inno.edu.api.factories;

import inno.edu.api.controllers.resources.AvailabilityResource;
import inno.edu.api.domain.availability.models.Availability;
import org.springframework.hateoas.Resources;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class AvailabilityFactory {
    public static Availability availability() {
        return Availability.builder().id(fromString("841b43e1-08be-4401-968f-6ee45370a973")).build();
    }

    public static Availability updatedAvailability() {
        return Availability.builder().id(fromString("841b43e1-08be-4401-968f-6ee45370a973")).build();
    }

    public static AvailabilityResource availabilityResource() {
        return new AvailabilityResource(availability());
    }

    public static List<Availability> allAvailability() {
        return singletonList(availability());
    }

    public static Resources<AvailabilityResource> availabilityResources() {
        return new Resources<>(singletonList(availabilityResource()));
    }
}
