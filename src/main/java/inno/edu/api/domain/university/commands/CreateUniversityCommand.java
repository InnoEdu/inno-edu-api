package inno.edu.api.domain.university.commands;

import inno.edu.api.domain.university.models.University;
import inno.edu.api.domain.university.repositories.UniversityRepository;
import inno.edu.api.infrastructure.annotations.Command;

import static java.util.UUID.randomUUID;

@Command
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
