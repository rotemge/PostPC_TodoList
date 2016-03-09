package il.ac.huji.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToDoListManagerActivity extends AppCompatActivity {

    private List<String> todos;
    private MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_manager);
        ListView list = (ListView) findViewById(R.id.lstTodoItems);
        todos = new ArrayList<>();
        adapter = new MyListAdapter(getApplicationContext(), R.layout.todo_item, R.id.todo_text, todos);
        list.setAdapter(adapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ToDoListManagerActivity.this);
                alertBuilder.setCancelable(true).setPositiveButton("Delete item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        todos.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.setTitle(todos.get(position));
                alert.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_todo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(R.id.menuItemAdd == item.getItemId()){
            EditText editText = (EditText) findViewById(R.id.edtNewItem);
            todos.add(editText.getText().toString());
            editText.setText("");
            adapter.notifyDataSetChanged();
        }
        return true;
    }

    private class MyListAdapter extends ArrayAdapter<String>{
        private int resource;
        private int textViewId;
        private Context context;
        List<String> list;

        public MyListAdapter(Context context, int resource, int textViewResourceId, List<String> objects){
            super(context, resource, textViewResourceId, objects);
            this.context = context;
            this.resource = resource;
            textViewId = textViewResourceId;
            list = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = super.getView(position, convertView, parent);
            if (null == itemView){
                itemView = getLayoutInflater().inflate(resource, parent, false);
            }
            TextView textView = (TextView) itemView.findViewById(textViewId);
            textView.setText(list.get(position));
            textView.setTextColor((position % 2 == 0) ?
                    ContextCompat.getColor(context, R.color.colorPrimaryDark) :
                    ContextCompat.getColor(context, R.color.colorAccent));
            return itemView;
        }
    }
}
