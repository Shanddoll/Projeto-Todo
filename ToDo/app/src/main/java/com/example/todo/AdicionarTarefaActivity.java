package com.example.todo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    EditText inputTitulo;
    EditText inputDescricao;
    Button buttonAddTarefa;
    String titulo;
    String descricao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_adicionar_tarefa);


        inputTitulo = findViewById(R.id.inputTituloTarefa);
        inputDescricao = findViewById(R.id.inputDescTarefa);
        buttonAddTarefa = findViewById(R.id.buttonAdicionarTarefa);


        buttonAddTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telaMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                setInputValues();
                inserirTarefa(titulo, descricao);

                startActivity(telaMainActivity);
            }
        });

    }

    public void setInputValues () {
        // Método que adiciona um valor padrão caso os inputs não sejam preenchidos:
        if (inputTitulo.getText().toString().equals("")) {
            titulo = " - ";
        } else {
            titulo = inputTitulo.getText().toString();
        }

        if (inputDescricao.getText().toString().equals("")) {
            descricao = " - ";
        } else {
            descricao = inputDescricao.getText().toString();
        }
    }

    // MÉTODOS DO BANCO:
    public void criarIniciarBanco() {
        db = openOrCreateDatabase("tododb", MODE_PRIVATE, null);
    }

    public void criarTabelaTarefas() {
        db.execSQL("CREATE TABLE IF NOT EXISTS tarefas (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, descricao TEXT);");
    }

    public void inserirTarefa(String titulo, String descricao) {
        criarIniciarBanco();
        db.execSQL("INSERT INTO tarefas (titulo, descricao) VALUES ('"+ titulo +"','"+descricao+"')");
        fecharBanco();
    }


    private void removerTabela() {
        db.execSQL("DROP TABLE IF EXISTS tarefas;");
    }

    public void fecharBanco() {
        db.close();
    }
}