import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaCadastro sistema = new SistemaCadastro();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Sistema de Cadastro ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Cadastrar Produto");
            System.out.println("3. Cadastrar Venda");
            System.out.println("4. Listar Clientes");
            System.out.println("5. Listar Produtos");
            System.out.println("6. Listar Vendas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Consome a nova linha

            switch (opcao) {
                case 1:
                    // Cadastrar Cliente
                    sistema.cadastrarCliente();
                    break;

                case 2:
                    // Cadastrar Produto
                    sistema.cadastrarProduto();
                    break;

                case 3:
                    // Cadastrar Venda
                    sistema.cadastrarVenda();
                    break;

                case 4:
                    // Listar Clientes
                    System.out.println("\n--- Lista de Clientes ---");
                    for (Cliente cliente : sistema.getClientes()) {
                        System.out.println("ID: " + cliente.getId() + " | Nome: " + cliente.getNome() + " | CPF/CNPJ: " + cliente.getCpfCnpj() + " | Estado: " + cliente.getEndereco().getSiglaEstado() + " | Capital: " + cliente.getEndereco().isCapital() + " | Tipo: " + cliente.getTipoCliente() + " | Cashback: " + cliente.getCashBack());
                    }
                    break;

                case 5:
                    // Listar Produtos
                    System.out.println("\n--- Lista de Produtos ---");
                    for (Produto produto : sistema.getProdutos()) {
                        System.out.println("Código: " + produto.getCodigoItem() + " | Descrição: " + produto.getDescricao() + " | Valor: " + produto.getValorVenda() + " | Unidade: " + produto.getUnidade());
                    }
                    break;

                case 6:
                    // Listar Vendas
                    System.out.println("\n--- Lista de Vendas ---");
                    for (Venda venda : sistema.getVendas()) {
                        System.out.println("Data: " + venda.getData() + " | Cliente: " + venda.getCliente().getNome() + " | Método de Pagamento: " + venda.getMetodoPagamento() + " | Total: " + venda.calculaTotalNota() + " | Usar Cashback: " + venda.isUsarCashBack());
                        for (ItemVenda item : venda.getItens()) {
                            System.out.println("  Produto: " + item.getProduto().getDescricao() + " | Quantidade: " + item.getQuantidade() + " | Valor Unitário: " + item.getProduto().getValorVenda() + " | Imposto ICMS: " + item.getValorImpostoICMS() + " | Imposto Municipal: " + item.getValorImpostoMunicipal());
                        }
                    }
                    break;

                case 0:
                    // Sair
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
