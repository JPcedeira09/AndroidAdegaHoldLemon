package br.com.hold.adega.adega.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Activity.TelaPedidos;
import br.com.hold.adega.adega.Adapter.AdapterPedidosRealizados;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Listener.RecyclerItemClickListener;
import br.com.hold.adega.adega.Model.ItensCarrinho;
import br.com.hold.adega.adega.Model.Pedido;
import br.com.hold.adega.adega.Model.Usuario;
import br.com.hold.adega.adega.Model.ValoresPedido;
import br.com.hold.adega.adega.Util.FirebaseChildsUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PedidosRealizadosFragment extends Fragment {

    private DatabaseReference firebaseref;
    private RecyclerView recyclerPedidos;
    private static List<Pedido> listaPedidos = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_pedidos_realizadosragment, container, false);

        //Configuraçoes iniciais
        recyclerPedidos = view.findViewById(R.id.recylerPedidosRealizados);
        firebaseref = FirebaseConfig.getFirebase();

        //Evento de click no recylerView
        recyclerPedidos.addOnItemTouchListener( new RecyclerItemClickListener(getActivity(), recyclerPedidos,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Pedido pedido = listaPedidos.get(position);
                        Intent intent = new Intent(getActivity(), TelaPedidos.class);


                        intent.putExtra("selecionado",pedido);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {


                            DatabaseReference firebaseRef = FirebaseConfig.getFirebase();
                            DatabaseReference  produtoRef = firebaseRef.child("Adega")
                                    .child("Pedidos");

                            produtoRef.removeValue();


                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));

        //Configuraçoes do RecyclerView
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerPedidos.setLayoutManager(layoutManager);
            recyclerPedidos.setHasFixedSize(true);
            final AdapterPedidosRealizados adapterPedidosRealizados = new AdapterPedidosRealizados(listaPedidos,getActivity());
            recyclerPedidos.setAdapter(adapterPedidosRealizados);

        //Recupera dados do Firebase
        FirebaseChildsUtils.getPedidos()
                    .addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            listaPedidos.clear();

                            for (DataSnapshot ds : dataSnapshot.getChildren()){

                                Pedido pedido = new Pedido();

                                final DataSnapshot itensDS = ds.child("Itens");
                                List<ItensCarrinho> itensCarrinhos = new ArrayList<ItensCarrinho>();
                                for(DataSnapshot dsItem : itensDS.getChildren()) {
                                    ItensCarrinho itensCarrinho = dsItem.getValue(ItensCarrinho.class);
                                    itensCarrinhos.add(itensCarrinho);
                                    pedido.setItensCarrinho(itensCarrinhos);
                                    pedido.setKey(ds.child("key").getValue().toString());}



                                    final DataSnapshot valoresPedidoDS = ds.child("ValoresPedido");
                                    ValoresPedido valoresPedido = valoresPedidoDS.getValue(ValoresPedido.class);
                                    pedido.setValoresPedido(valoresPedido);

                                    final DataSnapshot UsuariosDS = ds.child("DadosCliente");
                                    Usuario usuario = UsuariosDS.getValue(Usuario.class);
                                    pedido.setUsuario(usuario);


                                    listaPedidos.add(pedido);


                                    System.out.println(pedido);





                            }

                            adapterPedidosRealizados.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });


        return view;
    }

}
