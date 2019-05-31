package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InserirCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_cliente);
    }
    public void cancelarDetalhesVenda(View v) {
        finish();
    }

    public void GuardarDetalhesVenda(View v) {//Inserir e as suas validaçes

        EditText ID_NCV = (EditText) findViewById(R.id.ID_NCV);//Nome cliente
        String NCV = ID_NCV.getText().toString();
        if (NCV.trim().length() == 0) {//nome cliente
            ID_NCV.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_NCV.requestFocus();
            return;
        }
        /////////////////////////////////////////


        EditText ID_EMP = (EditText) findViewById(R.id.ID_EMP);//Nome cliente
        String NP = ID_EMP.getText().toString();
        if (NP.trim().length() == 0) {//nome cliente
            ID_EMP.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_EMP.requestFocus();
            return;
        }
        /////////////////////////////////////////

        EditText ID_PREÇO = (EditText) findViewById(R.id.ID_PREÇO);//valor

        double valor = 0;//validação valor para maior k 0

        try {
            valor = Double.parseDouble(ID_PREÇO.getText().toString());
        } catch (NumberFormatException e) {
            ID_PREÇO.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_PREÇO.requestFocus();
            return;
        }

        if (valor == 0) {
            ID_PREÇO.setError(getString(R.string.erro_0));
            ID_PREÇO.requestFocus();
            return;
        }

        /////////////////////////////////////////

        EditText ID_TELEF = (EditText) findViewById(R.id.ID_TELEF);//valor

        double valor1 = 0;//validação valor para maior k 0

        try {
            valor1 = Double.parseDouble(ID_TELEF.getText().toString());
        } catch (NumberFormatException e) {
            ID_TELEF.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_TELEF.requestFocus();
            return;
        }

        if (valor1 == 0) {
            ID_TELEF.setError(getString(R.string.erro_0));
            ID_TELEF.requestFocus();
            return;
        }

        /////////////////////////////////////////


        /////////////////////////////////////////


        Toast.makeText(InserirCliente.this, getString(R.string.GuardadoSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
        finish();
    }
}
