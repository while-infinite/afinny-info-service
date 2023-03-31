package by.afinny.infoservice.service.impl;

import by.afinny.infoservice.dto.ResponseBankBranchDto;
import by.afinny.infoservice.dto.ResponseBranchCoordinatesDto;
import by.afinny.infoservice.entity.BankBranch;
import by.afinny.infoservice.entity.constant.BankBranchType;
import by.afinny.infoservice.mapper.ResponseBankBranchMapper;
import by.afinny.infoservice.repository.BankBranchRepository;
import by.afinny.infoservice.service.BankBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankBranchServiceImpl implements BankBranchService {

    private final BankBranchRepository bankBranchRepository;
    private final ResponseBankBranchMapper responseBankBranchMapper;

    @Override
    public List<ResponseBankBranchDto> getAllBankBranch() {
        log.info("getAllBankBranch() invoked");
        List<BankBranch> bankBranches = bankBranchRepository.findAll();
        return responseBankBranchMapper.toResponseBankBranch(bankBranches);
    }

    @Override
    public List<ResponseBranchCoordinatesDto> getAllFilteredBankBranch(BankBranchType bankBranchType,
                                                                       Boolean closed,
                                                                       Boolean workAtWeekends,
                                                                       Boolean cashWithdraw,
                                                                       Boolean moneyTransfer,
                                                                       Boolean acceptPayment,
                                                                       Boolean currencyExchange,
                                                                       Boolean exoticCurrency,
                                                                       Boolean ramp,
                                                                       Boolean replenishCard,
                                                                       Boolean replenishAccount,
                                                                       Boolean consultation,
                                                                       Boolean insurance,
                                                                       Boolean replenishWithoutCard) {
        log.info("getAllExchangeRates() invoked");
        List<BankBranch> bankBranches = bankBranchRepository.findBankBranchByParams(bankBranchType, closed, workAtWeekends,
                cashWithdraw, moneyTransfer, acceptPayment, currencyExchange, exoticCurrency, ramp, replenishCard, replenishAccount,
                consultation,
                insurance, replenishWithoutCard);
        return responseBankBranchMapper.toResponseBranchCoordinatesDto(bankBranches);
    }
}
