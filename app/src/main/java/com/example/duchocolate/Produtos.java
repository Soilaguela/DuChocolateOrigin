package com.example.duchocolate;

import android.content.ContentValues;
import android.database.Cursor;

public class Produtos {
    private long id;
    private String Produtoestoque;
    private Double Quantidade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProdutoestoque() {
        return Produtoestoque;
    }

    public void setProdutoestoque(String produtoestoque) {
        Produtoestoque = produtoestoque;
    }

    public Double getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(Double quantidade) {
        Quantidade = quantidade;
    }
    public ContentValues getContentValues() {
        android.content.ContentValues valores = new ContentValues();

        valores.put(BDProduto.CAMPO_PRODUTOESTOQUE, Produtoestoque);
        valores.put(BDProduto.CAMPO_QUANTIDADE, Quantidade);


        return valores;
    }
    public static Produtos fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BDProduto._ID)
        );

        String Produtoestoque = cursor.getString(
                cursor.getColumnIndex(BDProduto.CAMPO_PRODUTOESTOQUE)
        );

        Double Quantidade = cursor.getDouble(
                cursor.getColumnIndex(BDProduto.CAMPO_QUANTIDADE)
        );



        Produtos produto = new Produtos();

        produto.setId(id);
        produto.setProdutoestoque(Produtoestoque);
        produto.setQuantidade(Quantidade);

        return produto;
    }

}
