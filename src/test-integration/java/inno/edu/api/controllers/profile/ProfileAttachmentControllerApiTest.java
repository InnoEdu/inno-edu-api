package inno.edu.api.controllers.profile;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import static inno.edu.api.support.ProfileFactory.feiCreateAttachmentRequest;
import static inno.edu.api.support.ProfileFactory.feiProfileAttachment;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileAttachmentControllerApiTest extends ApiTest {
    @Test
    public void shouldUploadProfileContent() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "temporary.json", null, "bar".getBytes());

        this.mockMvc.perform(
                fileUpload("/api/profiles/"
                        + feiCreateAttachmentRequest().getProfileId()
                        + "/attachments?description="
                        + feiCreateAttachmentRequest().getDescription())
                        .file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(feiProfileAttachment().getId().toString())))
                .andExpect(jsonPath("$.profileId", is(feiProfileAttachment().getProfileId().toString())))
                .andExpect(jsonPath("$.description", is(feiProfileAttachment().getDescription())))
                .andExpect(jsonPath("$.url", is(feiProfileAttachment().getUrl())));
    }
}
