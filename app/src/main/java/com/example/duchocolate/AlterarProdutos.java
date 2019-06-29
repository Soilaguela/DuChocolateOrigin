package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AlterarProdutos extends AppCompatActivity {
    private static final int ID_CURSO_LOADER_PRODUTOS = 0;

    private boolean categoriasCarregadas = false;
    private boolean categoriaAtualizada = false;

    private Uri enderecoProdutoEditar;

    private Produtos produtos = null;

    private TextView textViewQuantidade;
    private TextView textViewProdutoestoque;


    EditText editProfileAge;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_produtos);

        Intent intent = getIntent();

        long idLivro = intent.getLongExtra(MainProdutos.ID_PRODUTO, -1);

        if (idLivro == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoProdutoEditar = Uri.withAppendedPath(VendasContentProvidar.ENDERECO_PRODUTOS, String.valueOf(idLivro));

        Cursor cursor = getContentResolver().query(enderecoProdutoEditar, BDProduto.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        produtos = Produtos.fromCursor(cursor);


        textViewQuantidade.setText(String.valueOf(produtos.getQuantidade()));
        textViewProdutoestoque.setText(produtos.getProdutoestoque());

    }

    public void cancelarAlteracaoProduto(View v) {
        finish();
    }

    public void AlterarProduto(View V) {
        EditText id_ADP = (EditText) findViewById(R.id.id_ADP);//Nome cliente
        String NP = id_ADP.getText().toString();
        if (NP.trim().length() == 0) {//nome cliente
            id_ADP.setError(getString(R.string.erro_ID_prodotosvendidos));
            id_ADP.requestFocus();
            return;
        }
        /////////////////////////////////////////

        EditText ID_AQ = (EditText) findViewById(R.id.ID_AQ);//valor

        double valor = 0;//validação valor para maior k 0

        try {
            valor = Double.parseDouble(ID_AQ.getText().toString());
        } catch (NumberFormatException e) {
            ID_AQ.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_AQ.requestFocus();
            return;
        }

        if (valor == 0) {
            ID_AQ.setError(getString(R.string.erro_0));
            ID_AQ.requestFocus();
            return;
        }
        try {
            getContentResolver().update(enderecoProdutoEditar, produtos.getContentValues(), null, null);

            Toast.makeText(this, getString(R.string.AlterarSucesso), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    ID_AQ,
                    getString(R.string.erro_guardar_produto),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }
}