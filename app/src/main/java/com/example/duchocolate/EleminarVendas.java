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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView ID_nomecliente = (EditText) findViewById(R.id.ID_nomecliente);
        //TextView Cliente = (Spinner) findViewById(R.id.spinnerCliente);
        TextView ID_datavenda = (EditText) findViewById(R.id.ID_datavenda);

        Intent intent= getIntent();


        long idVendas = intent.getLongExtra(MainVendas.ID_VENDAS, -1);

        if (idVendas == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoVendasApagar = Uri.withAppendedPath(VendasContentProvidar.ENDERECO_VENDAS, String.valueOf(idVendas));

        Cursor cursor = getContentResolver().query(enderecoVendasApagar, BDVendas.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Vendas vendas = Vendas.fromCursor(cursor);


       /* textViewTitulo.setText(livro.getTitulo());
        textViewCategoria.setText(livro.getNomeCategoria());
        textViewPagina.setText(String.valueOf(livro.getPagina()));*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eliminar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_eliminar) {
            EleminarVendas();
            return true;
        } else if (id == R.id.action_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /////////Para o Menus/////////////////////////////////
    private void EleminarVendas() {
        // todo: perguntar ao utilizador se tem a certeza

        getContentResolver().delete(enderecoVendasApagar, null, null);
        Toast.makeText(this, "Vendas eliminado com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }


///////////////////////////////Para os Botoes /////////////////////////
    public void cancelarEleninar (View v){
        finish();
    }

    public void EleminarVendas (View v) {//Inserir e as suas validaçes

        TextView ID_DP = (TextView) findViewById(R.id.ID_DP);
        String dp = ID_DP.getText().toString();

        if (dp.trim().length() == 0) {
            ID_DP.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_DP.requestFocus();
            return;
        }
        //////////////////////////////////////////

        TextView ID_NC = (TextView) findViewById(R.id.ID_nomecliente);//nome cliente
        String NC = ID_NC.getText().toString();

        if (NC.trim().length() == 0) {
            ID_NC.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_NC.requestFocus();
            return;
        }


        ////////
        TextView produto = (TextView) findViewById(R.id.produto);
        String produt = ID_DP.getText().toString();

        if (produt.trim().length() == 0) {
            ID_DP.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_DP.requestFocus();
            return;
        }

        ////
        TextView DATA = (TextView) findViewById(R.id.DATA);
        String data = ID_DP.getText().toString();

        if (data.trim().length() == 0) {
            ID_DP.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_DP.requestFocus();
            return;
        }

        Toast.makeText(EleminarVendas.this, getString(R.string.EleminarSucesso), Toast.LENGTH_LONG).show();
        finish();
    }
}
