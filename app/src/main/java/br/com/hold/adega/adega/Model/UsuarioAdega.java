package br.com.hold.adega.adega.Model;

public class UsuarioAdega {

    private String CEP;
    private String CNPJ;
    private Boolean aberto;
    private String endereco;
    private String horarioAberto;
    private String horarioFechado;
    private String nome;
    private String razao;
    private String numero;
    private Integer raioDeEntrega;
    private String telefone1;
    private String telefonen2;

    public String getCEP() {
        return CEP;
    }
    public void setCEP(String cEP) {
        CEP = cEP;
    }
    public String getCNPJ() {
        return CNPJ;
    }
    public void setCNPJ(String cNPJ) {
        CNPJ = cNPJ;
    }
    public Boolean getAberto() {
        return aberto;
    }
    public void setAberto(Boolean aberto) {
        this.aberto = aberto;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getHorarioAberto() {
        return horarioAberto;
    }
    public void setHorarioAberto(String horarioAberto) {
        this.horarioAberto = horarioAberto;
    }
    public String getHorarioFechado() {
        return horarioFechado;
    }
    public void setHorarioFechado(String horarioFechado) {
        this.horarioFechado = horarioFechado;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getRazao() {
        return razao;
    }
    public void setRazao(String razao) {
        this.razao = razao;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public Integer getRaioDeEntrega() {
        return raioDeEntrega;
    }
    public void setRaioDeEntrega(Integer raioDeEntrega) {
        this.raioDeEntrega = raioDeEntrega;
    }
    public String getTelefone1() {
        return telefone1;
    }
    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }
    public String getTelefonen2() {
        return telefonen2;
    }
    public void setTelefonen2(String telefonen2) {
        this.telefonen2 = telefonen2;
    }
    public UsuarioAdega(String cEP, String cNPJ, Boolean aberto, String endereco, String horarioAberto,
                        String horarioFechado, String nome,String razao, String numero, Integer raioDeEntrega, String telefone1,
                        String telefonen2) {
        super();
        CEP = cEP;
        CNPJ = cNPJ;
        this.aberto = aberto;
        this.endereco = endereco;
        this.horarioAberto = horarioAberto;
        this.horarioFechado = horarioFechado;
        this.nome = nome;
        this.razao = razao;
        this.numero = numero;
        this.raioDeEntrega = raioDeEntrega;
        this.telefone1 = telefone1;
        this.telefonen2 = telefonen2;
    }
    public UsuarioAdega() {
        super();
    }
    @Override
    public String toString() {
        return "UsuarioAdega [CEP=" + CEP + ", CNPJ=" + CNPJ + ", aberto=" + aberto + ", endereco=" + endereco
                + ", horarioAberto=" + horarioAberto + ", horarioFechado=" + horarioFechado + ", nome=" + nome +",razao" + razao
                + ", numero=" + numero + ", raioDeEntrega=" + raioDeEntrega + ", telefone1=" + telefone1
                + ", telefonen2=" + telefonen2 + "]";
    }

}
