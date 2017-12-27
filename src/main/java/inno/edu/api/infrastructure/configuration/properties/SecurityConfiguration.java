package inno.edu.api.infrastructure.configuration.properties;

import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

@Data
public class SecurityConfiguration {

    private List<EndpointConfiguration> unsecured = new ArrayList<>();

    @Data
    public static class EndpointConfiguration {
        private HttpMethod method;
        private String path;
    }
}
