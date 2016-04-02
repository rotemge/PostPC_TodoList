package il.ac.huji.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class TodoSqlHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "todo_db";
    public static final String TABLE_NAME = "todo";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_DUE = "due";
    public static final String[] ALL_COLS_NO_ID = {COL_TITLE, COL_DUE};
    public static final String[] ALL_COLS = {COL_ID, COL_TITLE, COL_DUE};

    public TodoSqlHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (" +
                COL_ID + " integer primary key autoincrement, " +
                COL_TITLE + " text, " + COL_DUE + " datetime" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertTodo(String title, Date date){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentVal = new ContentValues();
        contentVal.put(COL_TITLE, title);
        contentVal.put(COL_DUE, DateHelper.formatDate(date));
        long res = db.insert(TABLE_NAME, null, contentVal);
        return (res != -1);
    }

    public boolean deleteTodo(long todoId){
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {""+todoId};
        int res = db.delete(TABLE_NAME, COL_ID+"==?", args);
        return (res != 0);
    }

    public Cursor getTodoList(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, ALL_COLS, null, null, null, null, null);
    }
}
