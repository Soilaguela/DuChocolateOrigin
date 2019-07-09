package com.example.duchocolate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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


public class AlterarCliente extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSO_LOADER_PRODUTOS = 0;

    private Uri enderecoClienteEditar;

    private Cliente cliente = null;


    private boolean produtoLoaded = false;
    private boolean produtoRefreshed = false;

    private EditText et_nome_cliente;
    private EditText et_nome_emp;
    private EditText et_preco;
    private EditText et_telefone;
    private EditText et_email;
    private EditText et_data;

    private Spinner spinnerProduto;
    //private EditText editTextDate;

    EditText editProfileAge;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_cliente);

        et_nome_cliente = (EditText) findViewById(R.id.ID_NCV);
        et_nome_emp = (EditText) findViewById(R.id.ID_EMP);
        et_preco = (EditText) findViewById(R.id.ID_PREÇO);
        et_telefone = (EditText) findViewById(R.id.ID_TELEF);
        et_email = (EditText) findViewById(R.id.ID_EMAIL);
        et_data = (EditText) findViewById(R.id.ID_DATA);

        spinnerProduto =(Spinner)findViewById(R.id.spinnerCliente);

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
                new DatePickerDialog(AlterarCliente.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_PRODUTOS, null, this);




        Intent intent = getIntent();

        long idcliente = intent.getLongExtra(MainClientes.ID_CLIENTES, -1);

        if (idcliente == -1) {
            Toast.makeText(this, "Erro: não foi possível ler o Cliente Selecionado", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoClienteEditar = Uri.withAppendedPath(VendasContentProvidar.ENDERECO_CLIENTE, String.valueOf(idcliente));

        Cursor cursor = getContentResolver().query(enderecoClienteEditar, BDProduto.TODAS_COLUNAS, null, null, null);

        if (!cursor.moveToNext()) {
            Toast.makeText(this, "Erro: não foi possível ler os Clientes", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

       cliente = Cliente.fromCursor(cursor);


        et_nome_cliente.setText(String.valueOf(cliente.getNomeCliente()));
        et_nome_emp.setText(String.valueOf(cliente.getEmpresa()));
        et_preco.setText(String.valueOf(cliente.getPreço()));
        et_telefone.setText(String.valueOf(cliente.getTelefone()));
        et_data.setText(String.valueOf(cliente.getData()));
        et_email.setText(String.valueOf(cliente.getEmail()));
      // et_nome_cliente.setText(String.valueOf(cliente.getProdutos()));

    }
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,
                new Locale("pt", "PT"));
        editProfileAge.setText(sdf.format(myCalendar.getTime()));
        editProfileAge.setError(null);
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
    private void refreshProfileSelected() {
        if (!produtoLoaded) return;
        if (produtoRefreshed) return;

        for (int i = 0; i < spinnerProduto.getCount(); i++) {
            if (spinnerProduto.getItemIdAtPosition(i) == cliente.getId()) {
                spinnerProduto.setSelection(i);
                break;
            }
        }

        produtoRefreshed = true;
    }

    public void cancelarAlteracao(View v) {
        finish(); }

    public void GuardarAlteracao(View v) {//Inserir e as suas validaçes


        String nomecliente = et_nome_cliente.getText().toString();
        if (nomecliente.trim().length() == 0) {//nome cliente
            et_nome_cliente.setError(getString(R.string.erro_ID_prodotosvendidos));
            et_nome_cliente.requestFocus();
            return;
        }
        /////////////////////////////////////////


        String empresa  = et_nome_emp.getText().toString();
        if (empresa.trim().length() == 0) {//nome cliente
            et_nome_emp.setError(getString(R.string.erro_ID_prodotosvendidos));
            et_nome_emp.requestFocus();
            return;
        }
        /////////////////////////////////////////


        String email  = et_email.getText().toString();
        if (email.trim().length() == 0) {//nome cliente
            et_email.setError(getString(R.string.erro_ID_prodotosvendidos));
            et_email.requestFocus();
            return;
        }
        /////////////////////////////////////////




        double valor = 0;//validação valor para maior k 0

        try {
            valor = Double.parseDouble(et_preco.getText().toString());
        } catch (NumberFormatException e) {
            et_preco.setError(getString(R.string.erro_ID_prodotosvendidos));
            et_preco.requestFocus();
            return;
        }

        if (valor == 0) {
            et_preco.setError(getString(R.string.erro_0));
            et_preco.requestFocus();
            return;
        }

        /////////////////////////////////////////


        int telefone = 0;//validação valor para maior k 0

        try {
            telefone = Integer.parseInt(et_telefone.getText().toString());
        } catch (NumberFormatException e) {
            et_telefone.setError(getString(R.string.erro_ID_prodotosvendidos));
            et_telefone.requestFocus();
            return;
        }

        if (telefone == 0) {
            et_telefone.setError(getString(R.string.erro_0));
            et_telefone.requestFocus();
            return;
        }

        String data = editProfileAge.getText().toString();

        if (data.trim().length()==0){
            editProfileAge.setError(getString(R.string.preecha_data));
            editProfileAge.requestFocus();
            if (editProfileAge.hasFocus())
                new DatePickerDialog(AlterarCliente.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            return;

        }
        Toast.makeText(this, "{" + getString(R.string.GuardadoSucesso) + "}", Toast.LENGTH_SHORT).show();
        finish();


        long idProduto = spinnerProduto.getSelectedItemId();


        cliente.setProdutos(idProduto);
        cliente.setNomeCliente(nomecliente);
        cliente.setEmpresa(empresa);
        cliente.setPreço(valor);
        cliente.setTelefone(telefone);
        cliente.setData(data);
        cliente.setEmail(email);



        try {
            getContentResolver().update(enderecoClienteEditar, cliente.getContentValues(), null, null);

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
        mostraProdutoSpinner(data);
        produtoLoaded = true;
        refreshProfileSelected();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        produtoLoaded = false;
        produtoRefreshed= false;
        mostraProdutoSpinner(null);
    }
}

