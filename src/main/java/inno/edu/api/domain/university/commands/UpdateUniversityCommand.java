package inno.edu.api.domain.university.commands;

import inno.edu.api.domain.university.exceptions.UniversityNotFoundException;
import inno.edu.api.domain.university.models.University;
import inno.edu.api.domain.university.repositories.UniversityRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Command
public class UpdateUniversityCommand {
    private final UniversityRepository universityRepository;

    public UpdateUniversityCommand(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public University run(UUID id, University university) {
        University currentUniversity = ofNullable(universityRepository.findOne(id))
                .orElseThrow(() -> new UniversityNotFoundException(id));

        currentUniversity.setId(id);
        currentUniversity.setName(university.getName());

        return universityRepository.save(currentUniversity);
    }
}
