package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EleminarCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleminar_cliente);
    }
    public void cancelarEleminarDetalhesVenda(View v) {
        finish();
    }

    public void EleminarDetalhesVenda(View v) {//Inserir e as suas validaçes

        EditText ID_NCVE = (EditText) findViewById(R.id.ID_NCVE);//Nome cliente
        String NCV = ID_NCVE.getText().toString();
        if (NCV.trim().length() == 0) {//nome cliente
            ID_NCVE.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_NCVE.requestFocus();
            return;
        }
        /////////////////////////////////////////


        EditText ID_EMPE = (EditText) findViewById(R.id.ID_EMP);//Nome cliente
        String NP = ID_EMPE.getText().toString();
        if (NP.trim().length() == 0) {//nome cliente
            ID_EMPE.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_EMPE.requestFocus();
            return;
        }
        /////////////////////////////////////////

        EditText ID_PREÇOE = (EditText) findViewById(R.id.ID_PREÇO);//valor

        double valor = 0;//validação valor para maior k 0

        try {
            valor = Double.parseDouble(ID_PREÇOE.getText().toString());
        } catch (NumberFormatException e) {
            ID_PREÇOE.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_PREÇOE.requestFocus();
            return;
        }

        if (valor == 0) {
            ID_PREÇOE.setError(getString(R.string.erro_0));
            ID_PREÇOE.requestFocus();
            return;
        }

        /////////////////////////////////////////

        EditText ID_TELEFE = (EditText) findViewById(R.id.ID_TELEF);//valor

        double valor1 = 0;//validação valor para maior k 0

        try {
            valor1 = Double.parseDouble(ID_TELEFE.getText().toString());
        } catch (NumberFormatException e) {
            ID_TELEFE.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_TELEFE.requestFocus();
            return;
        }

        if (valor1 == 0) {
            ID_TELEFE.setError(getString(R.string.erro_0));
            ID_TELEFE.requestFocus();
            return;
        }

        /////////////////////////////////////////


        /////////////////////////////////////////


        Toast.makeText(EleminarCliente.this, getString(R.string.GuardadoSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
        finish();
    }
}
