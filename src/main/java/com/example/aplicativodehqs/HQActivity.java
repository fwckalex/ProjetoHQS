package com.example.aplicativodehqs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class HQActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public ListView listViewDados;
    public Button botao;
    public ArrayList<Integer> arrayIds;
    public Integer idSelecionado;
    public String idColec;

    MainActivity acesso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hqactivity);


        listViewDados = (ListView) findViewById(R.id.listViewDadosHQ);
        botao = (Button) findViewById(R.id.btnCadastrarHQ);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });



        listViewDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                abrirTelaAlterar();
            }
        });


        criarBancoDados1();
        listarDados();
    }



    @Override
    protected void onResume(){
        super.onResume();
        listarDados();
    }


    public void criarBancoDados1(){
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS HQ_new(" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", nome VARCHAR " +
                    ", ano VARCHAR "+
                    ", licenciador VARCHAR "+
                    ", genero VARCHAR "+
                    ", numero VARCHAR" +
                    ", idColecao INTEGER," +
                    "FOREIGN KEY (idColecao) REFERENCES colecao(id))");
            bancoDados.close();
        }   catch (Exception e){
            e.printStackTrace();
        }

    }


    public void listarDados(){
        try {
            arrayIds = new ArrayList<>();
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome, numero FROM HQ_new JOIN colecao ON HQ_new.idColecao = colecao.id WHERE colecao.id = idSelecionado", null );
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            listViewDados.setAdapter(meuAdapter);
            meuCursor.moveToFirst();
            while (meuCursor != null){
                linhas.add(meuCursor.getString(1));
                arrayIds.add(meuCursor.getInt(0));
                meuCursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void abrirTelaCadastro(){
        Intent intent = new Intent(this,CadastroHQActivity.class);
        startActivity(intent);
    }




    public void abrirTelaAlterar(){
        Intent intent = new Intent (this, AlterarHQActivity.class);
        startActivity(intent);
    }
}