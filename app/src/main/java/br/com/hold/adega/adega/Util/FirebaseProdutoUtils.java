package br.com.hold.adega.adega.Util;

import android.support.annotation.NonNull;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Interfaces.CRUDInterface;
import br.com.hold.adega.adega.Model.Produto;

public class FirebaseProdutoUtils implements CRUDInterface<Produto> {

    private static Produto produto = new Produto();
    private static List<Produto> produtos = new ArrayList<Produto>();

    @Override
    public void create(Produto produto) {
        FirebaseConfig.getFirebase().setValue(produto);
    }

    @Override
    public Produto read(String key) {

        FirebaseConfig.getFirebase().child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produto = dataSnapshot.getValue(Produto.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return produto;
    }

    @Override
    public List<Produto> readAll() {

        System.out.println("Usando o metodo readAll de Produtos!!!");

        FirebaseChildsUtils.getProdutos().addValueEventListener(new ValueEventListener() {
            private List<Produto> list = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap:dataSnapshot.getChildren()){
                    Produto p = snap.getValue(Produto.class);
                    this.list.add(p);
                }

                for(Produto p : produtos){
                    System.out.println("--------");
                    System.out.println(p.toString());
                    System.out.println("--------");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return produtos;
    }

    @Override
    public void update(Produto produto) {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = mapper.convertValue(produto , new TypeReference<Map<String, Object>>() {
        });

        FirebaseConfig.getFirebase().updateChildren(map);
    }

    @Override
    public void delete(Produto produto) {
        FirebaseConfig.getFirebase().child(produto.getNome()).removeValue();
    }
}
