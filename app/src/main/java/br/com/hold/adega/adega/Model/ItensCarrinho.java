package br.com.hold.adega.adega.Model;

public class ItensCarrinho {

    private String key;
    private Integer qtd;
    private String nome;
    private Double totalItem;

    public ItensCarrinho() {
        super();
    }

    public ItensCarrinho(String key, Integer qtd, String nome, Double totalItem) {
        super();
        this.key = key;
        this.qtd = qtd;
        this.nome = nome;
        this.totalItem = totalItem;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getQtd() {
        return qtd;
    }
    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Double getTotalItem() {
        return totalItem;
    }
    public void setTotalItem(Double totalItem) {
        this.totalItem = totalItem;
    }



    @Override
    public String toString() {
        return "ItensCarrinho{" +
                "key='" + key + '\'' +
                ", qtd=" + qtd +
                ", nome='" + nome + '\'' +
                ", totalItem=" + totalItem +
                '}';
    }
}
