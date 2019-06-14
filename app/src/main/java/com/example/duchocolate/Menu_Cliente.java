package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu_Cliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__cliente);
    }
    /*
     * Chamar o Inserir , Aletrar e Eleminar Menu clicando no butao PRODUTOS
     * */
    public void InserirClientes (View v){
        Intent i =  new Intent( this,InserirCliente.class );
        startActivity(i);
    }


    public void AlterarClientes (View v){
        Intent i =  new Intent( this,AlterarCliente.class );
        startActivity(i);
    }

    public void EleminarClientes (View v){
        Intent i =  new Intent( this,EleminarCliente.class );
        startActivity(i);
    }
}
