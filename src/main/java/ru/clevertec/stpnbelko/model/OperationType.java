package ru.clevertec.stpnbelko.model;

import ru.clevertec.stpnbelko.model.transaction.Transaction;

import java.math.BigDecimal;
import java.util.Scanner;

public enum OperationType {

    TRANSFER(1, "Перевод"),
    WITHDRAWAL(2, "Снятие"),
    REFIL(3, "Пополнение"),
    STATEMENT(4, "Баланс"),
    INTEREST_CALCULATION(5, "Рассчёт процентов");

    public final String descr;
    public final int id;

    OperationType(int id, String descr) {
        this.id = id;
        this.descr = descr;
    }

    @Override
    public String toString() {
        return id + "  " + descr + " \n";
    }

    public void fillTransaction(Account currentAccount, Transaction transaction) {
        switch (transaction.getOperationType()) {
            case REFIL -> {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Какую сумму вы хотите внести?");
                BigDecimal amount = BigDecimal.valueOf(scanner.nextDouble());

                transaction.setAmount(amount);
                transaction.setFromAccountNumber(currentAccount.getId());
                transaction.setToAccountNumber(currentAccount.getId());
            }
            case TRANSFER -> {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Выберите получателя (введите 10-значный номер счёта)");
                transaction.setFromAccountNumber(currentAccount.getId());
                transaction.setToAccountNumber(scanner.nextInt());

                System.out.println("Какую сумму вы хотите отправить?");

                BigDecimal amount = null;
                do {
                    System.out.println("Не больше " + currentAccount.getBalance() + " " + currentAccount.getCurrency());
                    amount = BigDecimal.valueOf(scanner.nextDouble());
                } while (currentAccount.getBalance().compareTo(amount) == -1);
                transaction.setAmount(amount);
            }
            case WITHDRAWAL -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Какую сумму вы хотите снять?");
                BigDecimal amount = BigDecimal.valueOf(-(scanner.nextDouble()));

                transaction.setAmount(amount);
                transaction.setFromAccountNumber(currentAccount.getId());
                transaction.setToAccountNumber(currentAccount.getId());
            }
            case STATEMENT -> {
                transaction.setAmount(new BigDecimal(0));
                transaction.setFromAccountNumber(currentAccount.getId());
                transaction.setToAccountNumber(currentAccount.getId());
            }
        }
    }

}
