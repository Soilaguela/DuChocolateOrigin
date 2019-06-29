package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EleminarProdutos extends AppCompatActivity {
    private Uri enderecoProdutoApagar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleminar_produtos);

        TextView textViewQuantidade = (TextView) findViewById(R.id.id_eDP);
        TextView textViewdescricaoProduto = (TextView) findViewById(R.id.ID_EQ);


        Intent intent = getIntent();

        long idLivro = intent.getLongExtra(MainProdutos.ID_PRODUTO, -1);

        if (idLivro == -1) {
            Toast.makeText(this, "Erro: não foi possível apagar o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoProdutoApagar = Uri.withAppendedPath(VendasContentProvidar.ENDERECO_PRODUTOS, String.valueOf(idLivro));

        Cursor cursor = getContentResolver().query(enderecoProdutoApagar, BDProduto.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível apagar o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Produtos produtos = Produtos.fromCursor(cursor);

       textViewQuantidade.setText(String.valueOf(produtos.getQuantidade()));
        textViewdescricaoProduto.setText(produtos.getProdutoestoque());
    }
    public void cancelarEleminarProduto (View v){
        finish();
    }

    public void EleminarProdutos (View v){//Inserir e as suas validaçes

        getContentResolver().delete(enderecoProdutoApagar, null, null);
        Toast.makeText(this, "Eleminar eliminado com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }
}
