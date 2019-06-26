package com.example.duchocolate;

import android.content.ContentValues;
import android.database.Cursor;

public class Cliente {
    private long id;
    private String NomeCliente;
    private String Empresa ;
    private Double Preço ;
    private int Telefone;
    private String data;
    private String Email;
    private long produtos;

    public String getNomcliente() {
        return nomcliente;
    }

    public void setNomcliente(String nomcliente) {
        this.nomcliente = nomcliente;
    }

    private String nomcliente; // Campo "externo"


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return NomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        NomeCliente = nomeCliente;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String empresa) {
        Empresa = empresa;
    }

    public Double getPreço() {
        return Preço;
    }

    public void setPreço(Double preço) {
        Preço = preço;
    }

    public int getTelefone() {
        return Telefone;
    }

    public void setTelefone(int telefone) {
        Telefone = telefone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }




    public long getProdutos() {
        return produtos;
    }

    public void setProdutos(long produtos) {
        this.produtos = produtos;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BDCliente.CAMPO_NOMECLIENTE1, NomeCliente);
        valores.put(BDCliente.CAMPO_EMPRESA, Empresa);
        valores.put(BDCliente.CAMPO_PREÇO, Preço);
        valores.put(BDCliente.CAMPO_TELEFONE, Telefone);
        valores.put(BDCliente.CAMPO_EMAIL, Email);
        valores.put(BDCliente.CAMPO_DATA, data);
        valores.put(BDCliente.CAMPO_PRODUTO, produtos);


        return valores;
    }

    public static Cliente fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BDCliente._ID)
        );

        String NomeCliente = cursor.getString(
                cursor.getColumnIndex(BDCliente.CAMPO_NOMECLIENTE1)
        );

        String Empresa = cursor.getString(
                cursor.getColumnIndex(BDCliente.CAMPO_EMPRESA)
        );
        String Email = cursor.getString(
                cursor.getColumnIndex(BDCliente.CAMPO_EMAIL)
        );

        String data = cursor.getString(
                cursor.getColumnIndex(BDCliente.CAMPO_DATA)
        );

        Double Preço = cursor.getDouble(
                cursor.getColumnIndex(BDCliente.CAMPO_PREÇO)
        );
        int Telefone = cursor.getInt(
                cursor.getColumnIndex(BDCliente.CAMPO_TELEFONE)
        );
        long produtos = cursor.getLong(
                cursor.getColumnIndex(BDCliente.CAMPO_PRODUTO)
        );
        String nomcliente = cursor.getString(
                cursor.getColumnIndex(BDCliente.ALIAS_NOME_CLIENTE)
        );





        Cliente cliente = new Cliente();

        cliente.setId(id);
        cliente.setNomeCliente(NomeCliente);
        cliente.setEmpresa(Empresa);
        cliente.setPreço(Preço);
        cliente.setTelefone(Telefone);
        cliente.setEmail(Email);
        cliente.setData(data);
        cliente.setProdutos(produtos);
        cliente.nomcliente = nomcliente;


        return cliente;
    }


}
