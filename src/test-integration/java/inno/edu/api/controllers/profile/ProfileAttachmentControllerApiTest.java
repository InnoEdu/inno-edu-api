package inno.edu.api.controllers.profile;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import static inno.edu.api.infrastructure.configuration.StaticConstants.NO_FILE;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static inno.edu.api.support.AttachmentFactory.feiAttachment;
import static inno.edu.api.support.MockMvcUtils.getIdentity;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileAttachmentControllerApiTest extends ApiTest {
    @Test
    public void shouldListProfileAttachments() throws Exception {
        this.mockMvc.perform(get("/api/profiles/"
                + feiProfile().getId()
                + "/attachments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.attachmentResourceList[*].id", containsInAnyOrder(feiAttachment().getId().toString())))
                .andExpect(jsonPath("$._embedded.attachmentResourceList[*].description", containsInAnyOrder(feiAttachment().getDescription())))
                .andExpect(jsonPath("$._embedded.attachmentResourceList[*].url", containsInAnyOrder(feiAttachment().getUrl())));
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
                .andExpect(jsonPath("$.id", not(feiAttachment().getId().toString())))
                .andExpect(jsonPath("$.description", is(feiAttachment().getDescription())))
                .andExpect(jsonPath("$.url", is(NO_FILE)));
    }

    @Test
    public void shouldDeleteAttachment() throws Exception {
        this.mockMvc.perform(
                delete("/api/profiles/"
                        + feiProfile().getId()
                        + "/attachments/"
                        + feiAttachment().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldUploadProfilePhoto() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "temporary.json", null, "bar".getBytes());

        MvcResult mvcResult = this.mockMvc.perform(
                fileUpload("/api/profiles/"
                        + feiProfile().getId()
                        + "/upload-photo")
                        .file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(feiAttachment().getId().toString())))
                .andExpect(jsonPath("$.url", is(NO_FILE)))
                .andReturn();

        this.mockMvc.perform(get("/api/profiles/" + feiProfile().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photoId", is(getIdentity(mvcResult))));
    }
}
