package br.com.hold.adega.adega.Fragment;


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
public class PedidosRealizadosragment extends Fragment {

    private DatabaseReference firebaseref;
    private RecyclerView recyclerPedidos;
    private List<Pedido> listaPedidos;


    public PedidosRealizadosragment() {
        // Required empty public constructor
    }


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
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));


        //listaPedidos = PedidoFirebase.listAll();

        //Recupera dados do Firebase

        if (listaPedidos == null) {
            listaPedidos = new ArrayList<>();

            //Configuraçoes do RecyclerView
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerPedidos.setLayoutManager(layoutManager);
            recyclerPedidos.setHasFixedSize(true);
            final AdapterPedidosRealizados adapterPedidosRealizados = new AdapterPedidosRealizados(listaPedidos,getActivity());
            recyclerPedidos.setAdapter(adapterPedidosRealizados);

            FirebaseChildsUtils.getPedidos()
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            listaPedidos.clear();

                            for (DataSnapshot ds : dataSnapshot.getChildren()){

                                Pedido pedido = new Pedido();

                                final DataSnapshot itensDS = ds.child("Itens");
                                List<ItensCarrinho> itensCarrinhos = new ArrayList<ItensCarrinho>();
                                for(DataSnapshot dsItem : itensDS.getChildren()){
                                    ItensCarrinho itensCarrinho = dsItem.getValue(ItensCarrinho.class);
                                    itensCarrinhos.add(itensCarrinho);

                                    System.out.println(ds);
                                    System.out.println(itensCarrinhos);

                                }
                                pedido.setItensCarrinho(itensCarrinhos);
                                final DataSnapshot valoresPedidoDS = ds.child("ValoresPedido");
                                ValoresPedido valoresPedido = valoresPedidoDS.getValue(ValoresPedido.class);
                                pedido.setValoresPedido(valoresPedido);
                                System.out.println(ds);
                                System.out.println(valoresPedido);

                                final DataSnapshot UsuariosDS = ds.child("DadosCliente");
                                Usuario usuario = UsuariosDS.getValue(Usuario.class);
                                pedido.setUsuario(usuario);
                                System.out.println(ds);
                                System.out.println(usuario);


                                listaPedidos.add(pedido);
                            }

                            adapterPedidosRealizados.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

        }

        return view;
    }

}
