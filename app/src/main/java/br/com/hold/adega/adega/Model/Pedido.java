package br.com.hold.adega.adega.Model;


import android.os.Parcel;
import android.os.Parcelable;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Pedido implements Serializable {



    private String key;
    private ValoresPedido valoresPedido;
    private Usuario usuario;
    private List<ItensCarrinho> itensCarrinho;

    public Pedido(String key, ValoresPedido valoresPedido, Usuario usuario, List<ItensCarrinho> itensCarrinho) {
        super();
        this.key = key;
        this.valoresPedido = valoresPedido;
        this.usuario = usuario;
        this.itensCarrinho = itensCarrinho;
    }

    public Pedido() {
        super();
    }




    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public ValoresPedido getValoresPedido() {
        return valoresPedido;
    }
    public void setValoresPedido(ValoresPedido valoresPedido) {
        this.valoresPedido = valoresPedido;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public List<ItensCarrinho> getItensCarrinho() {
        return itensCarrinho;
    }
    public void setItensCarrinho(List<ItensCarrinho> itensCarrinho) {
        this.itensCarrinho = itensCarrinho;
    }

    @Override
    public String toString() {
        return "Pedido [key=" + key + ", valoresPedido=" + valoresPedido + ", usuario=" + usuario + ", itensCarrinho="
                + itensCarrinho + "]";
    }


}
