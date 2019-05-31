package com.example.duchocolate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BDCliente implements BaseColumns {
    public static final String NOME_TABELA = "detalhesvendas";
    public static final String CAMPO_NOMECLIENTE1 = "NomeCliente";
    public static final String CAMPO_EMPRESA = "Empresa";
    public static final String CAMPO_EMAIL = "Email";
    public static final String CAMPO_PREÇO = "Preço";
    public static final String CAMPO_TELEFONE = "Telefone";
    public static final String CAMPO_DATA = "data";
    public static final String CAMPO_VENDAS = "vendas";


    public static final String[] TODAS_COLUNAS = new String[] {
            _ID, CAMPO_NOMECLIENTE1, CAMPO_EMPRESA, CAMPO_DATA, CAMPO_TELEFONE, CAMPO_EMAIL,CAMPO_VENDAS, CAMPO_PREÇO };


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
                CAMPO_VENDAS +" INTERGER NOT NULL,"+
                "FOREIGN KEY (" + CAMPO_VENDAS + ") REFERENCES " + BDVendas.NOME_TABELA + "(" + BDVendas._ID + ")" +
                ")"
        );
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
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
