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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Activity.EstoqueTelaProduto;
import br.com.hold.adega.adega.Activity.MenuCliente;
import br.com.hold.adega.adega.Activity.TelaProduto;
import br.com.hold.adega.adega.Adapter.AdapterEstoque;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Listener.RecyclerItemClickListener;
import br.com.hold.adega.adega.Model.Produto;
import br.com.hold.adega.adega.Util.FirebaseChildsUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstoqueFragment extends Fragment {
    private DatabaseReference firebaseRef;
    private RecyclerView recyclerProdutos;
    private List<Produto> produtos = new ArrayList<>();


    public EstoqueFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View view = inflater.inflate(R.layout.fragment_estoque, container, false);

        recyclerProdutos = view.findViewById(R.id.recyclerProdutos);
        firebaseRef = FirebaseConfig.getFirebase();


        //Configurar o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerProdutos.setLayoutManager(layoutManager);
        recyclerProdutos.setHasFixedSize(true);
        final AdapterEstoque adapterEstoque = new AdapterEstoque(produtos,getActivity());
        recyclerProdutos.setAdapter(adapterEstoque);

        //Adiciona evento de clique no Recyclcerview
        recyclerProdutos.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerProdutos,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Produto p = produtos.get(position);
                        Intent intent = new Intent(getActivity(), EstoqueTelaProduto.class);
                        intent.putExtra("selecionado", p);
                        startActivity(intent);


                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
//                        Produto produtoSelecionado = produtos.get(position);
//                        produtoSelecionado.remover();
//                        Toast.makeText(getActivity(), "Produto excluido com sucesso ", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));


        //Recupera os dados do Firebase
        FirebaseChildsUtils.getProdutos()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        produtos.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            produtos.add(ds.getValue(Produto.class));


                        }
                        adapterEstoque.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        return view;
    }

}
