package com.example.duchocolate;

import android.content.Context;
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
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

//import android.content.CursorLoader;

public class MainProdutos extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String ID_PRODUTO = "ID_PRODUTO";

    private static final int ID_CURSO_LOADER_PRODUTOS = 0;

    private RecyclerView recyclerViewProdutos;
    private AdaptadorProdutos adaptadorProdutos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_produto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_PRODUTOS, null, this);


        recyclerViewProdutos=(RecyclerView) findViewById(R.id.recyclerViewProdutos);
        adaptadorProdutos=new AdaptadorProdutos(this);
        recyclerViewProdutos.setAdapter(adaptadorProdutos);
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_PRODUTOS, null, this);

        super.onResume();
    }

    private Menu menu;

    public void atualizaOpcoesMenu() {
        Produtos produtos = adaptadorProdutos.getProdutoSelecionado();

        boolean mostraAlterarEliminar = (produtos != null);

        menu.findItem(R.id.action_alterar).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.action_eliminar).setVisible(mostraAlterarEliminar);
    }

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
            Toast.makeText(this,"Inserir",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, InserirProdutos.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.action_alterar) {
            Toast.makeText(this,"Alterar",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, AlterarProdutos.class);
            intent.putExtra(ID_PRODUTO, adaptadorProdutos.getProdutoSelecionado().getId());

            startActivity(intent);

            return true;
        } else if (id == R.id.action_eliminar) {
            Toast.makeText(this,"Eleminar",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, EleminarProdutos.class);
            intent.putExtra(ID_PRODUTO, adaptadorProdutos.getProdutoSelecionado().getId());

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Loader<Cursor> cursorLoader = new CursorLoader(this,VendasContentProvidar.ENDERECO_PRODUTOS,BDProduto.TODAS_COLUNAS, null,null, BDProduto.CAMPO_PRODUTOESTOQUE);

        return cursorLoader;
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
       /* FloatingActionButton fab = findViewById(R.id.fab);

        Snackbar.make(fab, "Produtos existentes: " + data.getCount(), Snackbar.LENGTH_INDEFINITE).show();
*/
       adaptadorProdutos.setCursor(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
