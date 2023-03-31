package by.afinny.infoservice.controller;

import by.afinny.infoservice.dto.ResponseBankBranchDto;
import by.afinny.infoservice.dto.ResponseBranchCoordinatesDto;
import by.afinny.infoservice.entity.constant.BankBranchType;
import by.afinny.infoservice.service.BankBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("bank-branch")
public class BankBranchesController {

    private final BankBranchService bankBranchService;

    @GetMapping
    public ResponseEntity<List<ResponseBankBranchDto>> getBankBranches() {
        List<ResponseBankBranchDto> responseBankBranches = bankBranchService.getAllBankBranch();
        return ResponseEntity.ok(responseBankBranches);
    }

    @GetMapping("/filters")
    public ResponseEntity<List<ResponseBranchCoordinatesDto>> getFilteredBankBranches(@RequestParam(required = false) BankBranchType bankBranchType,
                                                                                      @RequestParam(required = false) Boolean closed,
                                                                                      @RequestParam(required = false) Boolean workAtWeekends,
                                                                                      @RequestParam(required = false) Boolean cashWithdraw,
                                                                                      @RequestParam(required = false) Boolean moneyTransfer,
                                                                                      @RequestParam(required = false) Boolean acceptPayment,
                                                                                      @RequestParam(required = false) Boolean currencyExchange,
                                                                                      @RequestParam(required = false) Boolean exoticCurrency,
                                                                                      @RequestParam(required = false) Boolean ramp,
                                                                                      @RequestParam(required = false) Boolean replenishCard,
                                                                                      @RequestParam(required = false) Boolean replenishAccount,
                                                                                      @RequestParam(required = false) Boolean consultation,
                                                                                      @RequestParam(required = false) Boolean insurance,
                                                                                      @RequestParam(required = false) Boolean replenishWithoutCard) {

        List<ResponseBranchCoordinatesDto> responseBranchCoordinates = bankBranchService.getAllFilteredBankBranch(bankBranchType,
                closed, workAtWeekends, cashWithdraw, moneyTransfer, acceptPayment, currencyExchange, exoticCurrency,
                ramp, replenishCard, replenishAccount, consultation, insurance, replenishWithoutCard);
        return ResponseEntity.ok(responseBranchCoordinates);
    }
}
