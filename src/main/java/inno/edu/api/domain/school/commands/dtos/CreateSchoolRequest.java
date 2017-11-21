package inno.edu.api.domain.school.commands.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchoolRequest {
    private String name;
    private String description;
    private String photoUrl;
}
