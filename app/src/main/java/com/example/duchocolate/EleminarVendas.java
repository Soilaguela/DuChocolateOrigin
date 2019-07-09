package com.example.duchocolate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
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
        TextView et_nome_cliente = (TextView) findViewById(R.id.ID_NCVE);
        TextView spinnerCliente = (TextView) findViewById(R.id.produto);
        TextView editTextDate = (TextView) findViewById(R.id.DATA);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EleminarVendas(v);
            }
        });

        Intent intent = getIntent();

        long idvendas = intent.getLongExtra(MainClientes.ID_CLIENTES, -1);

        if (idvendas == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o Cliente Selecionado", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoVendasApagar = Uri.withAppendedPath(VendasContentProvidar.ENDERECO_VENDAS, String.valueOf(idvendas));

        Cursor cursor = getContentResolver().query(enderecoVendasApagar, BDProduto.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o Cliente", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Vendas vendas = Vendas.fromCursor(cursor);


        et_nome_cliente.setText(String.valueOf(vendas.getDescricaoProdutoV()));
        spinnerCliente.setText(String.valueOf(vendas.getCliente()));
        editTextDate.setText(String.valueOf(vendas.getData()));
    }
        public void cancelarEleninar (View v){
            finish();
        }

        public void EleminarVendas (View v){//Inserir e as suas validaçes
            AlertDialog.Builder box = new AlertDialog.Builder(this);
            box.setTitle("Excluido!");
            box.setIcon(android.R.drawable.ic_menu_delete);
            box.setMessage("Tem certeza que deseja eleminar este item?");
            box.setPositiveButton("sim!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(EleminarVendas.this, "Clicou em Sim", Toast.LENGTH_LONG).show();
                    getContentResolver().delete(enderecoVendasApagar, null, null);

                    Toast.makeText(EleminarVendas.this, getString(R.string.GuardadoSucesso), Toast.LENGTH_LONG).show();
                    finish();
                }


            });
            box.setNegativeButton("não!", null);

            box.show();

        }


}
