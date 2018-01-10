package inno.edu.api.controllers.profile;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import static inno.edu.api.support.ProfileFactory.feiUploadAttachmentRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileAttachmentControllerApiTest extends ApiTest {
    @Test
    public void shouldUploadProfileContent() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "temporary.json", null, "bar".getBytes());

        this.mockMvc.perform(
                fileUpload("/api/profiles/"
                        + feiUploadAttachmentRequest().getProfileId()
                        + "/upload?description="
                        + feiUploadAttachmentRequest().getDescription())
                        .file(file))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
