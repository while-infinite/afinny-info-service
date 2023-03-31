package by.afinny.infoservice.repository;

import by.afinny.infoservice.entity.BankBranch;
import by.afinny.infoservice.entity.constant.BankBranchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BankBranchRepository extends JpaRepository<BankBranch, UUID> {

    @Query("SELECT b FROM BankBranch b WHERE " +
            "(b.bankBranchType=:bankBranchType OR :bankBranchType is null) and " +
            "(b.closed=:closed OR :closed is null) and " +
            "(b.workAtWeekends=:workAtWeekends OR :workAtWeekends is null) and " +
            "(b.cashWithdraw=:cashWithdraw OR :cashWithdraw is null) and " +
            "(b.moneyTransfer=:moneyTransfer OR :moneyTransfer is null) and " +
            "(b.acceptPayment=:acceptPayment OR :acceptPayment is null) and " +
            "(b.currencyExchange=:currencyExchange OR :currencyExchange is null) and " +
            "(b.exoticCurrency=:exoticCurrency OR :exoticCurrency is null) and " +
            "(b.ramp=:ramp OR :ramp is null) and " +
            "(b.replenishCard=:replenishCard OR :replenishCard is null) and " +
            "(b.replenishAccount=:replenishAccount OR :replenishAccount is null) and " +
            "(b.consultation=:consultation OR :consultation is null) and " +
            "(b.insurance=:insurance OR :insurance is null) and " +
            "(b.replenishWithoutCard=:replenishWithoutCard OR :replenishWithoutCard is null)")
    List<BankBranch> findBankBranchByParams(@Param("bankBranchType") BankBranchType bankBranchType,
                                            @Param("closed") Boolean closed,
                                            @Param("workAtWeekends") Boolean workAtWeekends,
                                            @Param("cashWithdraw") Boolean cashWithdraw,
                                            @Param("moneyTransfer") Boolean moneyTransfer,
                                            @Param("acceptPayment") Boolean acceptPayment,
                                            @Param("currencyExchange") Boolean currencyExchange,
                                            @Param("exoticCurrency") Boolean exoticCurrency,
                                            @Param("ramp") Boolean ramp,
                                            @Param("replenishCard") Boolean replenishCard,
                                            @Param("replenishAccount") Boolean replenishAccount,
                                            @Param("consultation") Boolean consultation,
                                            @Param("insurance") Boolean insurance,
                                            @Param("replenishWithoutCard") Boolean replenishWithoutCard);

}
