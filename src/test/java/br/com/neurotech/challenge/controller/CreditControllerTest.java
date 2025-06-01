package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.CreditService;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreditController.class)
class CreditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditService creditService;

    @Test
    void testCheckCreditEligible() throws Exception {
        when(creditService.checkCredit("1", VehicleModel.HATCH)).thenReturn(true);

        mockMvc.perform(get("/api/credit/check")
                        .param("clientId", "1")
                        .param("vehicleModel", "HATCH"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testCheckCreditNotEligible() throws Exception {
        when(creditService.checkCredit("2", VehicleModel.SUV)).thenReturn(false);

        mockMvc.perform(get("/api/credit/check")
                        .param("clientId", "2")
                        .param("vehicleModel", "SUV"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void testCheckCreditClientNotFound() throws Exception {
        when(creditService.checkCredit("99", VehicleModel.SUV))
                .thenThrow(new ClientNotFoundException("99"));

        mockMvc.perform(get("/api/credit/check")
                        .param("clientId", "99")
                        .param("vehicleModel", "SUV"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente de id 99 não encontrado"));
    }

}