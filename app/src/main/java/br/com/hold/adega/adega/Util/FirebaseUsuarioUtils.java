package br.com.hold.adega.adega.Util;

import android.support.annotation.NonNull;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Interfaces.CRUDInterface;
import br.com.hold.adega.adega.Model.Usuario;

public class FirebaseUsuarioUtils implements CRUDInterface<Usuario> {

    Usuario usuario = new Usuario();

    @Override
    public void create(Usuario usuario) {
        DatabaseReference childUsuario = FirebaseChildsUtils.getUsuario("1111");
        childUsuario.setValue(usuario);
    }

    public void createUsuario(Usuario usuario,String uid) {
        DatabaseReference childUsuario = FirebaseChildsUtils.getUsuario(uid);



        childUsuario.setValue(usuario);
    }

    @Override
    public Usuario read(String uid) {

        DatabaseReference usuario = FirebaseChildsUtils.getUsuario(uid);
        usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUsuarioUtils.this.usuario = dataSnapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return this.usuario;
    }

    @Override
    public List<Usuario> readAll() {
//        FirebaseConfig.getFirebase().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snap : dataSnapshot.getChildren()){
//                    Usuario u = snap.getValue(Usuario.class);
//                    usuarios.add(u);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        return usuarios;
        return null;
    }

    @Override
    public void update(Usuario usuario) {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = mapper.convertValue(usuario , new TypeReference<Map<String, Object>>() {
        });

        FirebaseConfig.getFirebase().updateChildren(map);
    }

    @Override
    public void delete(Usuario usuario) {
        FirebaseConfig.getFirebase().removeValue();
    }

    public static String getUID(FirebaseAuth auth){
       return auth.getCurrentUser().getUid();
    }
}
