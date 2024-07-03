import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class SistemaCadastroTest {
    private String cpf_cnpj;
    private String nome;
    private String estado;
    private boolean isCapital;
    private String tipoCliente;

    private String codigoProduto;
    private String descricaoProduto;
    private double valorVendaProduto;
    private String unidadeProduto;

    private String dataVenda;
    private String metodoPagamento;
    private boolean usarCashback;

    private SistemaCadastro sysCadastro;

    @Before
    public void setup(){
        sysCadastro = new SistemaCadastro();
    }

    public SistemaCadastroTest(String cpf_cnpj, String nome, String estado, boolean isCapital, String tipoCliente,
                               String codigoProduto, String descricaoProduto, double valorVendaProduto, String unidadeProduto,
                               String dataVenda, String metodoPagamento, boolean usarCashback) {
        this.cpf_cnpj = cpf_cnpj;
        this.nome = nome;
        this.estado = estado;
        this.isCapital = isCapital;
        this.tipoCliente = tipoCliente;

        this.codigoProduto = codigoProduto;
        this.descricaoProduto = descricaoProduto;
        this.valorVendaProduto = valorVendaProduto;
        this.unidadeProduto = unidadeProduto;

        this.dataVenda = dataVenda;
        this.metodoPagamento = metodoPagamento;
        this.usarCashback = usarCashback;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"12345678900" , "Pedro Álvares Cabral", "SP", true, "prime",
             "001", "Produto A", 10.0, "unidade",
             "2024-07-01", "cartao_loja", true},
            {"98765432100", "João Terceiro da Lua", "MG", false, "padrao",
             "002", "Produto B", 20.0, "caixa",
             "2024-07-02", "dinheiro", false},
            {"58963510200", "Sidney Fernando", "GO", false, "padrao",
             "003", "Produto C", 30.0, "pacote",
             "2024-07-03", "cartao_empresa", true}
        });
    }

    @Test
    public void testCadastroCliente() {
        String[] inputs = {cpf_cnpj, nome, estado, isCapital ? "s" : "n", tipoCliente.equals("prime") ? "s" : "n"};
        sysCadastro.setScanner(new java.util.Scanner(String.join("\n", inputs) + "\n"));
        
        sysCadastro.cadastrarCliente();

        Cliente cliente = sysCadastro.getClientes().get(sysCadastro.getClientes().size() - 1);
        
        assertNotNull(cliente);
        assertEquals(cpf_cnpj, cliente.getCpfCnpj());
        assertEquals(nome, cliente.getNome());
        assertEquals(estado, cliente.getEndereco().getSiglaEstado());
        assertEquals(isCapital, cliente.getEndereco().isCapital());
        assertEquals(tipoCliente, cliente.getTipoCliente());
    }

    @Test
    public void testCadastroProduto() {
        // Configura o Scanner com a entrada dos dados do produto
        sysCadastro.setScanner(new java.util.Scanner(String.join("\n", codigoProduto, descricaoProduto, 
            String.valueOf(valorVendaProduto), unidadeProduto) + "\n"));

        // Cadastra o produto usando o método de cadastro
        sysCadastro.cadastrarProduto();

        // Obtém o produto que foi adicionado na lista
        Produto produto = sysCadastro.getProdutos().get(sysCadastro.getProdutos().size() - 1);

        // Verifica se o produto foi adicionado e seus dados estão corretos
        assertNotNull(produto);
        assertEquals(codigoProduto, produto.getCodigoItem());
        assertEquals(descricaoProduto, produto.getDescricao());
        assertEquals(valorVendaProduto, produto.getValorVenda(), 0.01);
        assertEquals(unidadeProduto, produto.getUnidade());
    }


    @Test
    public void testCadastroVenda() {
        // Adiciona cliente
        String[] clienteInputs = {cpf_cnpj, nome, estado, isCapital ? "s" : "n", tipoCliente.equals("prime") ? "s" : "n"};
        sysCadastro.setScanner(new java.util.Scanner(String.join("\n", clienteInputs) + "\n"));
        sysCadastro.cadastrarCliente();

        Cliente cliente = sysCadastro.getClientes().get(sysCadastro.getClientes().size() - 1);
        
        // Adiciona produto
        String[] produtoInputs = {codigoProduto, descricaoProduto, String.valueOf(valorVendaProduto), unidadeProduto};
        sysCadastro.setScanner(new java.util.Scanner(String.join("\n", produtoInputs) + "\n"));
        sysCadastro.cadastrarProduto();

        Produto produto = sysCadastro.getProdutos().get(sysCadastro.getProdutos().size() - 1);

        // Cadastra a venda
        String[] vendaInputs = {dataVenda, metodoPagamento.equals("dinheiro") ? "1" : metodoPagamento.equals("cartao_loja") ? "2" : "3", usarCashback ? "s" : "n"};
        sysCadastro.setScanner(new java.util.Scanner(String.join("\n", vendaInputs) + "\n" + codigoProduto + "\n" + "1" + "\n" + "fim" + "\n"));
        sysCadastro.cadastrarVenda();

        Venda venda = sysCadastro.getVendas().get(sysCadastro.getVendas().size() - 1);

        assertNotNull(venda);
        assertEquals(dataVenda, venda.getData());
        assertEquals(cliente, venda.getCliente());
        assertEquals(metodoPagamento, venda.getMetodoPagamento());
        assertEquals(usarCashback, venda.isUsarCashBack());

        ItemVenda itemVenda = venda.getItens().get(0);
        assertEquals(produto, itemVenda.getProduto());
        assertEquals(1, itemVenda.getQuantidade());

        // Verifica o total da nota
        double totalNotaEsperado = venda.calculaTotalNota();
        assertEquals(totalNotaEsperado, venda.getTotal(), 0.01);
    }
}
