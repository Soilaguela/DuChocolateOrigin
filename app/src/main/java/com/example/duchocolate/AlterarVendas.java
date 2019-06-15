package com.example.duchocolate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AlterarVendas extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_CLIENTES = 0;

    private EditText ID_nomecliente;
    private Spinner spinnerCliente;
    private  EditText ID_datavenda;


    private Vendas vendas = null;

    private boolean clenteCarregadas = false;
    private boolean clenteAtualizada = false;

    private Uri enderecoVendasEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_vendas);



        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CLIENTES, null, this);

        spinnerCliente = (Spinner) findViewById(R.id.spinnerCliente);
        ID_datavenda = (EditText) findViewById(R.id.ID_datavenda);

        Intent intent= getIntent();


        long idVendas = intent.getLongExtra(MainVendas.ID_VENDAS, -1);

        if (idVendas == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoVendasEditar = Uri.withAppendedPath(VendasContentProvidar.ENDERECO_VENDAS, String.valueOf(idVendas));

        Cursor cursor = getContentResolver().query(enderecoVendasEditar, BDVendas.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        vendas = Vendas.fromCursor(cursor);

        ID_datavenda.setText(String.valueOf(vendas.getData()));

        actualizaVendasSelecionada();
    }


    private void actualizaVendasSelecionada() {
        if (!clenteCarregadas) return;
        if (clenteAtualizada) return;

        for (int i = 0; i < spinnerCliente.getCount(); i++) {
            if (spinnerCliente.getItemIdAtPosition(i) == vendas.getId()) {
                spinnerCliente.setSelection(i);
                break;
            }
        }

        clenteAtualizada = true;
    }

    private void mostraClienteSpinner(Cursor cursorCliente) {
        SimpleCursorAdapter adaptadorVendas = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorCliente,
                new String[]{BDCliente.CAMPO_NOMECLIENTE1},
                new int[]{android.R.id.text1}
        );
        spinnerCliente.setAdapter(adaptadorVendas);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
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
            return true;
        } else if (id == R.id.action_guardar) {
            Alterarvendas();
            return true;
        } else if (id == R.id.action_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    ///////Para s Menus ////////////////////////////////////

    private void Alterarvendas() {

        EditText ID_prodotosvendidos = (EditText) findViewById(R.id.ID_produtosvendidos);//Numero de produtos
        String dt = ID_prodotosvendidos.getText().toString();
        if (dt.trim().length() == 0) {//nome cliente
            ID_prodotosvendidos.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_prodotosvendidos.requestFocus();
            return;
        }



        String data = ID_datavenda.getText().toString();

        if (data.trim().isEmpty()) {
            ID_datavenda.setError(getString(R.string.preecha_data));
            return;
        }
        /*  int produtos;

        String strPagina = editTextPagina.getText().toString();

        if (strPagina.trim().isEmpty()) {
            editTextPagina.setError(getString(R.string.preecha_pagina));
            return;
        }*/

        long idCliente = spinnerCliente.getSelectedItemId();

        // guardar os dados
        Vendas vendas = new Vendas();
        vendas.setDescricaoProdutoV(dt);
        vendas.setData(data);
        vendas.setCliente(idCliente);
        try {

            Toast.makeText(AlterarVendas.this,getString(R.string.AlterarSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
            finish();
        }catch (Exception e){
            Snackbar.make(
                    ID_nomecliente,
                    getString(R.string.erro_guardar_venda),
                    Snackbar.LENGTH_LONG
            ).show();
            e.getStackTrace();
        }
    }


    ////////////////////Para Botoes ///////////////////////////

    public void canselarAlteracao (View v){
        finish();
    }
    public void Alterarvendas (View V){
        EditText id_descProduto = (EditText) findViewById(R.id.id_descProduto);//Nome cliente
        String nomecliente = id_descProduto.getText().toString();

        if (nomecliente.trim().length() == 0) {  //nome cliente
            id_descProduto.setError(getString(R.string.erro_ID_prodotosvendidos));
            id_descProduto.requestFocus();
            return ;
        }

        String data = ID_datavenda.getText().toString();

        if (data.trim().isEmpty()) {
            ID_datavenda.setError(getString(R.string.preecha_data));
            return;
        }

        //////////////////////////////////
        EditText id_ClienteNome = (EditText) findViewById(R.id.id_ClienteNome);//Nome cliente
        String ClienteNome = id_descProduto.getText().toString();

        long idCliente = spinnerCliente.getSelectedItemId();

        // guardar os dados
        Vendas vendas = new Vendas();
        vendas.setDescricaoProdutoV(nomecliente);
        vendas.setData(data);
        vendas.setCliente(idCliente);
        try {


            if (ClienteNome.trim().length() == 0) {  //nome cliente
            id_descProduto.setError(getString(R.string.erro_ID_prodotosvendidos));
            id_descProduto.requestFocus();
            return ;
        }

        Toast.makeText(AlterarVendas.this,getString(R.string.AlterarSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
        finish();
        }catch (Exception e){
            Snackbar.make(
                    ID_nomecliente,
                    getString(R.string.erro_guardar_venda),
                    Snackbar.LENGTH_LONG
            ).show();
            e.getStackTrace();
        }
    }
////////////////////////Para Botoes////////////////////////////////////
    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, VendasContentProvidar.ENDERECO_VENDAS, BDVendas.TODAS_COLUNAS, null, null, BDVendas.CAMPO_CLIENTE
        );
        return cursorLoader;
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     *
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraClienteSpinner(data);
        clenteCarregadas = false;
        actualizaVendasSelecionada();


    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        clenteCarregadas = false;
        clenteAtualizada = false;
        mostraClienteSpinner(null);

    }
}
