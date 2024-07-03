public class Cliente {
    private int id;
    private String nome;
    private Endereco endereco;
    private String tipoCliente;
    private String cpf_cnpj;
    private double cash_back;

    public Cliente(int id, String cpf_cnpj, String nome, Endereco endereco, String tipoCliente){
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
        this.cash_back = 0.00;
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

    public double getCashBack(){
        return cash_back;
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

    public void setCashBack(double cash_back) {
        this.cash_back = cash_back;
    }
}
