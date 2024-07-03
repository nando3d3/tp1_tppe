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
    public void cadastrarProduto() {
        System.out.println("Digite o codigo do produto: ");
        String codigoItem = scanner.nextLine();

        System.out.println("Adicione a descricao: ");
        String descricao = scanner.nextLine();

        System.out.println("Digite o valor de venda: ");
        double valorVenda = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Digite a unidade: ");
        String unidade = scanner.nextLine();
        Produto produto = new Produto(codigoItem, descricao, valorVenda, unidade);
        produtos.add(produto);

        System.out.println("Produto cadastrado com sucesso!");

    }

    public void cadastrarVenda() {

        System.out.println("Clientes disponíveis:");
        for (Cliente cliente : clientes) {
            System.out.println("ID: " + cliente.getId() + " | Nome: " + cliente.getNome() + " | CPF/CNPJ: " + cliente.getCpfCnpj());
        }
    
        System.out.println("Digite o ID do cliente para a venda:");
        int clienteId = scanner.nextInt();
        scanner.nextLine(); 
    
        Cliente cliente = null;
        for (Cliente c : clientes) {
            if (c.getId() == clienteId) {
                cliente = c;
                break;
            }
        }
    
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }
    

        System.out.println("Produtos disponíveis:");
        for (Produto produto : produtos) {
            System.out.println("Código: " + produto.getCodigoItem() + " | Descrição: " + produto.getDescricao() + " | Valor: " + produto.getValorVenda() + " | Unidade: " + produto.getUnidade());
        }
    
        List<Produto> produtosSelecionados = new ArrayList<>();
        List<Integer> quantidades = new ArrayList<>();
    
        while (true) {
            System.out.println("Digite o código do produto ou 'fim' para finalizar:");
            String codigoProduto = scanner.nextLine();
    
            if (codigoProduto.equalsIgnoreCase("fim")) {
                break;
            }
    
            Produto produto = null;
            for (Produto p : produtos) {
                if (p.getCodigoItem().equals(codigoProduto)) {
                    produto = p;
                    break;
                }
            }
    
            if (produto == null) {
                System.out.println("Produto não encontrado!");
                continue;
            }
    
            System.out.println("Digite a quantidade do produto:");
            int quantidade = scanner.nextInt();
            scanner.nextLine(); 
    
            if (quantidade <= 0) {
                System.out.println("Quantidade deve ser maior que zero.");
                continue;
            }
    
            produtosSelecionados.add(produto);
            quantidades.add(quantidade);
    
            System.out.println("Produto adicionado com sucesso!");
        }
    
        if (produtosSelecionados.isEmpty()) {
            System.out.println("Nenhum produto foi adicionado à venda.");
            return;
        }
    

        String dataAtual;
        while (true) {
            System.out.println("Digite a data da venda no formato yyyy-MM-dd:");
            dataAtual = scanner.nextLine();
            if (dataAtual.matches("\\d{4}-\\d{2}-\\d{2}")) {
                break;
            } else {
                System.out.println("Data inválida. Por favor, use o formato yyyy-MM-dd.");
            }
        }
    
        System.out.println("Digite o número do método de pagamento:");
        System.out.println("1 - Dinheiro\n2 - Cartão da Loja\n3 - Cartão da Empresa");
        int numMetodoPagamento = scanner.nextInt();
        scanner.nextLine(); 
    
        String metodoPagamento;
        switch (numMetodoPagamento) {
            case 1:
                metodoPagamento = "dinheiro";
                break;
            case 2:
                metodoPagamento = "cartao_loja";
                break;
            case 3:
                metodoPagamento = "cartao_empresa";
                break;
            default:
                System.out.println("Método de pagamento inválido.");
                return;
        }
    
        boolean usarCashback = false;
        if (cliente.getTipoCliente().equals("prime")) {
            System.out.println("Deseja usar cashback? (s/n):");
            String respostaCashback = scanner.nextLine().toLowerCase();
            if (respostaCashback.equals("s")) {
                usarCashback = true;
            }
        }
    
        Venda venda = new Venda(dataAtual, cliente, metodoPagamento, usarCashback);
    
        for (int i = 0; i < produtosSelecionados.size(); i++) {
            venda.addItem(produtosSelecionados.get(i), quantidades.get(i));
        }
    
        double totalNota = venda.calculaTotalNota();
        System.out.println("Valor total da nota: " + totalNota);
    
        vendas.add(venda);
        idCounter++;
    
        System.out.println("Venda registrada com sucesso!");
        System.out.println("ID da venda: " + idCounter);
        System.out.println("Valor total da venda: " + totalNota);
    }

    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Produto> getProdutos(){
        return produtos;
    }

    public List<Venda> getVendas(){
        return vendas;
    }
}
