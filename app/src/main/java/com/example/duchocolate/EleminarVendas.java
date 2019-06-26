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
            eliminar();
            return true;
        } else if (id == R.id.action_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void cancelarEleninar (View v){
        finish();
    }
    private void eliminar() {
        // todo: perguntar ao utilizador se tem a certeza

        getContentResolver().delete(enderecoVendasApagar, null, null);
        Toast.makeText(this, "Vendas eliminado com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }

    public void EleminarVendas (View v) {//Inserir e as suas validaçes

        getContentResolver().delete(enderecoVendasApagar, null, null);
        Toast.makeText(this, "Venda eliminada com sucesso", Toast.LENGTH_LONG).show();
        finish();

       /* int erasedProfile = getContentResolver().delete(enderecoVendasApagar, null, null);
        if (erasedProfile == 1) {
            Toast.makeText(this, "Profile deleted with success!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Error: It was not possible to delete the profile!", Toast.LENGTH_LONG).show();
        }*/
    }

}
