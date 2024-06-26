public class Cliente {
    private String id;
    private String nome;
    private String endereco;
    private boolean isCapital;
    private String tipoCliente;

    public Cliente(String id, String nome, String endereco, boolean isCapital, String tipoCliente){
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.isCapital = isCapital;
        this.tipoCliente = tipoCliente;
    }

    public String getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getEndereco(){
        return endereco;
    }

    public boolean getCapital(){
        return isCapital;
    }

    public String getTipoCliente(){
        return tipoCliente;
    }
}
