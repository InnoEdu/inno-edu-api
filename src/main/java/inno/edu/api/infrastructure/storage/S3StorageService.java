package inno.edu.api.infrastructure.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3URI;
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
@ConditionalOnProperty(name = "application.storage.mode", havingValue = "s3")
public class S3StorageService implements StorageService {
    private final UUIDGeneratorService uuidGeneratorService;
    private final ApplicationConfiguration applicationConfiguration;

    public S3StorageService(UUIDGeneratorService uuidGeneratorService, ApplicationConfiguration applicationConfiguration) {
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
            String bucket = applicationConfiguration.getStorage().getBucket();

            client.putObject(bucket,
                    filename,
                    file.getInputStream(),
                    metadata);

            return client.getUrl(bucket, filename).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String file) {
        AmazonS3 client = buildClient();
        AmazonS3URI uri = new AmazonS3URI(file, true);

        client.deleteObject(uri.getBucket(), uri.getKey());
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