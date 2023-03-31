package by.afinny.infoservice.dto;

import by.afinny.infoservice.entity.constant.BankBranchType;
import lombok.*;

import java.sql.Time;

@Builder
@Getter
@Setter(AccessLevel.PUBLIC)
@ToString
public class ResponseBankBranchDto {

    private BankBranchType bankBranchType;
    private String branchNumber;
    private String branchCoordinates;
    private String city;
    private String branchAddress;
    private Boolean closed;
    private Time openingTime;
    private Time closingTime;
    private Boolean workAtWeekends;
    private Boolean cashWithdraw;
    private Boolean moneyTransfer;
    private Boolean acceptPayment;
    private Boolean currencyExchange;
    private Boolean exoticCurrency;
    private Boolean ramp;
    private Boolean replenishCard;
    private Boolean replenishAccount;
    private Boolean consultation;
    private Boolean insurance;
    private Boolean replenishWithoutCard;
}
