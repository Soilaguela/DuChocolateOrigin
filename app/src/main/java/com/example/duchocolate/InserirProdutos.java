package com.example.duchocolate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class InserirProdutos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_produtos);

    }


    public void cancelarProduto (View v){
        Toast.makeText(this, "{" + getString(R.string.cancelado) + "}", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void GuardarProdutos (View v){//Inserir e as suas validaçes

        EditText ID_NP = (EditText) findViewById(R.id.ID_NP);//Nome cliente
        String NP = ID_NP.getText().toString();
        if (NP.trim().length() == 0) {//nome cliente
            ID_NP.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_NP.requestFocus();
            return;
        }
        /////////////////////////////////////////

        EditText ID_QuantE = (EditText) findViewById(R.id.ID_QuantE);//valor

        double valor = 0;//validação valor para maior k 0

        try {
            valor = Double.parseDouble(ID_QuantE.getText().toString());
        } catch (NumberFormatException e) {
            ID_QuantE.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_QuantE.requestFocus();
            return;
        }

        if (valor == 0) {
            ID_QuantE.setError(getString(R.string.erro_0));
            ID_QuantE.requestFocus();
            return;
        }

        Produtos produto = new Produtos();

        produto.setProdutoestoque(NP);
        produto.setQuantidade(valor);


        try {
            getContentResolver().insert(VendasContentProvidar.ENDERECO_PRODUTOS, produto.getContentValues());

            Toast.makeText(this, "Produto guardado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(ID_NP
                    ,
                    getString(R.string.erro_guardar_produto),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }

}
