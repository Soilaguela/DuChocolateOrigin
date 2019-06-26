package com.example.duchocolate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlterarVendas extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_CLIENTES = 0;

    private Spinner spinneraletrarCliente;
    private  EditText idData;
    private Vendas vendas = null;
    private boolean clienteCarregadas = false;
    private boolean clienteAtualizada = false;

    private Uri enderecoVendasEditar;

    EditText editdata;
    Calendar calendario;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_vendas);
        editdata = findViewById(R.id.ID_datavenda);
        calendario= Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, monthOfYear);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Validardata();
            }
        };

     /* editdata.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              new DatePickerDialog(AlterarVendas.this, date,
              calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                      calendario.get(Calendar.DAY_OF_MONTH)
              ).show();
          }
      });*/


        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CLIENTES, null, this);

        spinneraletrarCliente = (Spinner) findViewById(R.id.spinnerCliente);
        idData = (EditText) findViewById(R.id.ID_datavenda);

        Intent intent = getIntent();
        long idVenda = intent.getLongExtra(MainVendas.ID_VENDAS, -1);

        if (idVenda == -1) {
            Toast.makeText(this, "Erro: não foi possível ler a Venda", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        enderecoVendasEditar = Uri.withAppendedPath(VendasContentProvidar.ENDERECO_VENDAS, String.valueOf(idVenda));

        Cursor cursor = getContentResolver().query(enderecoVendasEditar, BDVendas.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler o livro", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        vendas = Vendas.fromCursor(cursor);
        idData.setText(vendas.getData());
        actualizaVendasSelecionada();
    }

    private void Validardata() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,
                new Locale("pt", "PT"));
        editdata.setText(sdf.format(calendario.getTime()));
        editdata.setError(null);
    }


    private void actualizaVendasSelecionada() {
        if (!clienteCarregadas) return;
        if (clienteAtualizada) return;

        for (int i = 0; i < spinneraletrarCliente.getCount(); i++) {
            if (spinneraletrarCliente.getItemIdAtPosition(i) == vendas.getId()) {
                spinneraletrarCliente.setSelection(i);
                break;
            }
        }

        clienteAtualizada = true;
    }

    private void mostraClienteSpinner(Cursor cursorCliente) {
        SimpleCursorAdapter adaptadorVendas = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorCliente,
                new String[]{BDCliente.CAMPO_NOMECLIENTE1},
                new int[]{android.R.id.text1}
        );
        spinneraletrarCliente.setAdapter(adaptadorVendas);
    }


    ////////////////////Para Botoes ///////////////////////////

    public void canselarAlteracao (View v){
        finish();
    }

    public void Alterarvendas (View V){

        EditText ID_prodotosvendidos = (EditText) findViewById(R.id.id_descProduto);//Numero de produtos
        String dt = ID_prodotosvendidos.getText().toString();
        if (dt.trim().length() == 0) {//nome cliente
            ID_prodotosvendidos.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_prodotosvendidos.requestFocus();
            return;
        }

        String data = editdata.getText().toString();

        if (data.trim().length()==0){
            editdata.setError(getString(R.string.preecha_data));
            editdata.requestFocus();
            if (editdata.requestFocus()) {
                new DatePickerDialog(AlterarVendas.this, date,
                        calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH)).show();
                return;

            }
            Toast.makeText(this, "{" + getString(R.string.GuardadoSucesso) + "}", Toast.LENGTH_SHORT).show();
            finish();

        }

        long idCliente = spinneraletrarCliente.getSelectedItemId();

        // guardar os dados

        vendas.setDescricaoProdutoV(dt);
        vendas.setData(data);
        vendas.setCliente(idCliente);

  try {
      getContentResolver().update(enderecoVendasEditar, vendas.getContentValues(), null, null);

        Toast.makeText(this,getString(R.string.GuardadoSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
        finish();
      }catch (Exception e){
    Snackbar.make(
            idData,
            getString(R.string.erro_guardar_venda),
            Snackbar.LENGTH_LONG
            ).show();
    e.getStackTrace();
}

    }

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
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, VendasContentProvidar.ENDERECO_CLIENTE, BDCliente.TODAS_COLUNAS, null, null, BDCliente.CAMPO_NOMECLIENTE1
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
        clienteCarregadas = false;
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
        clienteCarregadas = false;
        clienteAtualizada = false;
        mostraClienteSpinner(null);

    }
}
