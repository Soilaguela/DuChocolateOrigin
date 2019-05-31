package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu_Vendas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__vendas);
    }
    public void InseirVendas (View v){
        Intent i =  new Intent( this,InserirVendas.class );
        startActivity(i);
    }


    public void AlterarVendas (View v){
        Intent i =  new Intent( this,AlterarVendas.class );
        startActivity(i);
    }

    public void EleminarVendas (View v){
        Intent i =  new Intent( this,EleminarVendas.class );
        startActivity(i);
    }
}
