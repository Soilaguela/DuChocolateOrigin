package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Menu_Vendas extends AppCompatActivity {
    private AdaptadorVendas adaptadorVendas;
    public  static  String ID_VENDAS="ID_VENDAS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__vendas);
    }
    public void InseirVendas (View v){
        Toast.makeText(this, "Inserir", Toast.LENGTH_SHORT).show();
        Intent i =  new Intent( this,InserirVendas.class );
        startActivity(i);
    }


    public void AlterarVendas (View v){
        Toast.makeText(this, "Alterar", Toast.LENGTH_SHORT).show();
        Intent i =  new Intent( this,AlterarVendas.class );
      //  i.putExtra(ID_VENDAS, adaptadorVendas.getVendaSelecionado().getId());
        startActivity(i);
    }

    public void EleminarVendas (View v){
        Toast.makeText(this, "Eleminar", Toast.LENGTH_SHORT).show();
        Intent i =  new Intent( this,EleminarVendas.class );
      //  i.putExtra(ID_VENDAS, adaptadorVendas.getVendaSelecionado().getId());
        startActivity(i);
    }
}
