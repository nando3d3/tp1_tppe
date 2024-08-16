public abstract class Cliente {
    private int id;
    private String nome;
    private Endereco endereco;
    private String cpf_cnpj;

    /*
    A refatoracao aborda o mau-cheiro "Classe Grande". Nela, a classe Cliente foi 
    subdividida em ClientePadrao, ClienteEspecial e ClientePrime, cada uma 
    extraindo os comportamentos e responsabilidades especificas de cada cliente. 
    Entretando, as classes ainda não forem mais especificadas no trabalho, o que 
    pode gerar outro mau-cheiro no futuro, o de "Classe Preguicosa".

    Essa refatoracao melhorou a modularidade/clareza do codigo, alem de facilitar 
    a manutencao e adicao de novos tipos de clientes. Portanto, a separacao das 
    responsabilidades nas subclasses tambem tornou o codigo mais testavel ao 
    isolar a logica de cada tipo de cliente em suas respectivas subclasses.
    */

    public Cliente(int id, String cpf_cnpj, String nome, Endereco endereco) {
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.nome = nome;
        this.endereco = endereco;
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

    public void setCpfCnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }
}