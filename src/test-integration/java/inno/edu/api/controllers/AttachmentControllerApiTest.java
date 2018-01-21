package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static inno.edu.api.infrastructure.configuration.StaticConstants.NO_FILE;
import static inno.edu.api.support.AttachmentFactory.attachmentToDelete;
import static inno.edu.api.support.AttachmentFactory.createAttachmentRequest;
import static inno.edu.api.support.AttachmentFactory.feiAttachment;
import static inno.edu.api.support.AttachmentFactory.updateAttachmentRequest;
import static inno.edu.api.support.AttachmentFactory.updatedAttachment;
import static inno.edu.api.support.Payloads.putAttachmentPayload;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AttachmentControllerApiTest extends ApiTest {
    @Test
    public void shouldListAttachments() throws Exception {
        this.mockMvc.perform(get("/api/attachments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.attachmentResourceList[*].id", hasItems(feiAttachment().getId().toString())))
                .andExpect(jsonPath("$._embedded.attachmentResourceList[*].description", hasItems(feiAttachment().getDescription())))
                .andExpect(jsonPath("$._embedded.attachmentResourceList[*].url", hasItems(feiAttachment().getUrl())));
    }

    @Test
    public void shouldGetAttachmentById() throws Exception {
        this.mockMvc.perform(get("/api/attachments/" + feiAttachment().getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(feiAttachment().getId().toString())))
                .andExpect(jsonPath("$.description", is(feiAttachment().getDescription())))
                .andExpect(jsonPath("$.url", is(feiAttachment().getUrl())));
    }

    @Test
    public void shouldCreateAttachment() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "temporary.json", null, "bar".getBytes());

        this.mockMvc.perform(
                fileUpload("/api/attachments?description="
                        + createAttachmentRequest().getDescription())
                        .file(file))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(feiAttachment().getId().toString())))
                .andExpect(jsonPath("$.description", is(feiAttachment().getDescription())))
                .andExpect(jsonPath("$.url", is(NO_FILE)));
    }


    @Test
    public void shouldUpdateAttachment() throws Exception {
        this.mockMvc.perform(
                put("/api/attachments/" + feiAttachment().getId())
                        .content(putAttachmentPayload(updateAttachmentRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(feiAttachment().getId().toString())))
                .andExpect(jsonPath("$.description", is(updatedAttachment().getDescription())))
                .andExpect(jsonPath("$.url", is(updatedAttachment().getUrl())));
    }

    @Test
    public void shouldDeleteAttachment() throws Exception {
        this.mockMvc.perform(
                delete("/api/attachments/" + attachmentToDelete().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
