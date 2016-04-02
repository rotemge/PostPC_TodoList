package il.ac.huji.todolist;

import java.util.Date;

public class TodoItem {
    private String title;
    private Date dueDate;

    TodoItem(String todoTitle, Date todoDueDate){
        title = todoTitle;
        dueDate = (null == todoDueDate) ? null : DateHelper.getZeroTimeDate(todoDueDate);
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return DateHelper.formatDate(dueDate);
    }

    public boolean isPastDue(){
        return DateHelper.isPast(dueDate);
    }
}
