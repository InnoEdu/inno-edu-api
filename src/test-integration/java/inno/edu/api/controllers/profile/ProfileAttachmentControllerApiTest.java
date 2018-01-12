package inno.edu.api.controllers.profile;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class ProfileAttachmentControllerApiTest extends ApiTest {
    @Test
    public void shouldListProfileAttachments() throws Exception {
        this.mockMvc.perform(get("/api/profiles/"
                + feiProfile().getId()
                + "/attachments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.attachmentResourceList[*].id", containsInAnyOrder(attachment().getId().toString())))
                .andExpect(jsonPath("$._embedded.attachmentResourceList[*].description", containsInAnyOrder(attachment().getDescription())))
                .andExpect(jsonPath("$._embedded.attachmentResourceList[*].url", containsInAnyOrder(attachment().getUrl())));
    }

    @Test
    public void shouldCreateProfileAttachment() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "temporary.json", null, "bar".getBytes());

        this.mockMvc.perform(
                fileUpload("/api/profiles/"
                        + feiProfile().getId()
                        + "/attachments?description="
                        + createAttachmentRequest().getDescription())
                        .file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(attachment().getId().toString())))
                .andExpect(jsonPath("$.description", is(attachment().getDescription())))
                .andExpect(jsonPath("$.url", is(attachment().getUrl())));
    }


//    @Test
//    public void shouldDeleteAttachment() throws Exception {
//        this.mockMvc.perform(
//                delete("/api/attachments/" + attachment().getId()))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//    }
}
