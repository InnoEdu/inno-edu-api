package inno.edu.api.infrastructure.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class UUIDGeneratorService {
    public UUID generate() {
        return randomUUID();
    }
}
