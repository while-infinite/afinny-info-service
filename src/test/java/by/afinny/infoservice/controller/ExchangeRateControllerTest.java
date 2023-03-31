package by.afinny.infoservice.controller;

import by.afinny.infoservice.dto.ExchangeRateDto;
import by.afinny.infoservice.entity.constant.CurrencyCode;
import by.afinny.infoservice.exception.handler.ExceptionHandlerController;
import by.afinny.infoservice.service.ExchangeRateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(ExchangeRateController.class)
@ActiveProfiles("test")
class ExchangeRateControllerTest {

    private final CurrencyCode USD = CurrencyCode.USD;
    private final CurrencyCode CAD = CurrencyCode.CAD;
    @MockBean
    private ExchangeRateService exchangeRateService;
    private MockMvc mockMvc;
    private List<ExchangeRateDto> exchangeRateDtoList;
    private ExchangeRateDto from;
    private ExchangeRateDto to;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(new ExchangeRateController(exchangeRateService))
                .setControllerAdvice(new ExceptionHandlerController()).build();

        from = ExchangeRateDto.builder()
                .buyingRate(new BigDecimal(100))
                .currencyCode(USD)
                .sellingRate(new BigDecimal(100))
                .unit(1).build();

        to = ExchangeRateDto.builder()
                .buyingRate(new BigDecimal(111))
                .currencyCode(CAD)
                .sellingRate(new BigDecimal(111))
                .unit(11).build();

        exchangeRateDtoList = List.of(from, to);
    }


    @Test
    @DisplayName("If getExchangeRatesByCurrencyCode was successfully then return content")
    void getExchangeRatesByCurrencyCode_shouldReturnContent() throws Exception {
        //ARRANGE
        when(exchangeRateService.getExchangeRateByCurrencyCode(any(CurrencyCode.class)))
                .thenReturn(from)
                .thenReturn(to);

        //ACT
        MvcResult result = mockMvc.perform(
                        get("/auth/rates")
                                .param("currencyCodeFrom", USD.name())
                                .param("currencyCodeTo", CAD.name()))
                .andExpect(status().isOk())
                .andReturn();

        //VERIFY
        verifyBody(asJsonString(exchangeRateDtoList), result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("If getExchangeRatesByCurrencyCode wasn't successfully return status INTERNAL SERVER ERROR")
    void getExchangeRatesByCurrencyCode_ifNotSuccess_thenStatus500() throws Exception {
        //ARRANGE
        when(exchangeRateService.getExchangeRateByCurrencyCode(any(CurrencyCode.class))).thenThrow(new RuntimeException());

        //ACT & VERIFY
        mockMvc.perform(
                        get("/auth/rates")
                                .param("currencyCodeFrom", USD.name())
                                .param("currencyCodeTo", CAD.name()))
                .andExpect(status().isInternalServerError());
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(obj);
    }

    private void verifyBody(String expectedBody, String actualBody) {
        assertThat(actualBody).isEqualTo(expectedBody);
    }
}