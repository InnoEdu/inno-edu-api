package inno.edu.api.infrastructure.configuration.properties;

import lombok.Data;

@Data
public class StorageConfiguration {
    private StorageMode mode;
    private String location = "";
    private String accessKey = "";
    private String secretAccessKey = "";
}
