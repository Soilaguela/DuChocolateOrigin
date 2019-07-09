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


public class AdaptadorVendas extends RecyclerView.Adapter<AdaptadorVendas.ViewHolderVenda> {
            private Cursor cursor;
            private Context context;

            public AdaptadorVendas(Context context) {
                this.context = context;
            }

            public void setCursor(Cursor cursor) {
                if (this.cursor != cursor) {
                    this.cursor = cursor;
                    notifyDataSetChanged();
                }
    }


    @NonNull
    @Override
    public ViewHolderVenda onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemvendas = LayoutInflater.from(context).inflate(R.layout.item_vendas, parent, false);

        return new ViewHolderVenda(itemvendas);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderVenda holder, int position) {
        cursor.moveToPosition(position);
        Vendas vendas = Vendas.fromCursor(cursor);
        holder.setVendas (vendas);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;

        return cursor.getCount ();
    }

    public Vendas getVendaSelecionado() {
        if (viewHolderVendaelecionado == null) return null;
        return viewHolderVendaelecionado.vendas;
    }
    private static ViewHolderVenda viewHolderVendaelecionado = null;

    public class ViewHolderVenda extends  RecyclerView.ViewHolder implements  View.OnClickListener {
        public TextView textViewCliente;
        public TextView textViewDAta;
        public TextView textViewDescricaoProduto;

        private Vendas vendas;

        public ViewHolderVenda(@NonNull View itemView) {
            super(itemView);

            textViewCliente = (TextView)itemView.findViewById(R.id.textViewCliente);
            textViewDAta =  (TextView)itemView.findViewById(R.id.textViewDAta);
            textViewDescricaoProduto =  (TextView)itemView.findViewById(R.id.textViewDescricaoProduto);

            itemView.setOnClickListener(this);


        }

        public void setVendas(Vendas vendas) {
            this.vendas = vendas ;
            textViewDAta.setText(String.valueOf(vendas.getData()));
            textViewDescricaoProduto.setText(vendas.getDescricaoProdutoV());
            textViewCliente.setText((vendas.getCliente());

        }

        @Override
        public void onClick(View v) {
           if(viewHolderVendaelecionado!= null){
            viewHolderVendaelecionado.DesSelecioona();
           }
            viewHolderVendaelecionado = this;
            ((MainVendas) context).atualizaOpcoesMenu();
            Seleciona();

        }
        private void DesSelecioona(){
          itemView.setBackgroundResource(android.R.color.white);
        }
        private void Seleciona(){
            itemView.setBackgroundResource(R.color.endcastanho);
        }
    }


}
