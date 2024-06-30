import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class App 
{
    private static int idClientes = 0;

    private static List<Cliente>    clientes    = new ArrayList<>();
    private static List<Produto>    produtos    = new ArrayList<>();
    private static List<Venda>      vendas      = new ArrayList<>();
    public static void main( String[] args )
    {
        Scanner scanner     = new Scanner(System.in);
        while(true){
            System.err.println("+-------------------------------------+");
            System.err.println("|       Sistema de Cadastro           |");
            System.err.println("+-------------------------------------+");
            System.err.println("|   1 - Cadastra Cliente              |");
            System.err.println("|   2 - Cadastra Produto              |");
            System.err.println("|   3 - Realizar Venda                |");
            System.err.println("|   4 - Calcular Vendas do Ultimo Mes |");
            System.err.println("|   5 - Calcular Saldo de Cashback    |");
            System.err.println("|   6 - Sair                          |");
            System.err.println("+-------------------------------------+");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarCliente(scanner);
                    break;
                case 2:
                    cadastrarProduto(scanner);
                    break;
                case 3:
                    realizarVenda(scanner);
                    break;
                case 4:
                    System.out.println("Digite cpf/cnpj do cliente: ");
                    String identificadorCliente = scanner.nextLine();
                    calcularVendasUltimoMes(identificadorCliente);
                    break;
                case 5:
                    calcularSaldoCashback();
                    break;
                case 6:
                    scanner.close();
                    return;
                default:
                    System.err.println("Erro! Selecione uma opcao de 1 a 6.");
            }
        }
    }

    private static void cadastrarCliente(Scanner scanner) {
        System.out.println("Digite o cpf/cnpj do cliente: ");
        String cpf_cnpj = scanner.nextLine();

        System.out.println("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();

        System.out.println("Digite a sigla do estado: (Ex: DF, GO)");
        String estadoCliente = scanner.nextLine().toUpperCase();
        boolean isCapital = true;
        while (true) {
            System.out.println("Eh capital? (s/n)");
            String capital = scanner.nextLine().toLowerCase();
            if (capital.equals("s")) {
                isCapital = true;
                break;
            } else if (capital.equals("n")) {
                isCapital = false;
                break;
            } else {
                System.err.println("Erro! Digite 's' para sim ou 'n' para nao.");
            }
        }
        Endereco enderecoCliente = new Endereco(estadoCliente, isCapital);

        String tipoCliente = "";
        while (true) {
            System.out.println("Qual o tipo do cliente?");
            System.out.println("( 1 - Padrao; 2 - Especial; 3 - Prime)");
            System.out.println("");
            int tipo = scanner.nextInt();
            scanner.nextLine();
            switch (tipo) {
                case 1:
                    tipoCliente = "padrao";
                    break;
                case 2:
                    tipoCliente = "especial";
                    break;
                case 3:
                    tipoCliente = "prime";
                    break;
                default:
                    System.err.println("Erro! Digite 1, 2 ou 3.");
                    continue;
            }
            break;
        }

        Cliente novoCliente = new Cliente(idClientes, cpf_cnpj, nomeCliente, enderecoCliente, tipoCliente);
        clientes.add(novoCliente);
        ++idClientes;

        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void cadastrarProduto(Scanner scanner) {
        System.out.println("Digite o codigo do produto: ");
        String codigoItem = scanner.nextLine();

        System.out.println("Adicione a descricao: ");
        String descricao = scanner.nextLine();

        System.out.println("Digite o valor de venda: ");
        double valorVenda = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Digite a unidade: ");
        String unidade = scanner.nextLine();

        produtos.add(new Produto(codigoItem, descricao, valorVenda, unidade));

        System.out.println("Produto cadastrado com sucesso!");

    }

    private static void realizarVenda(Scanner scanner) {
        List<ItemVenda> itens = new ArrayList<>();
        while (true) {
            System.out.println("Digite o codigo do produto: ");
            String codNovoProduto = scanner.nextLine();
            Produto produto = produtos.stream()
                            .filter(p -> p.getCodigoItem().equals(codNovoProduto))
                            .findFirst().orElse(null);
            
            if (produto == null) {
                System.err.println("Erro! Produto nao encontrado!");
                return;
            }

            System.out.println("Digite a quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            itens.add(new ItemVenda(produto, quantidade));

            System.out.println("Deseja comprar mais? s/n");
            String endVenda = scanner.nextLine();
            if (endVenda.equalsIgnoreCase("n")) {break;}
        }

        System.out.println("Digite o cpf/cnpj do cliente: ");
        String idCliente = scanner.nextLine();
        Cliente consumidor = clientes.stream().filter(c -> c.getCpfCnpj().equals(idCliente))
                            .findFirst()
                            .orElse(null);

        if (consumidor == null) {
            System.err.println("Erro! Cliente nao encontrado!");
            return;
        }

        System.out.println("Qual a fomra de pagamento: ");
        String metodoPagamento = scanner.nextLine();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataVenda = LocalDate.now();
        String dataFormatada = dataVenda.format(dateFormatter);

        Venda venda = new Venda(dataFormatada, consumidor, metodoPagamento);
        vendas.add(venda);

        System.out.println("Venda realizada com sucesso!");
        System.out.println("valorTotal: " + venda.getTotal() );
        System.out.println();
    }

    private static String calcularVendasUltimoMes(String identificador) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date mesAnterior = cal.getTime();

        double totalVendas = 0.0;
        for (Cliente cliente: clientes) {
            if (cliente.getCpfCnpj().equalsIgnoreCase(identificador)){
                totalVendas = vendas.stream()
                        .filter(v -> {
                            try {
                                Date dataVenda = dateFormatter.parse(v.getData());
                                return dataVenda.after(mesAnterior);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        })
                        .filter(v -> v.getCliente().equals(cliente))
                        .mapToDouble(Venda::calculaTotalNota)
                        .sum();

                if (totalVendas > 100.0) {
                    System.out.println("Cliente " + 
                        cliente.getNome() + 
                        " com cpf/cnpj: " + 
                        cliente.getCpfCnpj() + 
                        ", voce esta elegivel para se tornar cliente especial.");
                    return cliente.getCpfCnpj();
                }
            }
        }
        return "";
    }

    private static void calcularSaldoCashback() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcularSaldoCashback'");
    }
    
}
