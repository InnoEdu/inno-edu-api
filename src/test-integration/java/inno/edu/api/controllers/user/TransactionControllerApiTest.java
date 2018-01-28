package inno.edu.api.controllers.user;

import inno.edu.api.ApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static inno.edu.api.support.Payloads.postTransactionPayload;
import static inno.edu.api.support.Payloads.putTransactionPayload;
import static inno.edu.api.support.UserFactory.createFeiTransactionRequest;
import static inno.edu.api.support.UserFactory.feiBalance;
import static inno.edu.api.support.UserFactory.feiTransaction;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.updateFeiTransactionRequest;
import static inno.edu.api.support.UserFactory.updatedFeiTransaction;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerApiTest extends ApiTest {
    @Test
    public void shouldListUserTransactions() throws Exception {
        this.mockMvc.perform(get("/api/users/" + fei().getId() + "/transactions")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.transactionResourceList[*].id", hasItems(feiTransaction().getId().toString())))
                .andExpect(jsonPath("$._embedded.transactionResourceList[*].userId", hasItems(feiTransaction().getUserId().toString())))
                .andExpect(jsonPath("$._embedded.transactionResourceList[*].appointmentId", hasItems(feiTransaction().getAppointmentId().toString())))
                .andExpect(jsonPath("$._embedded.transactionResourceList[*].value", hasItems(feiTransaction().getValue().doubleValue())))
                .andExpect(jsonPath("$._embedded.transactionResourceList[*].type", hasItems(feiTransaction().getType().toString())));
    }

    @Test
    public void shouldGetTransactionById() throws Exception {
        this.mockMvc.perform(get("/api/users/transactions/" + feiTransaction().getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(feiTransaction().getId().toString())))
                .andExpect(jsonPath("$.userId", is(feiTransaction().getUserId().toString())))
                .andExpect(jsonPath("$.appointmentId", is(feiTransaction().getAppointmentId().toString())))
                .andExpect(jsonPath("$.value", is(feiTransaction().getValue().doubleValue())))
                .andExpect(jsonPath("$.type", is(feiTransaction().getType().toString())));
    }

    @Test
    public void shouldCreateNewTransaction() throws Exception {
        this.mockMvc.perform(
                post("/api/users/" + fei().getId() + "/transactions")
                        .content(postTransactionPayload(createFeiTransactionRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(feiTransaction().getId().toString())))
                .andExpect(jsonPath("$.userId", is(feiTransaction().getUserId().toString())))
                .andExpect(jsonPath("$.appointmentId", is(feiTransaction().getAppointmentId().toString())))
                .andExpect(jsonPath("$.value", is(feiTransaction().getValue().doubleValue())))
                .andExpect(jsonPath("$.type", is(feiTransaction().getType().toString())));
    }

    @Test
    public void shouldUpdateTransaction() throws Exception {
        this.mockMvc.perform(
                put("/api/users/transactions/" + feiTransaction().getId())
                        .content(putTransactionPayload(updateFeiTransactionRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(feiTransaction().getId().toString())))
                .andExpect(jsonPath("$.userId", is(updatedFeiTransaction().getUserId().toString())))
                .andExpect(jsonPath("$.value", is(updatedFeiTransaction().getValue().doubleValue())))
                .andExpect(jsonPath("$.type", is(updatedFeiTransaction().getType().toString())));
    }

    @Test
    public void shouldDeleteTransaction() throws Exception {
        this.mockMvc.perform(
                delete("/api/users/transactions/" + feiTransaction().getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetBalanceByUserId() throws Exception {
        this.mockMvc.perform(get("/api/users/" + fei().getId().toString() + "/balance"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(feiBalance().getUserId().toString())))
                .andExpect(jsonPath("$.value", is(feiBalance().getValue().doubleValue())));
    }
}
