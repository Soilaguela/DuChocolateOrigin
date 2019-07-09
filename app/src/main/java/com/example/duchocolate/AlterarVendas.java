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

    private EditText et_descr_venda;;
    private Spinner spinnerVenda ;
    private EditText editTextDate;

    EditText editProfileAge;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_vendas);
        et_descr_venda = (EditText) findViewById(R.id.id_descProduto);
        editTextDate = (EditText) findViewById(R.id.idData);
        spinnerVenda = (Spinner) findViewById(R.id.spinneraletrarCliente);

        editProfileAge = findViewById(R.id.idData);
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

        long idvendas = intent.getLongExtra(MainVendas.ID_VENDAS, -1);

        if (idvendas == -1) {
            Toast.makeText(this, "Erro: não foi possível ler a Venda Selecionada", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoVendasEditar = Uri.withAppendedPath(VendasContentProvidar.ENDERECO_VENDAS, String.valueOf(idvendas));

        Cursor cursor = getContentResolver().query(enderecoVendasEditar, BDVendas.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler as Vendas", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        vendas = Vendas.fromCursor(cursor);


        et_descr_venda.setText(String.valueOf(vendas.getDescricaoProdutoV()));
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
        spinnerVenda.setAdapter(AdaptadorClientes);
    }
    private void refreshProfileSelected() {
        if (!produtoLoaded) return;
        if (produtoRefreshed) return;

        for (int i = 0; i < spinnerVenda.getCount(); i++) {
            if (spinnerVenda.getItemIdAtPosition(i) == vendas.getId()) {
                spinnerVenda.setSelection(i);
                break;
            }
        }

        produtoRefreshed = true;
    }

    public void canselarAlteracao(View v) {
        finish(); }

    public void Alterarvendas(View v) {//Inserir e as suas validaçes


        String nomecliente = et_descr_venda.getText().toString();
        if (nomecliente.trim().length() == 0) {//nome cliente
            et_descr_venda.setError(getString(R.string.erro_ID_prodotosvendidos));
            et_descr_venda.requestFocus();
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
        Toast.makeText(this, getString(R.string.GuardadoSucesso) , Toast.LENGTH_SHORT).show();
        finish();


        long idcliente = spinnerVenda.getSelectedItemId();


        vendas.setCliente(idcliente);
        vendas.setDescricaoProdutoV(nomecliente);
        vendas.setData(data);



        try {
            getContentResolver().update(enderecoVendasEditar, vendas.getContentValues(), null, null);

            Toast.makeText(this, getString(R.string.AlterarSucesso), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    et_descr_venda,
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
        androidx.loader.content.CursorLoader cursorLoader=new androidx.loader.content.CursorLoader(this,VendasContentProvidar.ENDERECO_CLIENTE, BDCliente.TODAS_COLUNAS, null,null,BDCliente.CAMPO_NOMECLIENTE1)
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
