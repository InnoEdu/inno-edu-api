package inno.edu.api.controllers;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.factories.UniversityFactory.otherUniversity;
import static inno.edu.api.factories.UniversityFactory.university;
import static inno.edu.api.factories.UniversityFactory.universityPayload;
import static inno.edu.api.factories.UniversityFactory.updatedUniversity;
import static java.lang.String.format;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UniversityControllerApiTest extends ApiTest {
    @Test
    public void shouldListUniversities() throws Exception {
        this.mockMvc.perform(get("/api/universities")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.universityResourceList[*].id", containsInAnyOrder(university().getId().toString(), otherUniversity().getId().toString())))
                .andExpect(jsonPath("$._embedded.universityResourceList[*].name", containsInAnyOrder(university().getName(), otherUniversity().getName())));
    }

    @Test
    public void shouldGetUniversityById() throws Exception {
        this.mockMvc.perform(get("/api/universities/" + university().getId().toString())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(is(university().getId().toString()))))
                .andExpect(jsonPath("$.name", is(university().getName())));
    }

    @Test
    public void shouldCreateNewUniversity() throws Exception {
        this.mockMvc.perform(
                post("/api/universities")
                        .content(format(universityPayload(), university().getName()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdateUniversity() throws Exception {
        this.mockMvc.perform(
                put("/api/universities/" + university().getId())
                        .content(format(universityPayload(), updatedUniversity().getName()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(is(university().getId().toString()))))
                .andExpect(jsonPath("$.name", is(updatedUniversity().getName())));
    }

    @Test
    public void shouldDeleteUniversity() throws Exception {
        this.mockMvc.perform(
                delete("/api/universities/" + university().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
