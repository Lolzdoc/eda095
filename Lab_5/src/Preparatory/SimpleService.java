package Preparatory;


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class SimpleService {

    public static void main(String[] args) {
        String command = "";
        String client = "";
        String result;
        Locale format = Locale.ENGLISH;

        while (true) {
            command = receive();
            switch (command) {
                case "t":
                    result = currentTime(format);
                    break;
                case "d":
                    result = currentDate(format);
                    break;
                case "dt":
                    result = currentDateTime(format);
                    break;
                default:
                    result = "Invalid command: "+ command;
                    break;
            }
            send(client, result);
        }
    }

    private static String currentDate(Locale format) {
        Date currentDate = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, format);

        return dateFormat.format(currentDate);
    }

    private static String currentTime(Locale format) {
        Date currentDate = new Date();
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.LONG, format);

        return dateFormat.format(currentDate);
    }

    private static String currentDateTime(Locale format) {
        Date currentDate = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, format);

        return dateFormat.format(currentDate);
    }

    private static String receive() {
        System.out.println("Enter a command: ");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    private static void send(String client, String result) {
        System.out.println("Sending: " + "\""+result+"\"" + " to: " + client);
    }

}
