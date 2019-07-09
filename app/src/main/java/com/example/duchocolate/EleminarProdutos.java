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

public class EleminarProdutos extends AppCompatActivity {
    private Uri enderecoProdutoApagar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleminar_produtos);

        TextView textViewQuantidade = (TextView) findViewById(R.id.id_eDP);
        TextView textViewdescricaoProduto = (TextView) findViewById(R.id.ID_EQ);

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EleminarProdutos(v);
            }
        });

        Intent intent = getIntent();

        long idProduto = intent.getLongExtra(MainProdutos.ID_PRODUTO, -1);

        if (idProduto == -1) {
            Toast.makeText(this, "Erro: não foi possível apagar o Produto", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoProdutoApagar = Uri.withAppendedPath(VendasContentProvidar.ENDERECO_PRODUTOS, String.valueOf(idProduto));

        Cursor cursor = getContentResolver().query(enderecoProdutoApagar, BDProduto.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível apagar o Produto", Toast.LENGTH_LONG).show();
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


        AlertDialog.Builder box= new AlertDialog.Builder(this);
        box.setTitle("Excluido!");
        box.setIcon(android.R.drawable.ic_menu_delete);
        box.setMessage("Tem certeza que deseja eleminar este item?");
        box.setPositiveButton("sim!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EleminarProdutos.this, "Clicou em Sim", Toast.LENGTH_LONG).show();
                getContentResolver().delete(enderecoProdutoApagar, null, null);

                Toast.makeText(EleminarProdutos.this, getString(R.string.GuardadoSucesso), Toast.LENGTH_LONG).show();
                finish();
            }


        });
        box.setNegativeButton("não!", null );

      /*  box.setNegativeButton("não!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EleminarProdutos.this,"Clicou em Não",Toast.LENGTH_LONG).show();


            }
        });
        box.show();
*/
        box.show();

    }
}
