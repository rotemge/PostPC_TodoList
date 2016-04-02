package il.ac.huji.todolist;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoListAdapter extends ArrayAdapter<TodoItem> {
    private int resource;
    private int titleViewId;
    private int dateViewId;
    private Context context;
    List<TodoItem> list;

    public TodoListAdapter(Context context, int resource, int titleViewResourceId,
                           int dateViewResourceId, List<TodoItem> objects){
        super(context, resource, titleViewResourceId, objects);
        this.context = context;
        this.resource = resource;
        titleViewId = titleViewResourceId;
        dateViewId = dateViewResourceId;
        list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = super.getView(position, convertView, parent);
        if (null == itemView){
            itemView = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        TextView titleView = (TextView) itemView.findViewById(titleViewId);
        TextView dateView = (TextView) itemView.findViewById(dateViewId);

        TodoItem item = list.get(position);
        String due = item.getDueDate();
        if (due.equals("")){
            due = context.getString(R.string.no_due);
        }

        dateView.setText(due);
        titleView.setText(item.getTitle());

        if(item.isPastDue()){
            titleView.setTextColor(Color.RED);
            dateView.setTextColor(Color.RED);
        }else{
            titleView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            dateView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }
        return itemView;
    }
}
