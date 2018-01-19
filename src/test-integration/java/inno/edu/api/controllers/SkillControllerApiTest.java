package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.postSkillPayload;
import static inno.edu.api.support.Payloads.putSkillPayload;
import static inno.edu.api.support.SkillFactory.createSkillRequest;
import static inno.edu.api.support.SkillFactory.skillToDelete;
import static inno.edu.api.support.SkillFactory.skill;
import static inno.edu.api.support.SkillFactory.updateSkillRequest;
import static inno.edu.api.support.SkillFactory.updatedSkill;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SkillControllerApiTest extends ApiTest {
    @Test
    public void shouldListSkills() throws Exception {
        this.mockMvc.perform(get("/api/skills")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.skillResourceList[*].id", hasItems(skill().getId().toString())))
                .andExpect(jsonPath("$._embedded.skillResourceList[*].title", hasItems(skill().getTitle())))
                .andExpect(jsonPath("$._embedded.skillResourceList[*].description", hasItems(skill().getDescription())));
    }

    @Test
    public void shouldGetSkillById() throws Exception {
        this.mockMvc.perform(get("/api/skills/" + skill().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(skill().getId().toString())))
                .andExpect(jsonPath("$.title", is(skill().getTitle())))
                .andExpect(jsonPath("$.description", is(skill().getDescription())));
    }

    @Test
    public void shouldCreateNewSkill() throws Exception {
        this.mockMvc.perform(
                post("/api/skills")
                        .content(postSkillPayload(createSkillRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(skill().getId().toString())))
                .andExpect(jsonPath("$.title", is(skill().getTitle())))
                .andExpect(jsonPath("$.description", is(skill().getDescription())));
    }

    @Test
    public void shouldUpdateSkill() throws Exception {
        this.mockMvc.perform(
                put("/api/skills/" + skill().getId())
                        .content(putSkillPayload(updateSkillRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(skill().getId().toString())))
                .andExpect(jsonPath("$.title", is(updatedSkill().getTitle())))
                .andExpect(jsonPath("$.description", is(updatedSkill().getDescription())));
    }

    @Test
    public void shouldDeleteSkill() throws Exception {
        this.mockMvc.perform(
                delete("/api/skills/" + skillToDelete().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
