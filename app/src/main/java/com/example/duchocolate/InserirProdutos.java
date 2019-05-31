package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InserirProdutos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_produtos);
    }
    public void cancelarProduto (View v){
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

        /////////////////////////////////////////
        Toast.makeText(InserirProdutos.this,getString(R.string.GuardadoSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
        finish();
    }
}
