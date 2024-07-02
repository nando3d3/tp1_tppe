public class Cliente {
    private int id;
    private String nome;
    private Endereco endereco;
    private String tipoCliente;
    private String cpf_cnpj;

    public Cliente(int id, String cpf_cnpj, String nome, Endereco endereco, String tipoCliente){
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public String getCpfCnpj() {
        return cpf_cnpj;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public void setCpfCnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }
}
