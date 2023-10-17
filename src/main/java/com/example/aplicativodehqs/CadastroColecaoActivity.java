package com.example.aplicativodehqs;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroColecaoActivity extends AppCompatActivity {
    EditText edtNome;
    EditText edtAutor;
    EditText edtGenero;
    Button btnSalvar;
    SQLiteDatabase bancoDados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_colecao);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtAutor = (EditText) findViewById(R.id.edtAutor);
        edtGenero = (EditText) findViewById(R.id.edtGenero);
        btnSalvar = (Button) findViewById(R.id.btnAlterar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
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
                String sql = "INSERT INTO colecao (nome, autor, genero) VALUES (?,?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, edtNome.getText().toString());
                stmt.bindString(2, edtAutor.getText().toString());
                stmt.bindString(3, edtGenero.getText().toString());
                stmt.executeInsert();

                bancoDados.close();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}