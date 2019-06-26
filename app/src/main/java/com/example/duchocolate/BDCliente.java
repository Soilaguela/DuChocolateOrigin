package com.example.duchocolate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class BDCliente implements BaseColumns {
    public static final String NOME_TABELA = "clientes";
    public static final String CAMPO_NOMECLIENTE1 = "NomeCliente";
    public static final String CAMPO_EMPRESA = "Empresa";
    public static final String CAMPO_EMAIL = "Email";
    public static final String CAMPO_PREÇO = "Preco";
    public static final String CAMPO_TELEFONE = "Telefone";
    public static final String CAMPO_DATA = "data";
    public static final String CAMPO_PRODUTO = "produto";
    public static final String ALIAS_NOME_CLIENTE = "nomcliente";
    public static final String CAMPO_NOME_CLIENTE = BDProduto.NOME_TABELA + "." + BDProduto.CAMPO_PRODUTOESTOQUE + " AS " + ALIAS_NOME_CLIENTE; // tabela de categorias (só de leitura)



    public static final String[] TODAS_COLUNAS = new String[] {
            NOME_TABELA + "." +  _ID, NOME_TABELA + "." + CAMPO_NOMECLIENTE1, CAMPO_EMPRESA,NOME_TABELA + "." + CAMPO_PRODUTO ,  CAMPO_DATA, CAMPO_TELEFONE, CAMPO_EMAIL, CAMPO_PREÇO ,CAMPO_NOME_CLIENTE};

//
    /// NOME_TABELA + "." +
    private SQLiteDatabase db;

    public BDCliente(SQLiteDatabase db){
        this.db =db;
    }

    public void cria(){
        db.execSQL( "CREATE TABLE " + NOME_TABELA + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_NOMECLIENTE1 + " TEXT NOT NULL," +
                CAMPO_EMPRESA + "  TEXT NOT NULL," +
                CAMPO_EMAIL + " TEXT NOT NULL," +
                CAMPO_PREÇO+" INTEGER NOT NULL," +
                CAMPO_DATA + " TEXT NOT NULL," +
                CAMPO_TELEFONE + " INTEGER NOT NULL," +
                CAMPO_PRODUTO +" INTERGER NOT NULL,"+
                "FOREIGN KEY (" + CAMPO_PRODUTO + ") REFERENCES " + BDProduto.NOME_TABELA + "(" + BDProduto._ID + ")" +
                ")"
        );
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
     //   return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
        String colunasSelect = TextUtils.join(",", columns);
        //
        // SELECT clientes._id,clientes.NomeCliente,Empresa,data,Telefone,Email,clientes.produto,Preco,clientes.produto.Produtoestoque  nomcliente clientes  produto  clientes.produto=produto._id
    // String sql = " SELECT " + NOME_TABELA +"."+_ID+","+"Produtoestoque"+","+"Quantidade"+","+NOME_TABELA+".produto"+
       String sql = " SELECT " + colunasSelect +
                " FROM " + NOME_TABELA + " INNER JOIN " + BDProduto.NOME_TABELA + " WHERE " + NOME_TABELA + "." + CAMPO_PRODUTO + "=" + BDProduto.NOME_TABELA + "." + BDProduto._ID;

        if (selection != null) {
            sql += " AND " + selection;
        }

        Log.d("listar Produto", "query: " + sql);

        return db.rawQuery(sql, selectionArgs);
    }

    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    public int update(ContentValues values, String whereClause, String [] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }


}
