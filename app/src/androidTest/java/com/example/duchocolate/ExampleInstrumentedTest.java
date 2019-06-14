package com.example.duchocolate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.duchocolate", appContext.getPackageName());
    }
    @Before
    public void apagaBaseDados() {
        getAppContext().deleteDatabase(BDVendasOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void criaBDDuchocolate(){
        Context appContext = getAppContext();
        BDVendasOpenHelper openHelper = new BDVendasOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }
    private Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void testCRUD() {

        BDVendasOpenHelper openHelper = new BDVendasOpenHelper(getAppContext ());
        SQLiteDatabase db = openHelper.getWritableDatabase();
        BDProduto tabelaProduto = new BDProduto(db);


        // Teste para ler Produtos (cRud)
        Cursor cursorProduto = getProduto(tabelaProduto);
        assertEquals(0, cursorProduto.getCount());

        // Teste escreva/ler Produtos (CRud)
        String nome = "Em Pó";
        int quilos=234;
        long idEmPo = criaProduto(tabelaProduto, nome,quilos);

        cursorProduto = getProduto(tabelaProduto);
        assertEquals(1, cursorProduto.getCount());

        Produtos produto = getProdutoComID(cursorProduto, idEmPo);
        assertEquals(nome, produto.getProdutoestoque());

        nome= "Seco" ;
        quilos=543;
        long idSeco = criaProduto(tabelaProduto, nome,quilos);

        cursorProduto = getProduto(tabelaProduto);
        assertEquals(2, cursorProduto.getCount());

        produto= getProdutoComID(cursorProduto,idSeco);
        assertEquals(nome, produto.getProdutoestoque());
        // Teste Update/Read (cRUd)
        nome = "Em Gomos";
        produto.setProdutoestoque(nome);
        int registosAlterados = tabelaProduto.update(produto.getContentValues(),BDProduto._ID+"=?",new String[]{String.valueOf(idSeco)});

        assertEquals(1,registosAlterados);
        cursorProduto=getProduto(tabelaProduto);
        produto = getProdutoComID(cursorProduto,idSeco);
        assertEquals(nome,produto.getProdutoestoque());
        // Teste Create/Delete/Read (CRuD)

        long id = criaProduto(tabelaProduto,"Teste",567);
        cursorProduto = getProduto(tabelaProduto);
        assertEquals(3,cursorProduto.getCount());

        tabelaProduto.delete(BDProduto._ID+"=?",new String[]{String.valueOf(id)});
        cursorProduto = getProduto(tabelaProduto);
        assertEquals(2,cursorProduto.getCount());
        getProdutoComID(cursorProduto,id);


        // Teste create/read Cliente (CRud)////////
        BDCliente tabelaCliente = new BDCliente(db);
        Cursor cursorCliente = getClientes(tabelaCliente);
        assertEquals(0,cursorCliente.getCount());

        String NomeCliente="Carla";
        String Empresa ="SUL";
        int Preço =455;
        int Telefone=956789678;
        String Email="carla@gmail.com";
        String data="2/5/2019";


        long idcliente =criaCliente(tabelaCliente,NomeCliente,Preço,Telefone,Email,data,Empresa,idSeco);
        cursorCliente = getClientes(tabelaCliente);
        assertEquals(1,cursorCliente.getCount());



        Cliente cliente= getClientesComID(cursorCliente,idcliente);
        assertEquals(NomeCliente,cliente.getNomeCliente());
        assertEquals(Empresa, cliente.getEmpresa());
        assertEquals(Telefone,cliente.getTelefone());
        assertEquals(Preço, cliente.getPreço());
        assertEquals(Email, cliente.getEmail());
        assertEquals(data, cliente.getData());
        assertEquals(idSeco, cliente.getProdutos());

        id = criaCliente(tabelaCliente,"Carlos",23,345678444,"fr@gmail.com", "5/7/2019","JHT",idSeco);
        cursorCliente=getClientes(tabelaCliente);
        assertEquals(2,cursorCliente.getCount());

        // Teste read/update c (cRUd)
        cliente= getClientesComID(cursorCliente, id);
        NomeCliente="Tereza";
        Empresa="CULT";
        Preço=79;
        Telefone=956678443;
        Email="trz@gmail.com";
        data="2/7/2019";

        cliente.setNomeCliente(NomeCliente);
        cliente.setEmail(Email);
        cliente.setEmpresa(Empresa);
        cliente.setTelefone(Telefone);
        cliente.setPreço(Preço);
        cliente.setData(data);

        tabelaCliente.update(cliente.getContentValues(),BDCliente._ID+"=?",new String[]{String.valueOf(id)});
        cursorCliente=getClientes(tabelaCliente);
        cliente= getClientesComID(cursorCliente,id);
        assertEquals(NomeCliente,cliente.getNomeCliente());
        assertEquals(Empresa, cliente.getEmpresa());
        assertEquals(Preço, cliente.getPreço());
        assertEquals(Telefone,cliente.getTelefone());
        assertEquals(Email, cliente.getEmail());
        assertEquals(data, cliente.getData());
        assertEquals(idSeco, cliente.getProdutos());

        // Teste read/delete clientes (cRuD)
        tabelaCliente.delete(BDCliente._ID+"=?", new String[]{String.valueOf(id)});
        cursorCliente= getClientes(tabelaCliente);
        assertEquals(1,cursorCliente.getCount());



        /////////////////////Vendas/////////////////
        BDVendas tabelaVendas = new BDVendas(db);
        Cursor cursorVendas= getVenda(tabelaVendas);
        assertEquals(0,cursorVendas.getCount());


        //
        String DescricaoProdutoV = "Seco";
        String Nomecliente = "Carlos Fernandes";
        String Data = "3/4/2017";

        long idvenda = criaVendas(tabelaVendas, DescricaoProdutoV ,Nomecliente,Data, idcliente);
        cursorVendas= getVenda(tabelaVendas);
        assertEquals(1,cursorVendas.getCount());



        Vendas vendas = getVendacComID(cursorVendas,idvenda);
        assertEquals(DescricaoProdutoV,vendas.getDescricaoProdutoV());
        assertEquals(Nomecliente,vendas.getNomecliente());
        assertEquals(Data,vendas.getData());
        assertEquals(idcliente, vendas.getCliente());

        idvenda  =criaVendas(tabelaVendas,"seco","Carlos","7/7/2016",id);
        cursorVendas= getVenda(tabelaVendas);
        assertEquals(2,cursorVendas.getCount());
        // Teste read/update vendas (cRUd)
        vendas= getVendacComID(cursorVendas,idvenda);
        DescricaoProdutoV= "Em Caclos";
        Nomecliente="Bruno";
        Data="2/7/2019";

        vendas.setDescricaoProdutoV(DescricaoProdutoV);
        vendas.setNomecliente(Nomecliente);
        vendas.setData(Data);
        vendas.setCliente(idcliente);

        tabelaVendas.update(vendas.getContentValues(),BDVendas._ID+"=?", new String[]{String.valueOf(idvenda)});

        cursorVendas= getVenda(tabelaVendas);
        vendas= getVendacComID(cursorVendas, idvenda);
        assertEquals(DescricaoProdutoV,vendas.getDescricaoProdutoV());
        assertEquals(Nomecliente, vendas.getNomecliente());
        assertEquals(Data, vendas.getData());
        assertEquals(idcliente, vendas.getCliente());

        // Teste read/delete  (cRuD)
        tabelaVendas.delete(BDVendas._ID+"=?", new String[]{String.valueOf(id)});
        cursorVendas= getVenda(tabelaVendas);
        assertEquals(2,cursorVendas.getCount());

    }

    private Cliente getClientesComID(Cursor cursor, long id) {
        Cliente cliente= null;
        while (cursor.moveToNext()){
            cliente = Cliente.fromCursor(cursor);

            if (cliente.getId()==id){
                break;
            }
        }
        assertNotNull(cliente);
        return cliente;
    }

    private long criaCliente(BDCliente tabelaCliente, String nomeCliente, int preço, int telefone, String email, String data, String empresa, long produtos) {
        Cliente cliente = new Cliente();

        cliente.setNomeCliente(nomeCliente);
        cliente.setEmpresa(empresa);
        cliente.setPreço(preço);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        cliente.setData(data);
        cliente.setProdutos(produtos);

        long id = tabelaCliente.insert(cliente.getContentValues());
        // Log.d("CREATION","Id "+id);
        assertNotEquals(-1, id);
        return id;
    }

    private Cursor getClientes(BDCliente tabelaCliente) {
        return tabelaCliente.query(BDCliente.TODAS_COLUNAS,null,null, null, null, null);
    }


    private Vendas getVendacComID(Cursor cursor, long id) {
        Vendas vendas=null;
        while (cursor.moveToNext()){
            vendas= Vendas.fromCursor(cursor);
            if ( vendas.getId()==id){
                break;
            }
        }
        assertNotNull(vendas);

        return vendas;
    }


    private long criaVendas(BDVendas tabelaVendas, String DescricaoProdutoV, String Nomecliente, String Data, long cliente) {
        Vendas vendas = new Vendas();

        vendas.setDescricaoProdutoV(DescricaoProdutoV);
        vendas.setNomecliente(Nomecliente);
        vendas.setCliente(cliente);
        vendas.setData(Data);

        long id =tabelaVendas.insert(vendas.getContentValues());
        assertNotEquals(-1,id);


        return id;

    }

    private Cursor getVenda(BDVendas tabelaVendas) {
        return tabelaVendas.query(BDVendas.TODAS_COLUNAS,null,null ,null, null, null);
    }


    private Produtos getProdutoComID(Cursor cursor, long idEmPo) {
        Produtos produto=null;
        while (cursor.moveToNext()){
            produto= Produtos.fromCursor(cursor);
            if ( produto.getId()==idEmPo){
                break;
            }
        }
        assertNotNull(produto);

        return produto;
    }

    private long criaProduto(BDProduto tabelaProduto, String nome ,int quilos) {
        Produtos produto = new Produtos();
        produto.setProdutoestoque(nome);
        produto.setQuantidade(quilos);

        long id =tabelaProduto.insert(produto.getContentValues());
        assertNotEquals(-1,id);

        return id;
    }

    private Cursor getProduto(BDProduto tabelaProduto) {
        return tabelaProduto.query(BDProduto.TODAS_COLUNAS,null,null,null,null,null);
    }

}