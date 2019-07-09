package com.example.duchocolate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class BDVendas implements BaseColumns {
    public static final String NOME_TABELA = "vendas";
    public static final String ALIAS_NOME_VENDA = "nomeVenda";

    public static final String CAMPO_DESCRICAOVENDA = "DescricaoProdutoV";
    //public static final String CAMPO_NOMECLIENTE = "Nomecliente";
    public static final String CAMPO_DATA="Data";
    public static final String CAMPO_CLIENTE = "cliente";
    public static final String CAMPO_NOME_CLIENTE = BDCliente.NOME_TABELA + "." + BDCliente.CAMPO_NOMECLIENTE1 + " AS " + ALIAS_NOME_VENDA; // tabela de categorias (só de leitura)


    public static final String[] TODAS_COLUNAS = new String[] {
            NOME_TABELA + "." + _ID , CAMPO_DESCRICAOVENDA ,  NOME_TABELA + "." + CAMPO_DATA ,NOME_TABELA + "." + CAMPO_CLIENTE ,  CAMPO_NOME_CLIENTE};


    private SQLiteDatabase db;

    public BDVendas(SQLiteDatabase db){
        this.db =db;
    }

    public void cria(){
        db.execSQL( " CREATE TABLE " + NOME_TABELA + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_DESCRICAOVENDA + " TEXT NOT NULL," +
                CAMPO_DATA + " TEXT NOT NULL, " +
                CAMPO_CLIENTE + " INTEGER  NOT NULL, "+
                "FOREIGN KEY (" + CAMPO_CLIENTE + ") REFERENCES " + BDCliente.NOME_TABELA + "(" + BDCliente._ID + ")" +
                ")"
        );
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
      String colunasSelect = TextUtils.join(",", columns);
        String sql = " SELECT " + colunasSelect +


     // String sql = " SELECT " + NOME_TABELA +"."+_ID+","+"NomeCliente"+","+"Empresa"+","+NOME_TABELA+".data,Telefone,Email,vendas,Preco"+
                " FROM " + NOME_TABELA + " INNER JOIN " + BDCliente.NOME_TABELA + " ON " + NOME_TABELA + "." + CAMPO_CLIENTE + "=" + BDCliente.NOME_TABELA + "." + BDCliente._ID;

        if (selection != null) {
            sql += " AND " + selection;
        }

        Log.d("listar Venda", "query: " + sql);

        return db.rawQuery(sql, selectionArgs);

     /*  String colunasSelect = TextUtils.join(",", columns);

        String sql = " SELECT " + colunasSelect +
                " FROM " + NOME_TABELA + " INNER JOIN " + BDCliente.NOME_TABELA + " WHERE " + NOME_TABELA + "." + CAMPO_CLIENTE + "=" + BDCliente.NOME_TABELA + "." + BDCliente._ID;

        if (selection != null) {
            sql += " AND " + selection;
        }

        Log.d("listar Venda", "query: " + sql);

        return db.rawQuery(sql, selectionArgs);*/
        /*A string sql ficou:


                  String sql = " SELECT " + colunasSelect +
                " FROM " + NOME_TABELA + " INNER JOIN " + BDCliente.NOME_TABELA + " WHERE " + NOME_TABELA + "." + CAMPO_CLIENTE + "=" + BDCliente.NOME_TABELA + "." + BDCliente._ID;
                */
    }


    public long insert(ContentValues values) {
        return  db.insert(NOME_TABELA, null, values);
    }

    public int update(ContentValues values, String whereClause, String [] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
