package com.example.duchocolate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InserirCliente extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_PRODUTOS = 0;


    private Spinner spinnerProduto;
    private EditText editTextDate;

    EditText editProfileAge;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_cliente);

       spinnerProduto =(Spinner)findViewById(R.id.spinnerCliente);

        editProfileAge = findViewById(R.id.ID_DATA);
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        editProfileAge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(InserirCliente.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_PRODUTOS, null, this);

       //spinnerProduto =(Spinner)findViewById(R.id.spinnerCliente);

        editTextDate = (EditText) findViewById(R.id.ID_DATA);
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,
                new Locale("pt", "PT"));
        editProfileAge.setText(sdf.format(myCalendar.getTime()));
        editProfileAge.setError(null);
    }

  @Override
    protected void onResume() {
        super.onResume();
    }

    private void mostraProdutoSpinner(Cursor cursorProduto) {
        SimpleCursorAdapter adaptadorProduto = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorProduto,
                new String[]{BDProduto.CAMPO_PRODUTOESTOQUE},
                new int[]{android.R.id.text1}
        );
        spinnerProduto.setAdapter(adaptadorProduto);
    }

    public void cancelarDetalhesVenda(View v) {
        Toast.makeText(this, "{" + getString(R.string.cancelado) + "}", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void GuardarDetalhesVenda(View v) {//Inserir e as suas validaçes

        EditText ID_NCV = (EditText) findViewById(R.id.ID_NCV);//CLIENTE
        String NCV = ID_NCV.getText().toString();
        if (NCV.trim().length() == 0) {//nome cliente
            ID_NCV.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_NCV.requestFocus();
            return;
        }
        /////////////////////////////////////////


        EditText ID_EMP = (EditText) findViewById(R.id.ID_EMP);//EMPRESA
        String NP = ID_EMP.getText().toString();
        if (NP.trim().length() == 0) {//nome cliente
            ID_EMP.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_EMP.requestFocus();
            return;
        }
        /////////////////////////////////////////
        EditText ID_EMAIL = (EditText) findViewById(R.id.ID_EMAIL);//EMAIL
        String editTextEmail = ID_EMAIL.getText().toString();
        if (editTextEmail.trim().length() == 0) {//EMAIL
            ID_EMAIL.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_EMAIL.requestFocus();
            return;
        }


        /////////////////////////

        EditText ID_PREÇO = (EditText) findViewById(R.id.ID_PREÇO);//PRECO
        double Preço = 0;//validação valor para maior k 0
        try {
            Preço = Double.parseDouble(ID_PREÇO.getText().toString());
        } catch (NumberFormatException e) {
            ID_PREÇO.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_PREÇO.requestFocus();
            return;
        }

        if (Preço == 0) {
            ID_PREÇO.setError(getString(R.string.erro_0));
            ID_PREÇO.requestFocus();
            return;
        }

        /////////////////////////////////////////

        EditText ID_TELEF = (EditText) findViewById(R.id.ID_TELEF);//TELEFONE

        int valor_telef = 0;//validação valor para maior k 0

        try {
            valor_telef = Integer.parseInt(ID_TELEF.getText().toString());
        } catch (NumberFormatException e) {
            ID_TELEF.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_TELEF.requestFocus();
            return;
        }

        Log.d("telefono", ""+valor_telef);

        if (valor_telef == 0) {
            ID_TELEF.setError(getString(R.string.erro_0));
            ID_TELEF.requestFocus();
            return;
        }
        ///////////




        /////////////////////////////////////////
        String data = editProfileAge.getText().toString();

        if (data.trim().length()==0){
            editProfileAge.setError(getString(R.string.preecha_data));
            editProfileAge.requestFocus();
            if (editProfileAge.hasFocus())
                new DatePickerDialog(InserirCliente.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            return;

            }
            Toast.makeText(this, "{" + getString(R.string.GuardadoSucesso) + "}", Toast.LENGTH_SHORT).show();
            finish();



        ////////////////////////////

        long idProduto = spinnerProduto.getSelectedItemId();

        Cliente cliente = new Cliente();

        cliente.setNomeCliente(NCV);
        cliente.setEmpresa(NP);
        cliente.setPreço(Preço);
        cliente.setTelefone(valor_telef);
        cliente.setEmail(editTextEmail);
        cliente.setData(data);
        cliente.setProdutos(idProduto);

        /////////////////////////////////////////


        try {
            getContentResolver().insert(VendasContentProvidar.ENDERECO_CLIENTE, cliente.getContentValues());

            Toast.makeText(this, getString(R.string.GuardadoSucesso), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextDate,
                    "Erro: Não foi possivel guardar Cliente!",
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader=new androidx.loader.content.CursorLoader(this,VendasContentProvidar.ENDERECO_PRODUTOS, BDProduto.TODAS_COLUNAS, null,null,BDProduto.CAMPO_PRODUTOESTOQUE)
         ;
        return cursorLoader ;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraProdutoSpinner(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {mostraProdutoSpinner(null);

    }
}
