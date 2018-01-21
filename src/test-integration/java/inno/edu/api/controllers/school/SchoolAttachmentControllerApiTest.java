package inno.edu.api.controllers.school;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static inno.edu.api.support.AttachmentFactory.otherAttachment;
import static inno.edu.api.support.MockMvcUtils.getIdentity;
import static inno.edu.api.support.SchoolFactory.stanford;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SchoolAttachmentControllerApiTest extends ApiTest {
    @Test
    public void shouldListSchoolAttachments() throws Exception {
        this.mockMvc.perform(get("/api/schools/"
                + stanford().getId()
                + "/attachments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.attachmentResourceList[*].id", containsInAnyOrder(otherAttachment().getId().toString())))
                .andExpect(jsonPath("$._embedded.attachmentResourceList[*].description", containsInAnyOrder(otherAttachment().getDescription())))
                .andExpect(jsonPath("$._embedded.attachmentResourceList[*].url", containsInAnyOrder(otherAttachment().getUrl())));
    }

    @Test
    public void shouldCreateSchoolAttachment() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "temporary.json", null, "bar".getBytes());

        this.mockMvc.perform(
                fileUpload("/api/schools/"
                        + stanford().getId()
                        + "/attachments?description="
                        + createAttachmentRequest().getDescription())
                        .file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(attachment().getId().toString())))
                .andExpect(jsonPath("$.description", is(attachment().getDescription())))
                .andExpect(jsonPath("$.url", is(attachment().getUrl())));
    }


    @Test
    public void shouldDeleteAttachment() throws Exception {
        this.mockMvc.perform(
                delete("/api/schools/"
                        + stanford().getId()
                        + "/attachments/"
                        + otherAttachment().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldUploadSchoolPhoto() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "temporary.json", null, "bar".getBytes());

        MvcResult mvcResult = this.mockMvc.perform(
                fileUpload("/api/schools/"
                        + stanford().getId()
                        + "/upload-photo")
                        .file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(attachment().getId().toString())))
                .andExpect(jsonPath("$.url", is(attachment().getUrl())))
                .andReturn();

        this.mockMvc.perform(get("/api/schools/" + stanford().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photoId", is(getIdentity(mvcResult))));
    }
}
