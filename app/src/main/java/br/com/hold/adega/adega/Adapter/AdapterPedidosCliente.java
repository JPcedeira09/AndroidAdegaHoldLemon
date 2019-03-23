package br.com.hold.adega.adega.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Model.ValoresPedido;

public class AdapterPedidosCliente extends RecyclerView.Adapter<AdapterPedidosCliente.MyViewHolder> {


    private List<ValoresPedido> pedidos;
    private Context context;

    public AdapterPedidosCliente(List<ValoresPedido> pedidos, Context context) {
        this.pedidos = pedidos;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterPedidosCliente.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_historico_pedido, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPedidosCliente.MyViewHolder holder, int i) {
        ValoresPedido pedido = pedidos.get(i);
        holder.statusPedido.setText(pedido.getStatusPedido());
        holder.valorPedido.setText("R$ " + pedido.getValorTotalProduto());
        holder.data.setText(pedido.getDataPedido());

    }

    @Override
    public int getItemCount() {

        return pedidos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView numeroPedido;
        TextView valorPedido;
        TextView statusPedido;
        TextView data;


        public MyViewHolder(View itemView) {
            super(itemView);

            numeroPedido = itemView.findViewById(R.id.textNumeroPedido);
            valorPedido = itemView.findViewById(R.id.textValorPedido);
            statusPedido = itemView.findViewById(R.id.textStatusPedido);
            data = itemView.findViewById(R.id.dataPedido);

        }
    }
}