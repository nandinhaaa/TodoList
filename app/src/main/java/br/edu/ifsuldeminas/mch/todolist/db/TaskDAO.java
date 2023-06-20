package br.edu.ifsuldeminas.mch.todolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.mch.todolist.domain.Task;

public class TaskDAO extends DAO {

    public TaskDAO(Context context){
        super(context);
    }

    public boolean save (Task task){
        SQLiteDatabase db = openToWrite();

        ContentValues values = new ContentValues();
        // Colocar no padrão chave e valor (valor dos atributos que a gente quer salvar)
        values.put("description", task.getDescription());

        String active = task.getActive()? "1" : "0";
        values.put("active", task.getActive());

        db.insert("task", null, values);

        db.close();

        return true;

    }

    public void update (Task task){
        SQLiteDatabase db = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("description", task.getDescription());
        contentValues.put("active", task.getActive() ? "1" : "0");

        String [] params = {task.getId().toString()}; //vetor com uma unica posicao com ID "WHERE ID"
        db.update("tasks", contentValues,"id = ?", params);
        db.close();
    }

    public  void delete (Task task){
    SQLiteDatabase db = openToWrite();

    String[] params = {task.getId().toString()};

    db.delete("tasks", "id = ?", params);
    db.close();
    }

    public List<Task> listAll(){
        SQLiteDatabase db = openToRead();
        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM tasks;";

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String activeStr = cursor.getString(cursor.getColumnIndexOrThrow("active"));
            Boolean active =activeStr.equals("1")? true : false;

            Task task = new Task(id, desc, active);
            tasks.add(task);

        }
        cursor.moveToNext();
        db.close();
        return tasks;
    }

}