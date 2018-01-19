package inno.edu.api.controllers.profile;

import inno.edu.api.ApiTest;
import org.junit.Test;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.SkillFactory.otherSkill;
import static inno.edu.api.support.SkillFactory.skill;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileSkillControllerApiTest extends ApiTest {
    @Test
    public void shouldListProfileSkills() throws Exception {
        this.mockMvc.perform(get("/api/profiles/"
                + feiProfile().getId()
                + "/skills"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.skillResourceList[*].id", hasItems(skill().getId().toString())))
                .andExpect(jsonPath("$._embedded.skillResourceList[*].title", hasItems(skill().getTitle())))
                .andExpect(jsonPath("$._embedded.skillResourceList[*].description", hasItems(skill().getDescription())));
    }

    @Test
    public void shouldCreateProfileSkill() throws Exception {
        this.mockMvc.perform(
                post("/api/profiles/"
                        + feiProfile().getId()
                        + "/skills/"
                        + otherSkill().getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profileId", is(feiProfile().getId().toString())))
                .andExpect(jsonPath("$.skillId", is(otherSkill().getId().toString())));
    }

    @Test
    public void shouldDeleteSkill() throws Exception {
        this.mockMvc.perform(
                delete("/api/profiles/"
                        + feiProfile().getId()
                        + "/skills/"
                        + skill().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
