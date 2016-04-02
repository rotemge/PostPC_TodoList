package il.ac.huji.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Date;

public class ToDoListManagerActivity extends AppCompatActivity {

    static final int NEW_ITEM_REQUEST = 1;

    private SimpleCursorAdapter adapter;
    private TodoSqlHelper todoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_manager);

        ListView list = (ListView) findViewById(R.id.lstTodoItems);
        registerForContextMenu(list);
        todoDB = new TodoSqlHelper(this);
        int[] views = {R.id.txtTodoTitle, R.id.txtTodoDueDate};
        adapter = new SimpleCursorAdapter(this, R.layout.todo_item,
                todoDB.getTodoList(), TodoSqlHelper.ALL_COLS_NO_ID, views, 0);
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                TextView textView = (TextView) view;
                int dateColIndex = cursor.getColumnIndex(TodoSqlHelper.COL_DUE);
                String date = cursor.getString(dateColIndex);
                if(DateHelper.isPast(date)){
                    textView.setTextColor(Color.RED);
                }else{
                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.colorPrimaryDark));
                }
                return false;
            }
        });
        list.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_todo_item_context, menu);
        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int titleColIndex = adapter.getCursor().getColumnIndex(TodoSqlHelper.COL_TITLE);
        String itemTitle = adapter.getCursor().getString(titleColIndex);
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
                if(todoDB.deleteTodo(info.id)){
                    adapter.changeCursor(todoDB.getTodoList());
                }
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
                if(todoDB.insertTodo(title, date)){
                    adapter.changeCursor(todoDB.getTodoList());
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        todoDB.close();
    }
}
