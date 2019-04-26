package br.com.hold.adega.adega.Model;

import android.widget.ImageView;

import java.io.Serializable;

public class Produto implements Serializable {

    private String nome;
    private String descricao;
    private Integer quantidade;
    private Double valor;
    private Boolean disponivel;
    private String imagemProduto;


    public String getImagemProduto() {
        return imagemProduto;
    }

    public void setImagemProduto(String imagemProduto) {
        this.imagemProduto = imagemProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Produto(String nome, String descricao, Integer quantidade, Double valor, Boolean disponivel) {
        super();
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valor = valor;
        this.disponivel = disponivel;
    }

    public Produto() {
        super();
    }

    @Override
    public String toString() {
        return "Produto [nome=" + nome + ", descricao=" + descricao + ", quantidade=" + quantidade + ", valor="
                + valor + ", disponivel=" + disponivel + "]";
    }


}

