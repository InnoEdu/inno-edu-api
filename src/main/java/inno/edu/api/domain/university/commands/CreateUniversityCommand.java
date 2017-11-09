package inno.edu.api.domain.university.commands;

import inno.edu.api.domain.university.models.University;
import inno.edu.api.domain.university.repositories.UniversityRepository;
import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;

@Component
public class CreateUniversityCommand {
    private final UniversityRepository universityRepository;

    public CreateUniversityCommand(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public University run(University university) {
        university.setId(randomUUID());
        return universityRepository.save(university);
    }
}
