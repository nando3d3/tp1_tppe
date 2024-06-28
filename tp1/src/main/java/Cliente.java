public class Cliente {
    private String id;
    private String nome;
    private Endereco endereco;
    private String tipoCliente;

    public Cliente(String id, String nome, Endereco endereco, String tipoCliente){
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
    }

    public String getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public Endereco getEndereco(){
        return endereco;
    }

    public String getTipoCliente(){
        return tipoCliente;
    }
}
