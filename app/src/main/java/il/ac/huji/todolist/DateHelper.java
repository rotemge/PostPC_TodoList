package il.ac.huji.todolist;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    private static final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

    public static String formatDate(Date date) {
        return (null == date) ? "" : dateFormat.format(date);
    }

    public static Date getZeroTimeDate(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static boolean isPast(String date){
        try {
            return isPast(dateFormat.parse(date));
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isPast(Date date){
        if(null == date){
            return false;
        }
        date = getZeroTimeDate(date);
        Date today = DateHelper.getZeroTimeDate(Calendar.getInstance().getTime());
        return today.after(date);
    }
}
