package br.com.hold.adega.adega.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Model.ItensCarrinho;
import br.com.hold.adega.adega.Model.Produto;

public class AdapterCarrinho extends RecyclerView.Adapter<AdapterCarrinho.MyViewHolder> {


    private List<ItensCarrinho> itensCarrinhos;
    private Context context;

    public AdapterCarrinho(List<ItensCarrinho> pp, Context cc) {
        this.itensCarrinhos = pp;
        this.context = cc;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_carrinho, parent, false);
        return new MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterCarrinho.MyViewHolder holder, int position) {


        ItensCarrinho carrinho = itensCarrinhos.get(position);
        holder.nome.setText(carrinho.getQtd()+"X "+carrinho.getNome() );
        holder.valor.setText("R$" + carrinho.getTotalItem());

    }


    @Override
    public int getItemCount() {

        return itensCarrinhos.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView valor;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textNomeProdutoCarrinho);
            valor = itemView.findViewById(R.id.textValorProdutoCarrinho);
        }


    }
}
