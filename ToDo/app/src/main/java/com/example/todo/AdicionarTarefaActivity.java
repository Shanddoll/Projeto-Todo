package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    EditText inputTitulo;
    EditText inputDescricao;
    Button buttonAddTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );


        inputTitulo = findViewById(R.id.inputTituloTarefa);
        inputDescricao = findViewById(R.id.inputDescTarefa);
        buttonAddTarefa = findViewById(R.id.buttonAdicionarTarefa);
        
    }
}