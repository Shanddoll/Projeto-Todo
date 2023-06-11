package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    Cursor cursor;
    Button buttonMenuAddTarefa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_main);


        buttonMenuAddTarefa = findViewById(R.id.buttonMenuAdicionar);


        criarIniciarBanco();
        criarTabelaTarefas();
        ArrayList<List<String>> listaDadosConsulta = getValoresBanco();
        fecharBanco();
        popularScrollView(listaDadosConsulta);
    }

    public void irParaAdicionarTarefa (View view) {
        Intent telaAdicionarTarefa = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
        startActivity(telaAdicionarTarefa);
    }


    // MÉTODOS DO BANCO:
    public void criarIniciarBanco() {
        db = openOrCreateDatabase("tododb", MODE_PRIVATE, null);
    }

    public void criarTabelaTarefas() {
        db.execSQL("CREATE TABLE IF NOT EXISTS tarefas (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, descricao TEXT);");
    }

    @SuppressLint("Range")
    public ArrayList<List<String>> getValoresBanco() {
        // Método que percorre a consulta sql e retorna um ARRAY CONTENDO LISTAS com id, título e descrição de cada registro do db.
        // Ex.: retorno: [[id1, titulo1, descricao1], [id2, titulo2, descricao2], [id3, titulo3, descricao3]]
        criarIniciarBanco();
        ArrayList<List<String>> listaDeDados = new ArrayList<>();
        cursor = db.rawQuery("SELECT * FROM tarefas;", null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            listaDeDados.add(Arrays.asList(
                    cursor.getString(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("titulo")),
                    cursor.getString(cursor.getColumnIndex("descricao"))
            ));
            //listaDeDados.add(cursor.getString(cursor.getColumnIndex("id")));
            //listaDeDados.add(cursor.getString(cursor.getColumnIndex("titulo")));
            //listaDeDados.add(cursor.getString(cursor.getColumnIndex("descricao")));
            cursor.moveToNext();
        }
        return listaDeDados;
    }

    public void fecharBanco() {
        db.close();
    }

    public void removerCardById(int id) {
        criarIniciarBanco();
        db.execSQL("DELETE FROM tarefas WHERE id ='" + id + "';");
        fecharBanco();
    }

    public void popularScrollView(ArrayList<List<String>> listaDados) {
        // Método que percorre o Array criado pelo método getValoresBanco() [LINHA 67]


        LayoutInflater l = getLayoutInflater();
        LinearLayout scrollViewLinearlayout = findViewById(R.id.scrollViewLinearLayout);
        TextView titulo;
        TextView descricao;
        Button botaoRemover;
        List<String> registro;

        if (listaDados.size() > 0) {
            for (int i = 0; i < listaDados.size(); i++) {

                // Percorre a lista (contendo id, titulo e descrição) do ArrayList(listaDados)
                registro = listaDados.get(i);
                // Inflate transforma um layout xml em um ViewGroup:
                ViewGroup cardTarefa = (ViewGroup) l.inflate(R.layout.layout_cardtarefa, null);
                // GetChildAt() retorna um objeto filho por index (ordem no layout):
                descricao = (TextView) cardTarefa.getChildAt(0);
                titulo = (TextView) cardTarefa.getChildAt(1);
                botaoRemover = (Button) cardTarefa.getChildAt(2);

                // Adiciona os dados das listas  no layout predefinido cardTarefa [LINHA 116]:
                int id = Integer.parseInt(registro.get(0));
                titulo.setText(registro.get(1));
                descricao.setText(registro.get(2));


                botaoRemover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removerCardById(id);
                        reloadActivity();
                    }
                });

                scrollViewLinearlayout.addView(cardTarefa);
            }
        }
    }

    public void reloadActivity () {
        // Recarrega a Activity sem piscar a tela
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }



}