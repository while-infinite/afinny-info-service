package by.afinny.infoservice.service;

import by.afinny.infoservice.dto.ResponseBankBranchDto;
import by.afinny.infoservice.dto.ResponseBranchCoordinatesDto;
import by.afinny.infoservice.entity.constant.BankBranchType;

import java.util.List;

public interface BankBranchService {

    List<ResponseBankBranchDto> getAllBankBranch();

    List<ResponseBranchCoordinatesDto> getAllFilteredBankBranch(BankBranchType bankBranchType,
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
                                                                Boolean replenishWithoutCard);
}
