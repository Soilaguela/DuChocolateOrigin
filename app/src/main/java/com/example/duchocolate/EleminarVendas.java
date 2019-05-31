package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EleminarVendas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleminar_vendas);
    }

    public void cancelarEleninar (View v){
        finish();
    }

    public void EleminarVendas (View v){//Inserir e as suas validaçes

        EditText ID_DP = (EditText) findViewById(R.id.ID_DP);//Nome cliente
        String dp = ID_DP.getText().toString();

        if (dp.trim().length() == 0) {
            ID_DP.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_DP.requestFocus();
            return;
        }
        //////////////////////////////////////////

        EditText ID_NC = (EditText) findViewById(R.id.ID_nomecliente);//nome cliente
        String NC = ID_NC.getText().toString();

        if (NC.trim().length() == 0) {
            ID_NC.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_NC.requestFocus();
            return;
        }

        Toast.makeText(EleminarVendas.this,getString(R.string.EleminarSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
        finish();
    }
}
