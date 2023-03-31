package by.afinny.infoservice.integration.controller;

import by.afinny.infoservice.config.annotation.TestWithPostgresContainer;
import by.afinny.infoservice.dto.ResponseBankBranchDto;
import by.afinny.infoservice.entity.BankBranch;
import by.afinny.infoservice.entity.constant.BankBranchType;
import by.afinny.infoservice.repository.BankBranchRepository;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestWithPostgresContainer
@Sql(scripts = "/truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Integration test for bank branches")
public class BankBranchesControllerIT {

    @MockBean
    private BankBranchService bankBranchService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BankBranchRepository repository;
    private List<ResponseBankBranchDto> bankBranches;

    @BeforeEach
    void setUp() {
        BankBranch bankBranch = BankBranch.builder()
                .bankBranchType(BankBranchType.ATM)
                .branchNumber("123")
                .branchCoordinates("55.786386, 37.682488")
                .city("Moscow")
                .branchAddress("Gestalo, 6")
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
        repository.save(bankBranch);
        bankBranches = bankBranchService.getAllBankBranch();
    }

    @Test
    @DisplayName("if the list of bank branches was successfully received then return status OK")
    public void getBankBranches_shouldReturnBankBranches() throws Exception {
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
    @DisplayName("if the list of bank branches wasn't successfully received then return runtime exception")
    void getBankBranches_ifNotSuccess_thenThrowRuntimeException() throws Exception {
        //ARRANGE
        when(bankBranchService.getAllBankBranch())
                .thenThrow(new RuntimeException());
        //ACT & VERIFY
        mockMvc.perform(get("/bank-branch"))
                .andExpect(status().isInternalServerError());
    }


    private String asJsonString(Object obj) throws JsonProcessingException {
        return objectMapper
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .writeValueAsString(obj);
    }


    private void verifyBody(String expectedBody, String actualBody) {
        assertThat(actualBody).isEqualTo(expectedBody);
    }

}
