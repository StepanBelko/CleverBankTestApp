package ru.clevertec.stpnbelko.output;

import ru.clevertec.stpnbelko.model.Receipt;

import java.text.SimpleDateFormat;


public class Writer {


    public static void writeToConsole(Receipt receipt) {
        System.out.format("___________________________________________\n");
        System.out.print("|             Банковский чек              |\n");
        System.out.format("| %10s                     %8s |\n",new SimpleDateFormat("yyyy-MM-dd").format(receipt.getDate()),new SimpleDateFormat("HH:mm:ss").format(receipt.getDate()));
        System.out.format("| Тип транзакции            %13s |\n",receipt.getOperationType());
        System.out.format("| Банк отправителя        %15s |\n",receipt.getFromBankName()); // 45 symbols);
        System.out.format("| Банк получателя         %15s |\n",receipt.getToBankName());
        System.out.format("| Счёт отправителя             %10s |\n",receipt.getFromAccountNumber());
        System.out.format("| Счёт получателя              %10s |\n",receipt.getToAccountNumber());
        System.out.format("| Сумма                   %11s %3s |\n",receipt.getAmount().abs(), receipt.getCurrency());
        System.out.format("|_________________________________________|\n");

    }

    public void writeToFile(Receipt receipt) {
    }
}
