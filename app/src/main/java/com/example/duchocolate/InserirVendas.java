package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InserirVendas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_vendas);
    }
    public void canselar (View v){
        finish();
    }
    public void GuardarVendas (View v){//Inserir e as suas validaçes
        EditText ID_prodotosvendidos = (EditText) findViewById(R.id.ID_produtosvendidos);//Numero de produtos
        String dt = ID_prodotosvendidos.getText().toString();
        if (dt.trim().length() == 0) {//nome cliente
            ID_prodotosvendidos.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_prodotosvendidos.requestFocus();
            return;
        }



        EditText ID_nomecliente = (EditText) findViewById(R.id.ID_nomecliente);//Nome cliente
        String NClient = ID_nomecliente.getText().toString();


        if (NClient.trim().length() == 0) {//nome cliente
            ID_nomecliente.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_nomecliente.requestFocus();
            return;
        }

        Toast.makeText(InserirVendas.this,getString(R.string.GuardadoSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
        finish();
    }

}
