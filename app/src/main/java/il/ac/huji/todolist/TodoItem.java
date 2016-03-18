package il.ac.huji.todolist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodoItem {
    private String title;
    private Date dueDate;
    private DateFormat dateFormat;

    TodoItem(String todoTitle, Date todoDueDate){
        title = todoTitle;
        dueDate = (null == todoDueDate) ? todoDueDate : getZeroTimeDate(todoDueDate);
        dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return (null == dueDate) ? "" : dateFormat.format(dueDate);
    }

    public boolean isPastDue(){
        if(null == dueDate){
            return false;
        }
        Date today = getZeroTimeDate(Calendar.getInstance().getTime());
        return today.after(dueDate);
    }

    private Date getZeroTimeDate(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
