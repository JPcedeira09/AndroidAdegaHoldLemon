package br.com.hold.adega.adega.Util;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import br.com.hold.adega.adega.Interfaces.CRUDInterface;
import br.com.hold.adega.adega.Model.ValoresPedido;

public class FirebaseValoresPedidoUtils implements CRUDInterface<ValoresPedido> {

    @Override
    public void create(ValoresPedido valoresPedido) {
    }

    public void createValoresPedido(ValoresPedido valoresPedido,String uid) {
        DatabaseReference childValoresPedido = FirebaseChildsUtils.getValoresPedido(uid);

        System.out.println("--------------------------");
        System.out.println("--------------------------");
        System.out.println(uid);
        System.out.println("Valores Pedido inserido no Firebase foi:"+valoresPedido.toString());
        System.out.println("Valores Pedido inserido no child:"+childValoresPedido.toString());
        System.out.println("--------------------------");
        System.out.println("--------------------------");

        childValoresPedido.setValue(valoresPedido);
    }

    @Override
    public ValoresPedido read(String key) {
        return null;
    }

    @Override
    public List<ValoresPedido> readAll() {
        return null;
    }

    @Override
    public void update(ValoresPedido classeVelha) {

    }

    @Override
    public void delete(ValoresPedido classe) {

    }
}
