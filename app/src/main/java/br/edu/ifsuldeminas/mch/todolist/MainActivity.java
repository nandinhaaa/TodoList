package br.edu.ifsuldeminas.mch.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.edu.ifsuldeminas.mch.todolist.FormActivity;
import br.edu.ifsuldeminas.mch.todolist.R;
import br.edu.ifsuldeminas.mch.todolist.db.TaskDAO;
import br.edu.ifsuldeminas.mch.todolist.domain.Task;

public class MainActivity extends AppCompatActivity {

    private ListView todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);

                startActivity(intent);
            }
        });

        todoList = findViewById(R.id.todo_list);
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //faz a acao que a gente quer trabalhar

                Task task = (Task) todoList.getItemAtPosition(position);
                //clik vai abrir essta intent
                Intent intent = new Intent(MainActivity.this, FormActivity.class);

                intent.putExtra("Task-to-edit", task); // implementou Serializable na TASK
                startActivity(intent);
            }
        });

        registerForContextMenu(todoList);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem menuItemRemover = menu.add("Remover");
        MenuItem menuItemAtivar = menu.add("Ativar/Desativar");

        menuItemRemover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo adapterView = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Task task = (Task) todoList.getItemAtPosition(adapterView.position);
                TaskDAO dao = new TaskDAO(MainActivity.this);
                dao.delete(task);

                Toast.makeText(MainActivity.this, "Tarefa excluída com sucesso!", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        menuItemRemover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo adapterView = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Task task = (Task) todoList.getItemAtPosition(adapterView.position);
                task.setActive(false);
                TaskDAO dao = new TaskDAO(MainActivity.this);
                dao.update(task);

                Toast.makeText(MainActivity.this, "Tarefa desativada com sucesso!", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

    }

    // Método do ciclo de vida, "roda toda vez"
    @Override
    protected void onResume(){
        super.onResume();
        // Carregar
        loadTasks();
    }

    private void loadTasks(){
        TaskDAO dao = new TaskDAO(MainActivity.this);
        List<Task> tasks = dao.listAll();
        // Adapter recebe a fonte de dados e o visual que ele vai utilizar essa fonte de dados.
        ArrayAdapter<Task> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,tasks);

        todoList.setAdapter(adapter);
    }
}