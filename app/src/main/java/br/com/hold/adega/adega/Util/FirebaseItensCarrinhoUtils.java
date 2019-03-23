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
import br.com.hold.adega.adega.Model.ItensCarrinho;

public class FirebaseItensCarrinhoUtils implements CRUDInterface<ItensCarrinho> {

    ItensCarrinho itensCarrinho = new ItensCarrinho();
    List<ItensCarrinho> carrinho = new ArrayList<ItensCarrinho>();

    @Override
    public void create(ItensCarrinho itensCarrinho) {
        FirebaseConfig.getFirebase().setValue(itensCarrinho);
    }

    @Override
    public ItensCarrinho read(String key) {

        FirebaseConfig.getFirebase().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itensCarrinho = dataSnapshot.getValue(ItensCarrinho.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return itensCarrinho;
    }

    @Override
    public List<ItensCarrinho> readAll() {
        FirebaseConfig.getFirebase().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    ItensCarrinho item = dataSnapshot.getValue(ItensCarrinho.class);
                    carrinho.add(item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return carrinho;
    }

    @Override
    public void update(ItensCarrinho itensCarrinho) {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = mapper.convertValue(itensCarrinho , new TypeReference<Map<String, Object>>() {
        });

        FirebaseConfig.getFirebase().updateChildren(map);
    }

    @Override
    public void delete(ItensCarrinho itensCarrinho) {
        FirebaseConfig.getFirebase().removeValue();
    }
}
