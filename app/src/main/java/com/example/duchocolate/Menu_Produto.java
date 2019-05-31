package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu_Produto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__produto);
    }
    public void InserirProduto (View v){
        Intent i =  new Intent( this,InserirProdutos.class );
        startActivity(i);
    }


    public void AlterarProdutos (View v){
        Intent i =  new Intent( this,AlterarProdutos.class );
        startActivity(i);
    }

    public void EleminarProduto (View v){
        Intent i =  new Intent( this,EleminarProdutos.class );
        startActivity(i);
    }
}
