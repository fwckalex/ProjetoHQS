package com.example.aplicativodehqs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AlterarHQActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public Button buttonAlterar;
    public Button btnExcluir;
    public EditText edtNome;
    public EditText edtAno;
    public EditText edtLicenciador;
    public EditText edtGenero;
    public EditText edtNumero;
    Button botao;
    public Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_hqactivity);

        buttonAlterar = (Button) findViewById(R.id.btnAlterar);
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtAno = (EditText) findViewById(R.id.edtAno);
        edtLicenciador = (EditText) findViewById(R.id.edtLicenciador);
        edtGenero = (EditText) findViewById(R.id.edtGenero);
        edtNumero = (EditText) findViewById(R.id.edtNumero);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);


        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        carregarDados();

        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterar();
            }
        });
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluir();
            }
        });

    }

    public void carregarDados(){
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id, nome, ano, licenciador, genero, numero FROM HQ WHERE id = " +id.toString(), null);
            cursor.moveToFirst();
            edtNome.setText(cursor.getString(1));
            edtAno.setText(cursor.getString(2));
            edtLicenciador.setText(cursor.getString(3));
            edtGenero.setText(cursor.getString(4));
            edtNumero.setText(cursor.getString(5));

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void alterar(){
        String valueNome;
        String valueAno;
        String valueLicenciador;
        String valueGenero;
        String valueNumero;

        valueNome = edtNome.getText().toString();
        valueAno = edtAno.getText().toString();
        valueLicenciador = edtLicenciador.getText().toString();
        valueGenero = edtGenero.getText().toString();
        valueNumero = edtNumero.getText().toString();

        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            String sql = "UPDATE HQ SET nome=?, ano=?, licenciador=?, genero=?, numero=? WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1, valueNome);
            stmt.bindString(2, valueAno);
            stmt.bindString(3, valueLicenciador);
            stmt.bindString(4, valueGenero);
            stmt.bindString(5, valueNumero);
            stmt.bindLong(6, id);
            stmt.executeUpdateDelete();
            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        finish();
    }
    public void excluir(){
        try {
            bancoDados = openOrCreateDatabase("crudapp", MODE_PRIVATE, null);
            String sql = "DELETE FROM HQ WHERE id = ?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindLong(1, id);
            stmt.executeUpdateDelete();
            // listarDados();
            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        finish();
    }
}