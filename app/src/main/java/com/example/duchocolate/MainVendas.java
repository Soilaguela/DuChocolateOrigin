package com.example.duchocolate;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainVendas extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_VENDAS = 0;
    public  static  String ID_VENDAS="ID_VENDAS";

    private RecyclerView recyclerViewVendas;
    private AdaptadorVendas adaptadorVendas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vendas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_VENDAS,null, this);

        recyclerViewVendas = (RecyclerView) findViewById(R.id.recyclerViewVendas);
        adaptadorVendas = new AdaptadorVendas(this);
        recyclerViewVendas.setAdapter(adaptadorVendas);
        recyclerViewVendas.setLayoutManager(new LinearLayoutManager(this));
    }
    protected void onResume(){
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_VENDAS,null, this);
        super.onResume();

    }
    private Menu menu;

    public void atualizaOpcoesMenu() {
        Vendas vendas = adaptadorVendas.getVendaSelecionado();

        boolean mostraAlterarEliminar = (vendas != null);

        menu.findItem(R.id.action_alterar).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.action_eliminar).setVisible(mostraAlterarEliminar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Toast.makeText(this, "Clickaste", Toast.LENGTH_SHORT).show();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_inserir) {
            Intent intent = new Intent(this, InserirVendas.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.action_alterar) {
            Intent intent = new Intent(this, AlterarVendas.class);
            intent.putExtra(ID_VENDAS, adaptadorVendas.getVendaSelecionado().getId());

            startActivity(intent);

            return true;
        } else if (id == R.id.action_eliminar) {
            Intent intent = new Intent(this, EleminarVendas.class);
            intent.putExtra(ID_VENDAS, adaptadorVendas.getVendaSelecionado().getId());

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this,VendasContentProvidar.ENDERECO_VENDAS,BDVendas.TODAS_COLUNAS, null,null ,BDVendas.CAMPO_CLIENTE
        );


        return cursorLoader;

    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        adaptadorVendas.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) { adaptadorVendas.setCursor(null);}

}
