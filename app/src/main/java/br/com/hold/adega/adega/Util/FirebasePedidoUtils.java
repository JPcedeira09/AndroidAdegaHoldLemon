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
import br.com.hold.adega.adega.Model.Pedido;


public class FirebasePedidoUtils implements CRUDInterface<Pedido> {

    private static List<Pedido> pedidos = new ArrayList<Pedido>();
    private static Pedido pedido;

    @Override
    public  void create(Pedido pedido) {
        FirebaseConfig.getFirebase()
                .setValue(pedido);
    }

    @Override
    public Pedido read(String key) {

        FirebaseConfig.getFirebase().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pedido = dataSnapshot.getValue(Pedido.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return pedido;
    }

    @Override
    public List<Pedido> readAll() {
        DatabaseReference childRef = FirebaseConfig.getFirebase().child("Teste");//Trocar para produto

        childRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pedidos.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    pedidos.add(ds.getValue(Pedido.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return pedidos;
    }

    @Override
    public void update(Pedido pedido) {
        //Update De Pedido é diferente está abaixo
    }

    @Override
    /**
     * Usar a referência do pedido, para apagar no child especifico!
     */
    public void delete(Pedido pedido) {

        FirebaseConfig.getFirebase().removeValue();

    }

    public static void updateStatus(Pedido pedido , Boolean statuBool , String statusSting) {

        ObjectMapper mapper = new ObjectMapper();

        pedido.getValoresPedido().setStatusPedido(statusSting);
        pedido.getValoresPedido().setPedidoAceite(statuBool);

        Map<String, Object> map = mapper.convertValue(pedido , new TypeReference<Map<String, Object>>() {
        });

        FirebaseConfig.getFirebase().updateChildren(map);

    }
}
