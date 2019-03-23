package br.com.hold.adega.adega.Util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.hold.adega.adega.Config.FirebaseConfig;

public class FirebaseChildsUtils {

    private static DatabaseReference referenciaFirebase = null;

    public static DatabaseReference getItensCarrinho(String uid) {

        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseConfig.getFirebase().child("Usuarios").child(uid).child("MeusPedidos");
        }
        return referenciaFirebase;
    }

    public static DatabaseReference getItemCarrinho(String uid ) {

        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseConfig.getFirebase().child("Usuarios").child(uid).child("MeusPedidos").child("Produto");
        }
        return referenciaFirebase;
    }


    public static DatabaseReference getPedidos() {
        if (referenciaFirebase == null) {
            return FirebaseConfig.getFirebase().child("Pedidos");
        }
        return referenciaFirebase;

    }

    public static DatabaseReference getPedido(String key) {

        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            return FirebaseConfig.getFirebase().child("Pedidos").child(key);
        }
        return  referenciaFirebase;
    }


    public static DatabaseReference getProdutos() {

        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseConfig.getFirebase().child("Adega").child("Produtos");
        }
        return referenciaFirebase;

    }

    public static DatabaseReference getProduto(String nomeProduto) {
        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseConfig.getFirebase().child("Produtos").child(nomeProduto);
        }
        return referenciaFirebase;
    }

    public static DatabaseReference getUsuario(String uid) {
        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseConfig.getFirebase().child("Usuarios").child(uid).child("DadosPessoais");
        }
        return referenciaFirebase;
    }

    public static DatabaseReference getValoresPedido(String uid) {
        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseConfig.getFirebase().child("Usuarios").child(uid).child("ValoresPedido");
        }
        return referenciaFirebase;
    }

}
