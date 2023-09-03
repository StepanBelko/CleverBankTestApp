package ru.clevertec.stpnbelko.output;

import ru.clevertec.stpnbelko.model.Receipt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class Writer {


    public static String writeToConsole(Receipt receipt) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("___________________________________________\n");
        stringBuffer.append("|             Банковский чек              |\n");
        stringBuffer.append(String.format("| %10s                     %8s |\n",
                new SimpleDateFormat("yyyy-MM-dd").format(receipt.getDate()),
                new SimpleDateFormat("HH:mm:ss").format(receipt.getDate())));
        stringBuffer.append(String.format("| Тип транзакции            %13s |\n", receipt.getOperationType()));
        stringBuffer.append(String.format("| Банк отправителя        %15s |\n", receipt.getFromBankName())); // 45 symbols);
        stringBuffer.append(String.format("| Банк получателя         %15s |\n", receipt.getToBankName()));
        stringBuffer.append(String.format("| Счёт отправителя             %10s |\n", receipt.getFromAccountNumber()));
        stringBuffer.append(String.format("| Счёт получателя              %10s |\n", receipt.getToAccountNumber()));
        stringBuffer.append(String.format("| Сумма                   %11s %3s |\n", receipt.getAmount().abs(), receipt.getCurrency()));
        stringBuffer.append("|_________________________________________|\n");

        System.out.println(stringBuffer);

        return stringBuffer.toString();
    }

    public static void writeToFile(Receipt receipt) {
        String string = writeToConsole(receipt);

        File fileDirectory = new File("check");
        File file = new File(fileDirectory, String.format(String.format("%10s %8s.txt",
                new SimpleDateFormat("yyyy-MM-dd").format(receipt.getDate()),
                new SimpleDateFormat("HH:mm:ss").format(receipt.getDate()))));


        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(string);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
