package ru.clevertec.stpnbelko.output;

import ru.clevertec.stpnbelko.model.OperationType;
import ru.clevertec.stpnbelko.model.Receipt;
import ru.clevertec.stpnbelko.model.Statement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class Writer {

    public static String createCheckFromReceipt(Receipt receipt) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("___________________________________________\n");
        stringBuffer.append("|             Банковский чек              |\n");
        stringBuffer.append(String.format("| %10s                     %8s |\n",
                new SimpleDateFormat("yyyy-MM-dd").format(receipt.getCurrentTime()),
                new SimpleDateFormat("HH:mm:ss").format(receipt.getCurrentTime())));
        stringBuffer.append(String.format("| Тип транзакции            %13s |\n", receipt.getOperationType().descr));
        stringBuffer.append(String.format("| Банк отправителя        %15s |\n", receipt.getFromBankName())); // 45 symbols);
        stringBuffer.append(String.format("| Банк получателя         %15s |\n", receipt.getToBankName()));
        stringBuffer.append(String.format("| Счёт отправителя             %10s |\n", receipt.getFromAccountNumber()));
        stringBuffer.append(String.format("| Счёт получателя              %10s |\n", receipt.getToAccountNumber()));
        stringBuffer.append(String.format("| Сумма                   %11s %3s |\n", receipt.getAmount().abs(), receipt.getCurrency()));
        stringBuffer.append("|_________________________________________|\n");

        return stringBuffer.toString();
    }


/*    public static void main(String[] args) {
        Timestamp timestamp2 = Timestamp.from(Instant.now());

        Statement statement = new Statement("Ivan Ivanov", 1234567890, "Cleverbank", "USD", timestamp2, timestamp2, timestamp2, new BigDecimal(9999999), new BigDecimal(9999999), new BigDecimal(9999999));
        System.out.println(createCheckFromStatement(statement));
    }*/

    public static String createCheckFromStatement(Statement statement) {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("                    Money Statement                    \n"); //45 symbols
        stringBuffer.append(String.format("                %17s                      \n", statement.getBankName()));
        stringBuffer.append("Клиент                    |   ").append(statement.getOwnerFullName()).append("\n");
        stringBuffer.append("Счёт                      |   ").append(statement.getId()).append("\n");
        stringBuffer.append("Валюта                    |   ").append(statement.getCurrency()).append("\n");
        stringBuffer.append("Дата открытия             |   ").append(
                new SimpleDateFormat("yyyy-MM-dd").format(statement.getOpeningDate())).append("\n");
        stringBuffer.append("Период                    |   ").append(
                new SimpleDateFormat("yyyy-MM-dd - ").format(statement.getOpeningDate())).append(
                new SimpleDateFormat("yyyy-MM-dd").format(statement.getCheckDate())).append("\n");
        stringBuffer.append("Дата и время формирования |   ").append(
                new SimpleDateFormat("yyyy-MM-dd   HH:mm").format(statement.getCheckDate())).append("\n");
        stringBuffer.append("Остаток                   |   ").append(statement.getBalance()).append(" ").append(statement.getCurrency()).append("\n");
        stringBuffer.append("          Приход       |       Уход\n");
        stringBuffer.append(String.format("       %9s %3s   |    %9s %s\n", statement.getTotalIncome(), statement.getCurrency(),
                "-" + statement.getTotalSpending(), statement.getCurrency()));

        return stringBuffer.toString();
    }


    public static void writeToConsole(String string) {
        System.out.println(string);
    }

    public static void writeToFile(Receipt receipt) {
        String string = createCheckFromReceipt(receipt);

        File fileDirectory = new File("check");
        File file = new File(fileDirectory, String.format(String.format("%10s %8s|%s.txt",
                new SimpleDateFormat("yyyy-MM-dd").format(receipt.getCurrentTime()),
                new SimpleDateFormat("HH:mm:ss").format(receipt.getCurrentTime()),
                receipt.getOperationType().name())));


        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(string);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void writeToFile(Statement statement) {
        String string = createCheckFromStatement(statement);

        File fileDirectory = new File("check");
        File file = new File(fileDirectory, String.format(String.format("%10s %8s|%s.txt",
                new SimpleDateFormat("yyyy-MM-dd").format(statement.getCurrentTime()),
                new SimpleDateFormat("HH:mm:ss").format(statement.getCurrentTime()),
                OperationType.STATEMENT.name())));


        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(string);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
