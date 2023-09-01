package ru.clevertec.stpnbelko.model;

import lombok.Getter;

//@Getter
public enum OperationType {

    TRANSFER(1, "Перевод"),
    WITHDRAWAL(2, "Снятие"),
    REFIL(3, "Пополнение"),
    STATEMENT(4, "Выписка"),
    INTEREST_CALCULATION(5, "Рассчёт процентов");

    public final String descr;
    public final int id;

    OperationType(int id, String descr) {
        this.id = id;
        this.descr = descr;
    }

    @Override
    public String toString() {
        return  id + "  " + descr + " \n";
    }

}
