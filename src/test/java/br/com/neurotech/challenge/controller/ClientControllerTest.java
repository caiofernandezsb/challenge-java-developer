package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.dto.EligibleClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateClient() throws Exception {
        NeurotechClient client = new NeurotechClient();
        client.setName("João");
        client.setAge(24);
        client.setIncome(7000.0);

        when(clientService.save(client)).thenReturn("1");

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/client/1"));
    }

    @Test
    void testGetClientByIdFound() throws Exception {
        NeurotechClient client = new NeurotechClient();
        client.setId(1L);
        client.setName("João");
        client.setAge(24);
        client.setIncome(7000.0);

        when(clientService.get("1")).thenReturn(client);

        mockMvc.perform(get("/api/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João"))
                .andExpect(jsonPath("$.income").value(7000.0));
    }

    @Test
    void testGetClientByIdNotFound() throws Exception {
        when(clientService.get("99")).thenThrow(new ClientNotFoundException("99"));

        mockMvc.perform(get("/api/client/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente de id 99 não encontrado"));
    }

    @Test
    void testCreateClientWithInvalidData() throws Exception {
        NeurotechClient invalidClient = new NeurotechClient();
        invalidClient.setName("");
        invalidClient.setAge(15);
        invalidClient.setIncome(-1000.0);

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("O nome do cliente deve ser informado")));
    }

    @Test
    void testGetEligibleClientsForHatchFixRate() throws Exception {
        List<EligibleClientDTO> result = List.of(
                new EligibleClientDTO("Caio", 8000.0),
                new EligibleClientDTO("Lucas", 12000.0)
        );

        when(clientService.getEligibleClientsForHatchFixRate()).thenReturn(result);

        mockMvc.perform(get("/api/client/hatch-fix-rate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Caio"))
                .andExpect(jsonPath("$[1].income").value(12000.0));
    }

    @Test
    void testGetEligibleClientsForHatchFixRate_NoClients() throws Exception {
        when(clientService.getEligibleClientsForHatchFixRate()).thenReturn(List.of());

        mockMvc.perform(get("/api/client/hatch-fix-rate"))
                .andExpect(status().isNoContent());
    }
}
