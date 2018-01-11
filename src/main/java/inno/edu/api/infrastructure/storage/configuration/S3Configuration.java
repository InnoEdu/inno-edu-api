package inno.edu.api.infrastructure.storage.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import inno.edu.api.infrastructure.configuration.properties.ApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Configuration {
    @Bean
    public AmazonS3 amazonS3Client(ApplicationConfiguration applicationConfiguration) {
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
