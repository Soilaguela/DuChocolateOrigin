package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Menu_Cliente extends AppCompatActivity {
    public static final String ID_CLIENTE = "ID_CLIENTE";
    private  AdaptadorClientes adaptadorClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__cliente);
    }

    public void InserirClientes (View v){
        Toast.makeText(this, "Inserir", Toast.LENGTH_SHORT).show();
        Intent i =  new Intent( this,InserirCliente.class );
        startActivity(i);
    }

    public void AlterarClientes (View v){
        Toast.makeText(this, "Aletrar", Toast.LENGTH_SHORT).show();
        Intent i =  new Intent( this,MainClientes.class );
        startActivity(i);
    }

    public void EleminarClientes (View v){
        Toast.makeText(this, "Eleminar", Toast.LENGTH_SHORT).show();
        Intent i =  new Intent( this,EleminarCliente.class );
      //  i.putExtra(ID_CLIENTE, adaptadorClientes.getClientSelecionado().getId());
        startActivity(i);
    }
}
