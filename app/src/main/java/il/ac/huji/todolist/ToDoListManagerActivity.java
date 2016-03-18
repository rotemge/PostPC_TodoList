package il.ac.huji.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToDoListManagerActivity extends AppCompatActivity {

    static final int NEW_ITEM_REQUEST = 1;

    private List<TodoItem> todos;
    private TodoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_manager);
        ListView list = (ListView) findViewById(R.id.lstTodoItems);
        todos = new ArrayList<>();
        adapter = new TodoListAdapter(getApplicationContext(),
                R.layout.todo_item, R.id.txtTodoTitle, R.id.txtTodoDueDate, todos);
        list.setAdapter(adapter);
        registerForContextMenu(list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_todo_item_context, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String itemTitle = todos.get(info.position).getTitle();
        menu.setHeaderTitle(itemTitle);
        int index = itemTitle.toLowerCase().indexOf("call ");
        if(index == -1){
            menu.removeItem(R.id.menuItemCall);
        }else{
            menu.findItem(R.id.menuItemCall).setTitle(itemTitle);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menuItemDelete:
                todos.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menuItemCall:
                String phoneNum = item.getTitle().toString().substring(5);
                Uri number = Uri.parse("tel:"+phoneNum);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
            Intent addItemIntent = new Intent(this, AddNewTodoItemActivity.class);
            startActivityForResult(addItemIntent, NEW_ITEM_REQUEST);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (NEW_ITEM_REQUEST == requestCode){
            if(RESULT_OK == resultCode){
                Date date = (Date) data.getSerializableExtra(AddNewTodoItemActivity.EXTRA_DATE);
                String title = data.getStringExtra(AddNewTodoItemActivity.EXTRA_TITLE);
                TodoItem newItem = new TodoItem(title, date);
                todos.add(newItem);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
