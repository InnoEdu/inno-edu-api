package inno.edu.api.infrastructure.storage.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.ObjectMetadata;
import inno.edu.api.infrastructure.configuration.properties.ApplicationConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.google.common.io.Files.getFileExtension;

@Service
@ConditionalOnProperty(name = "application.storage.mode", havingValue = "s3")
public class S3StorageService implements StorageService {
    private final ApplicationConfiguration applicationConfiguration;
    private final AmazonS3 amazonS3Client;

    public S3StorageService(ApplicationConfiguration applicationConfiguration, AmazonS3 amazonS3Client) {
        this.applicationConfiguration = applicationConfiguration;
        this.amazonS3Client = amazonS3Client;
    }

    @Override
    public String save(UUID resourceId, MultipartFile file) {
        String filename = resourceId.toString()
                + "."
                + getFileExtension(file.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            String bucket = applicationConfiguration.getStorage().getBucket();

            amazonS3Client.putObject(bucket,
                    filename,
                    file.getInputStream(),
                    metadata);

            return amazonS3Client.getUrl(bucket, filename).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String file) {
        AmazonS3URI uri = new AmazonS3URI(file, true);
        amazonS3Client.deleteObject(uri.getBucket(), uri.getKey());
    }
}
