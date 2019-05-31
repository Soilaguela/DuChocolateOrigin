package com.example.duchocolate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

//import android.content.CursorLoader;

public class MainActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    @Override
    protected void onResume(){
       // getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CLIENTES,null, this);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Toast.makeText(MainActivity.this,"Clicou em Settings", Toast.LENGTH_LONG).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*
     * Chamar o Sengundo Menu clicando no butao VENDAS
     * */
    public void Vendas (View v){
        Intent i=  new Intent( this, Menu_Vendas.class );
        startActivity(i);

    }
    /*
     * Chamar o Sengundo Menu clicando no butao PRODUTOS
     * */
    public void Produtos(View v){
        Intent i = new Intent (this , Menu_Produto.class);
        startActivity(i);
    }

    /*
     * Chamar o Sengundo Menu clicando no butao GASTOS
     * */
    public void  Gastos(View v){
        Intent i= new Intent(this, Menu_Cliente.class );
        startActivity(i);
    }
}


