package by.afinny.infoservice.service;

import by.afinny.infoservice.dto.ResponseBankBranchDto;
import by.afinny.infoservice.dto.ResponseBranchCoordinatesDto;
import by.afinny.infoservice.entity.BankBranch;
import by.afinny.infoservice.entity.constant.BankBranchType;
import by.afinny.infoservice.mapper.ResponseBankBranchMapper;
import by.afinny.infoservice.mapper.ResponseBankBranchMapperImpl;
import by.afinny.infoservice.repository.BankBranchRepository;
import by.afinny.infoservice.service.impl.BankBranchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class BankBranchServiceTest {

    @InjectMocks
    private BankBranchServiceImpl bankBranchService;
    @Mock
    private BankBranchRepository bankBranchRepository;
    @Spy
    private ResponseBankBranchMapper responseBankBranchMapper = new ResponseBankBranchMapperImpl();

    private final String BRANCH_NUMBER = "123";
    private final String BRANCH_COORDINATE = "55.786386, 37.682488";
    private final String CITY = "MOSCOW";
    private final String ADDRESS = "Gestalo, 6";

    private BankBranch bankBranch;
    private List<BankBranch> bankBranches;

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

        bankBranches = List.of(bankBranch);
    }

    @Test
    @DisplayName("check equals updateAt before mapping and after: passed if true")
    void getBankBranches_shouldReturnListBankBranch() {
        //ARRANGE
        when(bankBranchRepository.findAll())
                .thenReturn(bankBranches);
        //ACT
        List<ResponseBankBranchDto> bankBranches = bankBranchService.getAllBankBranch();
        //VERIFY
        verifyResponseBankBranchList(bankBranches);
    }

    @Test
    @DisplayName("when service throws exception return status BAD REQUEST")
    void getBankBranches_ifNotSuccess_thenThrow() {
        //ARRANGE
        when(bankBranchRepository.findAll())
                .thenThrow(RuntimeException.class);
        //ACT & VERIFY
        assertThatThrownBy(() -> bankBranchService.getAllBankBranch())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("when find bank branch by params then list should not be empty and sizes should be equals")
    void getFilteredBankBranches_shouldReturnListResponseBranchCoordinates() {
        //ARRANGE
        when(bankBranchRepository.findBankBranchByParams(BankBranchType.ATM, false, true,
                true, true, true, true, false, false,
                true, true, true, false, false))
                .thenReturn(bankBranches);
        //ACT
        List<ResponseBranchCoordinatesDto> result =
                bankBranchService.getAllFilteredBankBranch(BankBranchType.ATM, false, true,
                        true, true, true, true, false, false,
                        true, true, true, false, false);
        //VERIFY
        verifyResponseBranchCoordinatesList(result);
    }

    @Test
    @DisplayName("when getFilteredBankBranches throws exception return status BAD REQUEST")
    void getFilteredBankBranches_ifNotSuccess_thenThrow() {
        //ARRANGE
        when(bankBranchRepository.findBankBranchByParams(BankBranchType.ATM, false, true,
                true, true, true, true, false, false,
                true, true, true, false, false))
                .thenThrow(RuntimeException.class);
        //ACT & VERIFY
        assertThatThrownBy(() -> bankBranchService.getAllFilteredBankBranch(BankBranchType.ATM, false, true,
                true, true, true, true, false, false,
                true, true, true, false, false))
                .isInstanceOf(RuntimeException.class);
    }

    private void verifyResponseBankBranchList(List<ResponseBankBranchDto> bankBranch) {
        assertSoftly(softly -> {
            softly.assertThat(bankBranch.isEmpty())
                    .withFailMessage("List should not be empty")
                    .isEqualTo(false);
            softly.assertThat(bankBranch.size())
                    .withFailMessage("List sizes should be equals")
                    .isEqualTo(bankBranches.size());
        });
    }

    private void verifyResponseBranchCoordinatesList(List<ResponseBranchCoordinatesDto> filteredBankBranch) {
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(filteredBankBranch.size())
                    .withFailMessage("List sizes should be equals")
                    .isEqualTo(bankBranches.size());
        });
    }
}