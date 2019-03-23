package br.com.hold.adega.adega.Model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String email;
    private String nome;
    private String cpf;
    private String cep;
    private String endereco;
    private String numero;
    private String complemento;
    private String celular;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Usuario(String email, String nome, String cpf, String cep, String endereco, String numero,
                   String complemento, String celular) {
        super();
        this.email = email;
        this.nome = nome;
        this.cpf = cpf;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
        this.celular = celular;
    }

    public Usuario() {
        super();
    }

    @Override
    public String toString() {
        return "Usuario [email=" + email + ", nome=" + nome + ", cpf=" + cpf + ", cep=" + cep + ", endereco=" + endereco
                + ", numero=" + numero + ", complemento=" + complemento + ", celular=" + celular +"]";
    }

}
