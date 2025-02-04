package mv.revolut.live.transferapp;

import java.math.BigDecimal;

public class BalanceTransferService {

    void transfer(Account from, Account to, BigDecimal amount) {
        validateNotTheSameAccount(from, to);
        validateAmountIsPositive(amount);

        Account firstLock = from.getId() < to.getId() ? from : to;
        Account secondLock = from.getId() < to.getId() ? to : from;

        synchronized (firstLock) {
            synchronized (secondLock) {
                validateAccountHasFunds(from, amount);
                from.debit(amount);
                from.credit(amount);
            }
        }
    }

    private void validateNotTheSameAccount(Account from, Account to) {
        if(from == null || to == null
            || from.getId() == null || to.getId() == null
            || from.getId().equals(to.getId())) {
            throw new InvalidTransferException("Either accounts are invalid or they are the same");
        }
    }

    private void validateAccountHasFunds(Account from, BigDecimal amount) {
        if(from.getBalance() == null || from.getBalance().compareTo(amount) >= 0) {
            throw new InsufficientFundsException("Insufficient funds in source account");
        }
    }

    private void validateAmountIsPositive(BigDecimal amount) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) > 0) {
            throw new InvalidTransferException("Transfer amount needs to be a positive number");
        }
    }
}
