package by.afinny.infoservice.entity;

import by.afinny.infoservice.entity.constant.BankBranchType;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.UUID;

@Entity
@Table(name = BankBranch.TABLE_NAME)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
@ToString
public class BankBranch {

    public static final String TABLE_NAME = "bank_branch";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "bank_branch_type", length = 17, nullable = false)
    @Enumerated(EnumType.STRING)
    private BankBranchType bankBranchType;

    @Column(name = "branch_number", length = 5, nullable = false)
    private String branchNumber;

    @Column(name = "branch_coordinates", length = 30, nullable = false)
    private String branchCoordinates;

    @Column(name = "city", length = 20, nullable = false)
    private String city;

    @Column(name = "branch_address", length = 20, nullable = false)
    private String branchAddress;

    @Column(name = "is_closed", nullable = false)
    private Boolean closed;

    @Column(name = "opening_time", nullable = false)
    private Time openingTime;

    @Column(name = "closing_time", nullable = false)
    private Time closingTime;

    @Column(name = "work_at_weekends", nullable = false)
    private Boolean workAtWeekends;

    @Column(name = "cash_withdraw", nullable = false)
    private Boolean cashWithdraw;

    @Column(name = "money_transfer", nullable = false)
    private Boolean moneyTransfer;

    @Column(name = "accept_payment", nullable = false)
    private Boolean acceptPayment;

    @Column(name = "currency_exchange", nullable = false)
    private Boolean currencyExchange;

    @Column(name = "exotic_currency", nullable = false)
    private Boolean exoticCurrency;

    @Column(name = "ramp", nullable = false)
    private Boolean ramp;

    @Column(name = "replenish_card", nullable = false)
    private Boolean replenishCard;

    @Column(name = "replenish_account", nullable = false)
    private Boolean replenishAccount;

    @Column(name = "consultation", nullable = false)
    private Boolean consultation;

    @Column(name = "insurance", nullable = false)
    private Boolean insurance;

    @Column(name = "replenish_without_card", nullable = false)
    private Boolean replenishWithoutCard;
}
