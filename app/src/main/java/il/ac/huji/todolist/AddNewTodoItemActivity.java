package il.ac.huji.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class AddNewTodoItemActivity extends Activity {

    public static final String EXTRA_TITLE = "title"; //"il.ac.huji.todolist.TITLE";
    public static final String EXTRA_DATE = "dueDate"; //"il.ac.huji.todolist.DUE_DATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent intent = getIntent();
        setContentView(R.layout.activity_add_new_todo_item);
        Button cancelBtn = (Button) findViewById(R.id.btnCancel);
        Button okBtn = (Button) findViewById(R.id.btnOK);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(RESULT_CANCELED, resultIntent);
                finish();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                EditText titleTxt = (EditText) findViewById(R.id.edtNewItem);
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
                resultIntent.putExtra(EXTRA_TITLE, titleTxt.getText().toString());
                resultIntent.putExtra(EXTRA_DATE, getDateFromDatePicker(datePicker));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    public static Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
