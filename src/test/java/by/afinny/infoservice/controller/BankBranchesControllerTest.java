package by.afinny.infoservice.controller;

import by.afinny.infoservice.dto.ResponseBankBranchDto;
import by.afinny.infoservice.dto.ResponseBranchCoordinatesDto;
import by.afinny.infoservice.entity.constant.BankBranchType;
import by.afinny.infoservice.service.BankBranchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(BankBranchesController.class)
@ActiveProfiles("test")
class BankBranchesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BankBranchService bankBranchService;

    private final UUID BRANCH_UUID = UUID.randomUUID();
    private final String BRANCH_NUMBER = "123";
    private final String BRANCH_COORDINATE = "55.786386, 37.682488";
    private final String CITY = "MOSCOW";
    private final String ADDRESS = "Gestalo, 6";

    private ResponseBankBranchDto bankBranch;
    private ResponseBranchCoordinatesDto filteredBranchCoordinate;
    private List<ResponseBankBranchDto> bankBranches;
    private List<ResponseBranchCoordinatesDto> filteredBranchesCoordinates;

    @BeforeEach
    void setUp() {
        bankBranch = ResponseBankBranchDto.builder()
                .bankBranchType(BankBranchType.ATM)
                .branchNumber(BRANCH_NUMBER)
                .branchCoordinates(BRANCH_COORDINATE)
                .city(CITY)
                .branchAddress(ADDRESS)
                .closed(false)
                .openingTime(Time.valueOf(LocalTime.of(8, 30)))
                .closingTime(Time.valueOf(LocalTime.of(20, 0)))
                .workAtWeekends(true)
                .cashWithdraw(true)
                .moneyTransfer(true)
                .acceptPayment(true)
                .currencyExchange(true)
                .exoticCurrency(false)
                .ramp(false)
                .replenishCard(true)
                .replenishAccount(true)
                .consultation(true)
                .insurance(false)
                .replenishWithoutCard(false)
                .build();
        filteredBranchCoordinate = ResponseBranchCoordinatesDto.builder()
                .id(BRANCH_UUID)
                .branchCoordinates(BRANCH_COORDINATE)
                .build();
        bankBranches = List.of(bankBranch);
        filteredBranchesCoordinates = List.of(filteredBranchCoordinate);
    }

    @Test
    @DisplayName("if the list of bank branches was successfully received then return status OK")
    void getBankBranches_shouldReturnBankBranches() throws Exception {
        //ARRANGE
        when(bankBranchService.getAllBankBranch())
                .thenReturn(bankBranches);
        //ACT
        MvcResult mvcResult = mockMvc.perform(
                        get("/bank-branch"))
                .andExpect(status().isOk())
                .andReturn();
        //VERIFY
        verifyBody(asJsonString(bankBranches), mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("if the list of bank branches wasn't successfully received then return Internal Server Error")
    void getExchangeRate_ifNotSuccess_thenStatus500() throws Exception {
        //ARRANGE
        when(bankBranchService.getAllBankBranch())
                .thenThrow(new RuntimeException());
        //ACT & VERIFY
        mockMvc.perform(get("/bank-branch"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("if the list of filtered bank branches was successfully received then return status OK")
    void getFilteredBankBranches_shouldReturnBranchCoordinates() throws Exception {
        //ARRANGE
        when(bankBranchService.getAllFilteredBankBranch(BankBranchType.ATM, false, false,
                false, false, false, false, false,
                false, false, false, false, false, false))
                .thenReturn(filteredBranchesCoordinates);
        //ACT
        MvcResult mvcResult = mockMvc.perform(
                        get("/bank-branch/filters")
                                .param("bankBranchType", BankBranchType.ATM.name())
                                .param("closed", "false")
                                .param("workAtWeekends", "false")
                                .param("cashWithdraw", "false")
                                .param("moneyTransfer", "false")
                                .param("acceptPayment", "false")
                                .param("currencyExchange", "false")
                                .param("exoticCurrency", "false")
                                .param("ramp", "false")
                                .param("replenishCard", "false")
                                .param("replenishAccount", "false")
                                .param("consultation", "false")
                                .param("insurance", "false")
                                .param("replenishWithoutCard", "false"))
                .andExpect(status().isOk())
                .andReturn();
        //VERIFY
        verifyBody(asJsonString(filteredBranchesCoordinates), mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("if the list of filtered bank branches wasn't successfully received then return Internal Server Error")
    void getFilteredBankBranches_ifNotSuccess_thenStatus500() throws Exception {
        //ARRANGE
        when(bankBranchService.getAllFilteredBankBranch(BankBranchType.ATM, false, false,
                        false, false, false, false, false,
                false, false, false, false, false, false))
                .thenThrow(new RuntimeException());
        //ACT & VERIFY
        mockMvc.perform(get("/bank-branch/filters")
                        .param("bankBranchType", BankBranchType.ATM.name())
                        .param("closed", "false")
                        .param("workAtWeekends", "false")
                        .param("cashWithdraw", "false")
                        .param("moneyTransfer", "false")
                        .param("acceptPayment", "false")
                        .param("currencyExchange", "false")
                        .param("exoticCurrency", "false")
                        .param("ramp", "false")
                        .param("replenishCard", "false")
                        .param("replenishAccount", "false")
                        .param("consultation", "false")
                        .param("insurance", "false")
                        .param("replenishWithoutCard", "false"))
                .andExpect(status().isInternalServerError());
    }


    private String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .writeValueAsString(obj);
    }

    private void verifyBody(String expectedBody, String actualBody) {
        assertThat(actualBody).isEqualTo(expectedBody);
    }
}