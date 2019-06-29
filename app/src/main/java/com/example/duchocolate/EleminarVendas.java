package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class EleminarVendas extends AppCompatActivity {
    private Uri enderecoVendasApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleminar_vendas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        TextView textViewDescricaoProduto = (TextView) findViewById(R.id.textViewDescricaoProduto);
        TextView textViewDAta = (TextView) findViewById(R.id.textViewDAta);
        TextView textViewCliente = (TextView) findViewById(R.id.textViewCliente);


        Intent intent= getIntent();


        long idVendas = intent.getLongExtra(MainVendas.ID_VENDAS, -1);

        if (idVendas == -1) {
            Toast.makeText(this, "Erro: não foi possível eleminar a venda", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoVendasApagar = Uri.withAppendedPath(VendasContentProvidar.ENDERECO_VENDAS, String.valueOf(idVendas));

        Cursor cursor = getContentResolver().query(enderecoVendasApagar, BDVendas.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível eleminar a venda", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Vendas vendas = Vendas.fromCursor(cursor);


       textViewDAta.setText(vendas.getData());
       textViewCliente.setText(String.valueOf(vendas.getCliente()));
       textViewDescricaoProduto.setText(vendas.getDescricaoProdutoV());

    }

    public void cancelarEleninar (View v){
        finish();
    }


    public void EleminarVendas (View v) {//Inserir e as suas validaçes

        getContentResolver().delete(enderecoVendasApagar, null, null);
        Toast.makeText(this, "Venda eliminada com sucesso", Toast.LENGTH_LONG).show();
        finish();


    }

}
