package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Menu_Produto extends AppCompatActivity {
    private AdaptadorProdutos adaptadorProdutos;
    public static final String ID_PRODUTOS = "ID_PRODUTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__produto);
    }
    public void InserirProduto (View v){
        Toast.makeText(this, "Inserir", Toast.LENGTH_SHORT).show();
        Intent i =  new Intent( this,InserirProdutos.class );
        startActivity(i);
    }


    public void AlterarProdutos (View v){
        Toast.makeText(this, "Alterar", Toast.LENGTH_SHORT).show();
        Intent i =  new Intent( this,AlterarProdutos.class );
        i.putExtra(ID_PRODUTOS, adaptadorProdutos.getProdutoSelecionado().getId());

        startActivity(i);
    }

    public void EleminarProduto (View v){
        Toast.makeText(this, "Eleminar", Toast.LENGTH_SHORT).show();
        Intent i =  new Intent( this,EleminarProdutos.class );
        i.putExtra(ID_PRODUTOS, adaptadorProdutos.getProdutoSelecionado().getId());
        startActivity(i);
    }
}
