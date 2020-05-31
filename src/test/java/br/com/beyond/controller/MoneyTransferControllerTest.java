package br.com.beyond.controller;

import br.com.beyond.controller.dto.TransferDTO;
import br.com.beyond.service.MoneyTransfer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {MoneyTransferController.class})
public class MoneyTransferControllerTest {

    static String API = "/moneytransfer";

    @MockBean
    private MoneyTransfer moneyTransfer;

    @Autowired
    private MockMvc mvc;

    // This object will be magically initialized by initFields methos below
    private JacksonTester<TransferDTO> jsonResult;
    private JacksonTester<Boolean> jsonResponse;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    @DisplayName("Must transfer funds")
    public void mustTransferFundsBetweenAccounts() throws Exception {
        // Cenário
        TransferDTO dto = TransferDTO.builder().originAccountId(1).destinyAccountId(2).amouth(BigDecimal.valueOf(200.0)).build();

        BDDMockito
                .given(moneyTransfer
                        .transfer(dto.getOriginAccountId(), dto.getDestinyAccountId(), dto.getAmouth()))
                .willReturn(true);


        // Ação
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                        .post(API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonResult.write(dto).getJson()))
                .andReturn().getResponse();

        // Verificação
        assertThat( response.getStatus() ).isEqualTo(HttpStatus.OK.value());
        assertThat( response.getContentAsString() )
                .isEqualTo( "Founds transferred successfully" );

    }

}
