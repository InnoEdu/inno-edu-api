package inno.edu.api.infrastructure.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import inno.edu.api.infrastructure.configuration.properties.ApplicationConfiguration;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.google.common.io.Files.getFileExtension;

@Service
@ConditionalOnProperty(name = "application.storage.mode", havingValue = "aws")
public class AwsStorageService implements StorageService {
    private final UUIDGeneratorService uuidGeneratorService;
    private final ApplicationConfiguration applicationConfiguration;

    public AwsStorageService(UUIDGeneratorService uuidGeneratorService, ApplicationConfiguration applicationConfiguration) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public String save(UUID keyId, MultipartFile file) {
        String filename = keyId.toString()
                + "/"
                + uuidGeneratorService.generate().toString()
                + "."
                + getFileExtension(file.getOriginalFilename());

        AmazonS3 client = buildClient();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            client.putObject(applicationConfiguration.getStorage().getBucket(),
                    filename,
                    file.getInputStream(),
                    metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filename;
    }

    private AmazonS3 buildClient() {
        AWSCredentials credentials = new BasicAWSCredentials(
                applicationConfiguration.getStorage().getAccessKey(),
                applicationConfiguration.getStorage().getSecretKey()
        );

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_1)
                .build();
    }
}
