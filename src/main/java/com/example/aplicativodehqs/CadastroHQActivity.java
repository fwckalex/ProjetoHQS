package com.example.aplicativodehqs;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CadastroHQActivity extends AppCompatActivity {
    EditText edtNome;
    EditText edtAno;
    EditText edtLicenciador;
    EditText edtGenero;
    EditText edtNumero;
    Button botao;
    Integer idSelecionado;
    SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_hqactivity);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtAno = (EditText) findViewById(R.id.edtAno);
        edtLicenciador = (EditText) findViewById(R.id.edtLicenciador);
        edtGenero = (EditText) findViewById(R.id.edtGenero);
        edtNumero = (EditText) findViewById(R.id.edtNumero);
        botao = (Button) findViewById(R.id.btnAlterar);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }


    public void cadastrar() {
        if (!TextUtils.isEmpty(edtNome.getText().toString())) {
            try {
                bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
                String sql  = "INSERT INTO HQ_new (nome, ano, licenciador, genero, numero, idColecao) VALUES (?, ?, ?, ?, ?, 12)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, edtNome.getText().toString());
                stmt.bindString(2, edtAno.getText().toString());
                stmt.bindString(3, edtLicenciador.getText().toString());
                stmt.bindString(4, edtGenero.getText().toString());
                stmt.bindString(5, edtNumero.getText().toString());
                stmt.executeInsert();

                bancoDados.close();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}