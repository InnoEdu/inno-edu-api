package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import static inno.edu.api.support.AttachmentFactory.attachment;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AttachmentControllerApiTest extends ApiTest {
    @Test
    public void shouldCreateAttachment() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "temporary.json", null, "bar".getBytes());

        this.mockMvc.perform(
                fileUpload("/api/attachments?description="
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
                delete("/api/attachments/" + attachment().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
