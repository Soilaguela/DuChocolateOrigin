package com.example.duchocolate;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorProdutos extends RecyclerView.Adapter<AdaptadorProdutos.ViewHolderProduto> {
    private Cursor cursor;
    private Context context;

    public AdaptadorProdutos (MainProdutos context){
        this.context= context;
    }
    public void setCursor(Cursor cursor){
        if (this.cursor!= cursor){
            this.cursor= cursor;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public AdaptadorProdutos.ViewHolderProduto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemProduto = LayoutInflater.from(context).inflate(R.layout.item_produto, parent,false);

        return new  ViewHolderProduto(itemProduto);
    }


    @Override
    public void onBindViewHolder(@NonNull AdaptadorProdutos.ViewHolderProduto holder, int position) {
        cursor.moveToPosition(position);
        Produtos produtos= Produtos.fromCursor(cursor);
        holder.setProdutos(produtos);

    }

    @Override
    public int getItemCount() {
       if (cursor==null)return 0;
       return  cursor.getCount();
    }
    public Produtos getProdutoSelecionado() {
        if (viewHolderprodutoSelecionado == null) return null;

        return viewHolderprodutoSelecionado.produtos;
    }

    public class ViewHolderProduto extends RecyclerView.ViewHolder implements  View.OnClickListener {
        private TextView textViewQuantidade;
        private TextView textViewProdutoestoque;

        private Produtos produtos;

        public ViewHolderProduto(@NonNull View itemView) {
            super(itemView);

            textViewProdutoestoque=(TextView)itemView.findViewById(R.id.textViewProdutoestoque);
            textViewQuantidade= (TextView)itemView.findViewById(R.id.textViewQuantidade);
            itemView.setOnClickListener(this);

        }
        public void setProdutos(Produtos produtos){
            this.produtos= produtos;
            textViewProdutoestoque.setText(produtos.getProdutoestoque());
            textViewQuantidade.setText(String.valueOf( produtos.getQuantidade()));
        }

        @Override
        public void onClick(View v) {
            if (viewHolderprodutoSelecionado!=null){
                viewHolderprodutoSelecionado.DesSeleciona();

            }

            viewHolderprodutoSelecionado= this;
            ((MainProdutos) context).atualizaOpcoesMenu();
            Seleciona();
        }
        private void DesSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }

        private void Seleciona() { itemView.setBackgroundResource(R.color.endcastanho);}
    }
    private static ViewHolderProduto viewHolderprodutoSelecionado=null;
}
