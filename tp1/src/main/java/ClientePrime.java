public class ClientePrime extends Cliente {
    private double cash_back;

    public ClientePrime(int id, String cpf_cnpj, String nome, Endereco endereco) {
        super(id, cpf_cnpj, nome, endereco);
        this.cash_back = 0.00;
    }

    public double getCashBack() {
        return cash_back;
    }

    public void setCashBack(double cash_back) {
        this.cash_back = cash_back;
    }
}