package inno.edu.api.domain.school.attachment.queries;

import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.domain.school.attachment.models.SchoolAttachment;
import inno.edu.api.domain.school.attachment.repositories.SchoolAttachmentRepository;
import inno.edu.api.domain.school.root.assertions.SchoolExistsAssertion;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Query
public class GetSchoolAttachmentsBySchoolIdQuery {
    private final SchoolExistsAssertion schoolExistsAssertion;
    private final SchoolAttachmentRepository schoolAttachmentRepository;

    public GetSchoolAttachmentsBySchoolIdQuery(SchoolExistsAssertion schoolExistsAssertion, SchoolAttachmentRepository schoolAttachmentRepository) {
        this.schoolExistsAssertion = schoolExistsAssertion;
        this.schoolAttachmentRepository = schoolAttachmentRepository;
    }

    public List<Attachment> run(UUID schoolId) {
        schoolExistsAssertion.run(schoolId);

        return schoolAttachmentRepository
                .findBySchoolId(schoolId)
                .stream()
                .map(SchoolAttachment::getAttachment)
                .collect(toList());
    }
}
