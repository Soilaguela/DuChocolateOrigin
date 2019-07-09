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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlterarVendas extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_CLIENTES = 0;


    private Vendas vendas = null;
    private boolean produtoLoaded = false;
    private boolean produtoRefreshed = false;

    private Uri enderecoVendasEditar;
  //  private Vendas vendas = null;

    private EditText et_nome_cliente;;
    private Spinner spinnerCliente ;
    private EditText editTextDate;

    EditText editProfileAge;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_vendas);
        et_nome_cliente = (EditText) findViewById(R.id.id_descProduto);
        editTextDate = (EditText) findViewById(R.id.idData);
        spinnerCliente = (Spinner) findViewById(R.id.spinneraletrarCliente);

        editProfileAge = findViewById(R.id.ID_DATA);
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
       editProfileAge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AlterarVendas.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_CLIENTES, null, this);




        Intent intent = getIntent();

        long idvendas = intent.getLongExtra(MainClientes.ID_CLIENTES, -1);

        if (idvendas == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o Cliente Selecionado", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoVendasEditar = Uri.withAppendedPath(VendasContentProvidar.ENDERECO_CLIENTE, String.valueOf(idvendas));

        Cursor cursor = getContentResolver().query(enderecoVendasEditar, BDProduto.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler os Clientes", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        vendas = Vendas.fromCursor(cursor);


        et_nome_cliente.setText(String.valueOf(vendas.getDescricaoProdutoV()));
        editTextDate.setText(String.valueOf(vendas.getData()));
       
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,
                new Locale("pt", "PT"));
        editProfileAge.setText(sdf.format(myCalendar.getTime()));
        editProfileAge.setError(null);
    }

    private void mostraClienteSpinner(Cursor cursorCliente) {
        SimpleCursorAdapter AdaptadorClientes = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorCliente,
                new String[]{BDCliente.CAMPO_NOMECLIENTE1},
                new int[]{android.R.id.text1}
        );
        spinnerCliente.setAdapter(AdaptadorClientes);
    }
    private void refreshProfileSelected() {
        if (!produtoLoaded) return;
        if (produtoRefreshed) return;

        for (int i = 0; i < spinnerCliente.getCount(); i++) {
            if (spinnerCliente.getItemIdAtPosition(i) == vendas.getId()) {
                spinnerCliente.setSelection(i);
                break;
            }
        }

        produtoRefreshed = true;
    }

    public void canselarAlteracao(View v) {
        finish(); }

    public void Alterarvendas(View v) {//Inserir e as suas validaçes


        String nomecliente = et_nome_cliente.getText().toString();
        if (nomecliente.trim().length() == 0) {//nome cliente
            et_nome_cliente.setError(getString(R.string.erro_ID_prodotosvendidos));
            et_nome_cliente.requestFocus();
            return;
        }
        /////////////////////////////////////////


        String data = editProfileAge.getText().toString();

        if (data.trim().length()==0){
            editProfileAge.setError(getString(R.string.preecha_data));
            editProfileAge.requestFocus();
            if (editProfileAge.hasFocus())
                new DatePickerDialog(AlterarVendas.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            return;

        }
        Toast.makeText(this, "{" + getString(R.string.GuardadoSucesso) + "}", Toast.LENGTH_SHORT).show();
        finish();


        long idcliente = spinnerCliente.getSelectedItemId();


        vendas.setCliente(idcliente);
        vendas.setDescricaoProdutoV(nomecliente);
        vendas.setData(data);



        try {
            getContentResolver().update(enderecoVendasEditar, vendas.getContentValues(), null, null);

            Toast.makeText(this, getString(R.string.AlterarSucesso), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    et_nome_cliente,
                    getString(R.string.erro_guardar_produto),
                    Snackbar.LENGTH_LONG)
                    .show();

            Toast.makeText(this, "ERRO PÁ", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader=new androidx.loader.content.CursorLoader(this,VendasContentProvidar.ENDERECO_PRODUTOS, BDProduto.TODAS_COLUNAS, null,null,BDProduto.CAMPO_PRODUTOESTOQUE)
                ;
        return cursorLoader ;    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraClienteSpinner(data);
        produtoLoaded = true;
        refreshProfileSelected();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        produtoLoaded = false;
        produtoRefreshed= false;
        mostraClienteSpinner(null);

    }
}
