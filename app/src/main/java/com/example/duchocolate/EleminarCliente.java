package com.example.duchocolate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class EleminarCliente extends AppCompatActivity {
    private Uri enderecoClienteApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleminar_cliente);

        TextView et_nome_cliente = (TextView) findViewById(R.id.ID_NCVE);
        TextView et_nome_emp = (TextView) findViewById(R.id.ID_EMP);
        TextView et_preco= (TextView) findViewById(R.id.ID_PREÇO);
        TextView et_telefone= (TextView) findViewById(R.id.ID_TELEF);;
        TextView et_email= (TextView) findViewById(R.id.ID_EMAIL);;
        TextView et_data=(TextView) findViewById(R.id.ID_DATA);
        TextView e_produto =(TextView) findViewById(R.id.ID_Produto);

        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EleminarDetalhesVenda(v);
            }
        });

        Intent intent = getIntent();

        long idcliente = intent.getLongExtra(MainClientes.ID_CLIENTES, -1);

        if (idcliente == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o Cliente Selecionado", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoClienteApagar = Uri.withAppendedPath(VendasContentProvidar.ENDERECO_CLIENTE, String.valueOf(idcliente));

        Cursor cursor = getContentResolver().query(enderecoClienteApagar, BDProduto.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o Cliente", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

       Cliente cliente = Cliente.fromCursor(cursor);


        et_nome_cliente.setText(String.valueOf(cliente.getNomeCliente()));
        et_nome_emp.setText(String.valueOf(cliente.getEmpresa()));
        et_preco.setText(String.valueOf(cliente.getPreço()));
        et_telefone.setText(String.valueOf(cliente.getTelefone()));
        et_data.setText(String.valueOf(cliente.getData()));
        et_email.setText(String.valueOf(cliente.getEmail()));
        e_produto.setText(String.valueOf(cliente.getProdutos()));

    }
    public void cancelarEleminarDetalhesVenda(View v) {
        finish();
    }

    public void EleminarDetalhesVenda(View v) {//Inserir e as suas validaçes
        AlertDialog.Builder box= new AlertDialog.Builder(this);
        box.setTitle("Excluido!");
        box.setIcon(android.R.drawable.ic_menu_delete);
        box.setMessage("Tem certeza que deseja eleminar este item?");
        box.setPositiveButton("sim!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EleminarCliente.this, "Clicou em Sim", Toast.LENGTH_LONG).show();
                getContentResolver().delete(enderecoClienteApagar, null, null);

                Toast.makeText(EleminarCliente.this, getString(R.string.GuardadoSucesso), Toast.LENGTH_LONG).show();
                finish();
            }


        });
        box.setNegativeButton("não!", null );

        box.show();

    }
}
