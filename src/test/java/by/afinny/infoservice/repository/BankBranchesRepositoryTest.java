package by.afinny.infoservice.repository;

import by.afinny.infoservice.entity.BankBranch;
import by.afinny.infoservice.entity.constant.BankBranchType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityNotFoundException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/schema-h2.sql"}
)
@ActiveProfiles("test")
public class BankBranchesRepositoryTest {

    @Autowired
    private BankBranchRepository bankBranchRepository;
    private BankBranch bankBranch;

    @BeforeAll
    void setUp() {
        bankBranch = BankBranch.builder()
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
    }

    @AfterEach
    void cleanUp() {
        bankBranchRepository.deleteAll();
    }

    @Test()
    @DisplayName("If bank branch exists then return it")
    void findById_ifBankBranchExists_thenReturnBankBranch() {
        //ARRANGE
        UUID id = bankBranchRepository.save(bankBranch).getId();
        bankBranch.setId(id);

        //ACT
        BankBranch result = getBankBranch(id);

        //VERIFY
        verifyBankBranches(bankBranch, result);
    }

    @Test()
    @DisplayName("If bank branch not exists then return empty")
    void findById_ifBankBranchNotExists_thenReturnEmpty() {
        // ARRANGE
        UUID id = UUID.randomUUID();
        //ACT
        Optional<BankBranch> result = bankBranchRepository.findById(id);
        //VERIFY
        assertThat(result).isEmpty();
    }

    private void verifyBankBranches(BankBranch expected, BankBranch actual) {
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actual.getId())
                    .isEqualTo(expected.getId());
            softAssertions.assertThat(actual.getBankBranchType())
                    .isEqualTo(expected.getBankBranchType());
            softAssertions.assertThat(actual.getBranchNumber())
                    .isEqualTo(expected.getBranchNumber());
            softAssertions.assertThat(actual.getBranchCoordinates())
                    .isEqualTo(expected.getBranchCoordinates());
            softAssertions.assertThat(actual.getCity())
                    .isEqualTo(expected.getCity());
            softAssertions.assertThat(actual.getBranchAddress())
                    .isEqualTo(expected.getBranchAddress());
            softAssertions.assertThat(actual.getClosed())
                    .isEqualTo(expected.getClosed());
            softAssertions.assertThat(actual.getOpeningTime())
                    .isEqualTo(expected.getOpeningTime());
            softAssertions.assertThat(actual.getClosingTime())
                    .isEqualTo(expected.getClosingTime());
            softAssertions.assertThat(actual.getWorkAtWeekends())
                    .isEqualTo(expected.getWorkAtWeekends());
            softAssertions.assertThat(actual.getCashWithdraw())
                    .isEqualTo(expected.getCashWithdraw());
            softAssertions.assertThat(actual.getMoneyTransfer())
                    .isEqualTo(expected.getMoneyTransfer());
            softAssertions.assertThat(actual.getAcceptPayment())
                    .isEqualTo(expected.getAcceptPayment());
            softAssertions.assertThat(actual.getCurrencyExchange())
                    .isEqualTo(expected.getCurrencyExchange());
            softAssertions.assertThat(actual.getRamp())
                    .isEqualTo(expected.getRamp());
            softAssertions.assertThat(actual.getReplenishCard())
                    .isEqualTo(expected.getReplenishCard());
            softAssertions.assertThat(actual.getReplenishAccount())
                    .isEqualTo(expected.getReplenishAccount());
            softAssertions.assertThat(actual.getConsultation())
                    .isEqualTo(expected.getConsultation());
            softAssertions.assertThat(actual.getInsurance())
                    .isEqualTo(expected.getInsurance());
        });
    }

    private BankBranch getBankBranch(UUID uuid) {
        return bankBranchRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("bank branch of " + uuid + " not found"));
    }

}
