package by.afinny.infoservice.repository;

import by.afinny.infoservice.entity.BankBranch;
import by.afinny.infoservice.entity.constant.BankBranchType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/schema-h2.sql"}
)
@ActiveProfiles("test")
public class BankBranchRepositoryTest {

    @Autowired
    private BankBranchRepository bankBranchRepository;

    private final String BRANCH_NUMBER = "123";
    private final String BRANCH_COORDINATE = "55.786386, 37.682488";
    private final String CITY = "MOSCOW";
    private final String ADDRESS = "Gestalo, 6";

    private BankBranch bankBranch;

    @BeforeEach
    void setUp() {
        bankBranch = BankBranch.builder()
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
    }

    @AfterEach
    void cleanUp() {
        bankBranchRepository.deleteAll();
    }

    @Test
    @DisplayName("If bank branch not exist then return empty")
    void findAll_ifBankBranchNotExist_thenReturnEmpty() {
        //ACT
        List<BankBranch> result = bankBranchRepository.findAll();
        //VERIFY
        assertThat(result).isEmpty();
    }

    @Test
    void findBankBranchByParams_ifExist_theReturnBankBranch() {
        //ACT
        bankBranch = bankBranchRepository.save(bankBranch);
        //ACT
        List<BankBranch> branch = bankBranchRepository.findBankBranchByParams(BankBranchType.ATM, false, true,
                true, true, true, true, false, false,
                true, true, true, false, false);
        //VERIFY
        System.out.println(branch);
        assertThat(branch).isNotNull();
    }
}
