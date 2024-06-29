import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaCadastro {
    private List<Cliente> clientes;
    private List<Produto> produtos;
    private List<Venda> vendas;
    private Scanner scanner;

    private int idCounter;

    public SistemaCadastro() {
        this.clientes = new ArrayList<>();
        this.produtos = new ArrayList<>();
        this.vendas = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.idCounter = 1;
    }

    public void cadastrarCliente() {
        System.out.println("Digite o CPF ou CNPJ do cliente (números apenas):");
        String cpf_cnpj = scanner.nextLine();

        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();

        System.out.println("Digite a sigla do estado do endereço:");
        String estado = scanner.nextLine().toUpperCase();

        boolean isCapital = false;
        while (true) {
            System.out.println("A cidade é capital? (s/n)");
            String cidade = scanner.nextLine().toLowerCase();
            if (cidade.equals("s")) {
                isCapital = true;
                break;
            } else if (cidade.equals("n")) {
                isCapital = false;
                break;
            } else {
                System.out.println("Resposta inválida. Digite 's' para sim ou 'n' para não.");
            }
        }

        String tipoCliente = "";
        while (true) {
            System.out.println("O cliente é prime? (s/n)");
            String respTipoCliente = scanner.nextLine().toLowerCase();
            if (respTipoCliente.equals("s")) {
                tipoCliente = "prime";
                break;
            } else if (respTipoCliente.equals("n")) {
                tipoCliente = "padrao";
                break;
            } else {
                System.out.println("Resposta inválida. Digite 's' para sim ou 'n' para não.");
            }
        }

        Endereco endereco = new Endereco(estado, isCapital);
        Cliente cliente = new Cliente(idCounter, cpf_cnpj, nome, endereco, tipoCliente);
        clientes.add(cliente);
        idCounter++;

        System.out.println("Cliente cadastrado com sucesso!");
    }

    //cadastrar produto e venda aqui...
    public void cadastrarProduto(){

    }

    public void cadastrarVenda(){
        
    }

    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
