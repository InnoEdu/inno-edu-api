package inno.edu.api.controllers.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Component
public class ResourceBuilder {
    public <T> Resources<T> fromResources(List<T> resourceItems) {
        Resources<T> resources = new Resources<>(resourceItems);

        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));

        return resources;
    }


}
