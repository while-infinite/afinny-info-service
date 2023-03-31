package by.afinny.infoservice.mapper;

import by.afinny.infoservice.dto.ResponseBankBranchDto;
import by.afinny.infoservice.dto.ResponseBranchCoordinatesDto;
import by.afinny.infoservice.entity.BankBranch;
import by.afinny.infoservice.entity.constant.BankBranchType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ResponseBankBranchMapperTest {

    @InjectMocks
    private ResponseBankBranchMapperImpl responseBankBranchMapper;

    private final UUID BRANCH_UUID = UUID.randomUUID();
    private final String BRANCH_NUMBER = "123";
    private final String BRANCH_COORDINATE = "55.786386, 37.682488";
    private final String CITY = "MOSCOW";
    private final String ADDRESS = "Gestalo, 6";

    private BankBranch bankBranch;
    private ResponseBankBranchDto responseBankBranchDto;
    private ResponseBranchCoordinatesDto filteredBranchCoordinate;

    @BeforeEach
    void setUp() {
        bankBranch = BankBranch.builder()
                .id(BRANCH_UUID)
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
        responseBankBranchDto = responseBankBranchMapper.toResponseBankBranch(List.of(bankBranch)).get(0);
        filteredBranchCoordinate = responseBankBranchMapper.toResponseBranchCoordinatesDto(List.of(bankBranch)).get(0);
    }

    @Test
    @DisplayName("check equals fields before mapping and after: passed if true")
    void toResponseBankBranch_CheckFields() {
        verifyResponseBankBranchFields(bankBranch, responseBankBranchDto);
    }

    @Test
    @DisplayName("check equals fields ResponseBranchCoordinatesDto before mapping and after: passed if true")
    void toResponseBranchCoordinatesDto_CheckFields() {
        verifyResponseBranchCoordinatesFields(bankBranch, filteredBranchCoordinate);
    }

    private void verifyResponseBankBranchFields(BankBranch bankBranch,
                                                ResponseBankBranchDto bankBranchDto) {
        assertSoftly(softly -> {
            softly.assertThat(bankBranch.getBranchAddress())
                    .withFailMessage("Branch address should be equals")
                    .isEqualTo(bankBranchDto.getBranchAddress());
            softly.assertThat(bankBranch.getBranchNumber())
                    .withFailMessage("Branch numbers should be equals")
                    .isEqualTo(bankBranchDto.getBranchNumber());
            softly.assertThat(bankBranch.getCity())
                    .withFailMessage("Cities should be equals")
                    .isEqualTo(bankBranchDto.getCity());
            softly.assertThat(bankBranch.getAcceptPayment())
                    .withFailMessage("Accept payments should be equals")
                    .isEqualTo(bankBranchDto.getAcceptPayment());
            softly.assertThat(bankBranch.getClosed())
                    .withFailMessage("Accept payments should be equals")
                    .isEqualTo(bankBranchDto.getClosed());
            softly.assertThat(bankBranch.getInsurance())
                    .withFailMessage("Insurances should be equals")
                    .isEqualTo(bankBranchDto.getInsurance());
            softly.assertThat(bankBranch.getOpeningTime())
                    .withFailMessage("Opening times should be equals")
                    .isEqualTo(bankBranchDto.getOpeningTime());
            softly.assertThat(bankBranch.getClosingTime())
                    .withFailMessage("Closing times should be equals")
                    .isEqualTo(bankBranchDto.getClosingTime());
            softly.assertThat(bankBranch.getReplenishWithoutCard())
                    .withFailMessage("Replenish without card should be equals")
                    .isEqualTo(bankBranchDto.getReplenishWithoutCard());

        });
    }

    private void verifyResponseBranchCoordinatesFields(BankBranch bankBranch,
                                                       ResponseBranchCoordinatesDto responseBranchCoordinatesDto) {
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(bankBranch.getId())
                    .withFailMessage("UUID should be equals")
                    .isEqualTo(responseBranchCoordinatesDto.getId());
            softAssertions.assertThat(bankBranch.getBranchCoordinates())
                    .withFailMessage("BranchCoordinates should be equals")
                    .isEqualTo(responseBranchCoordinatesDto.getBranchCoordinates());

        });
    }
}