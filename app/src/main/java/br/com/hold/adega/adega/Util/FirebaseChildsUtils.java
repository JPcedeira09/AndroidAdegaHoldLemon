package br.com.hold.adega.adega.Util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import br.com.hold.adega.adega.Config.FirebaseConfig;

/**
 * Casse que abstrai todos os childs do firebase adega.
 */
public class FirebaseChildsUtils {

    private static DatabaseReference referenciaFirebase = null;

    /**
     * Trás o child de pedidos de um UID x que é passado por parametro.
     * path -> /Usuarios/uidx/MeusPedidos/
     * @param uid
     * @return DatabaseReference uma referencia do nosso banco de dados do firebase.
     */
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
            referenciaFirebase = FirebaseConfig.getFirebase().child("Usuarios").child(uid).child("MeusPedidos").push();
        }
        return referenciaFirebase;
    }


    public static DatabaseReference getPedidos() {
        if (referenciaFirebase == null) {
            return FirebaseConfig.getFirebase().child("Adega").child("Pedidos");
        }
        return referenciaFirebase;

    }

    public static DatabaseReference getPedido() {

        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            return FirebaseConfig.getFirebase().child("Adega").child("Pedidos").push();
        }
        return  referenciaFirebase;
    }

    public static DatabaseReference getOPedidoItens(String key) {

        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            return FirebaseConfig.getFirebase().child("Adega").child("Pedidos").child(key).child("Itens");
        }
        return  referenciaFirebase;
    }

    public static DatabaseReference getOPedidoDados(String key) {

        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            return FirebaseConfig.getFirebase().child("Adega").child("Pedidos").child(key).child("DadosCliente");
        }
        return  referenciaFirebase;
    }

    public static DatabaseReference getOPedidoValores(String key) {

        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            return FirebaseConfig.getFirebase().child("Adega").child("Pedidos").child(key).child("ValoresPedido");
        }
        return  referenciaFirebase;
    }

    public static DatabaseReference getHistorico(String uid) {

        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            return FirebaseConfig.getFirebase().child("Usuarios").child(uid).child("HistoricoPedidos").push();
        }
        return  referenciaFirebase;
    }

    public static DatabaseReference getRetornoHistorico(String uid) {

        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            return FirebaseConfig.getFirebase().child("Usuarios").child(uid).child("HistoricoPedidos");
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
            referenciaFirebase = FirebaseConfig.getFirebase().child("Adega").child("Produtos").child(nomeProduto);
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

    public static DatabaseReference getDadosAdega() {

        referenciaFirebase = null;

        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseConfig.getFirebase().child("Adega").child("DadosAdega");
        }
        return referenciaFirebase;

    }

}
