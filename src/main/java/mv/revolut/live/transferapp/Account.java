package mv.revolut.live.transferapp;

import java.math.BigDecimal;

public class Account {
    private final Long id;
    private BigDecimal balance;

    public Account(Long id) {
        this(id, BigDecimal.ZERO);
    }

    public Account(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public synchronized void credit(BigDecimal amount) {
        this.balance = balance.add(amount);
    }

    public synchronized void debit(BigDecimal amount) {
        this.balance = balance.subtract(amount);
    }
}
