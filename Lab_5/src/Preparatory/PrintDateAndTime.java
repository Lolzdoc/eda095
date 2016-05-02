package Preparatory;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class PrintDateAndTime {

    public static void main(String args[]) {
        Date currentDate = new Date();
        DateFormat dateFormat =
                DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,Locale.GERMAN);
        System.out.println(dateFormat.format(currentDate));
    }

}
