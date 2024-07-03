import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class VendaTest {

    private String data;
    private Cliente cliente;
    private String metodoPagamento;
    private Produto produto;
    private int quantidade;
    private double valorFrete;
    private double totalPedido;
    private double imposto;
    private double valorTotalNota;
    private boolean usarCashback;

    private Venda venda;

    public VendaTest(String data, Cliente cliente, String metodoPagamento, Produto produto, int quantidade, double valorFrete, double imposto, double totalPedido, double valorTotalNota, boolean usarCashback) {
        this.data = data;
        this.cliente = cliente;
        this.metodoPagamento = metodoPagamento;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorFrete = valorFrete;
        this.imposto = imposto;
        this.totalPedido = totalPedido;
        this.valorTotalNota = valorTotalNota;
        this.usarCashback = usarCashback;
    }

    @Before
    public void setup(){
        venda = new Venda(data, cliente, metodoPagamento, usarCashback);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Produto produto1 = new Produto("P1", "Camiseta", 50.0, "unidade");
        Produto produto2 = new Produto("P2", "Calça Jeans", 120.0, "unidade");

        Endereco end1 = new Endereco("SP", true);
        Endereco end2 = new Endereco("GO", false);
        Endereco end3 = new Endereco("AM", true);

        Cliente cliente1 = new Cliente(1, "12345678900", "José da Silva", end1, "padrao");
        Cliente cliente2 = new Cliente(2, "98765432111", "Maria", end2, "especial");
        Cliente cliente3 = new Cliente(3, "12398765433","Alexandre o Grande", end3, "prime");

        return Arrays.asList(new Object[][]{
            //[0] cliente padrão
            {"2024-03-25", cliente1, "a_vista", produto1, 2, 7.00, 2 * 50.0 * (0.12 + 0.04), 2 * 50.0, (2 * 50.0) + (2 * 50.0 * (0.12 + 0.04)) + 7.00, false},

            //[1] cliente especial com pagamento em cartão de débito                                            
            {"2024-02-26", cliente2, "cartao_debito", produto2, 1, 13.00, 1 * 120.0 * (0.12 + 0.04), 1 * 120.0, ((0.90 * 120.0 + (120.0 * (0.12 + 0.04))) + (13.00 * 0.70)), false},

            //[2] cliente especial com pagamento em cartão da empresa
            {"2024-03-15", cliente2, "cartao_empresa", produto2, 1, 13.00, 1 * 120.0 * (0.12 + 0.04), 120.0, 0.90 *(120.00 * 0.90 + 120*0.16 + 13.00 * 0.70), false},

            //[3] cliente Prime com pagamento em dinheiro com cashback
            {"2024-02-09", cliente3, "dinheiro", produto1, 3, 20.00, 3 * 50.0 * (0.12 + 0.04), 150.0, 150 + 150*0.16 - 150*0.03, true},

            //[4] cliente Prime com pagamento em cartão da loja com cashback
            {"2024-07-09", cliente3, "cartao_loja", produto1, 3, 20.00, 3 * 50.0 * (0.12 + 0.04), 150.0, 150 + 150*0.16 - 150*0.05, true},

            //[5] cliente Prime com pagamento em dinheiro sem cashback
            {"2024-08-09", cliente3, "dinheiro", produto2, 3, 20.00, 3 * 120 * (0.12 + 0.04), 3 * 120, 3 * 120 + 3 * 120 *0.16, false},

            //[6] cliente Prime com pagamento em cartão da loja sem cashback
            {"2024-07-09", cliente3, "cartao_loja", produto1, 3, 20.00, 3 * 50.0 * (0.12 + 0.04), 150.0, 150 + 150*0.16 , false}
        });
    }

    @Test
    public void testCadastroVenda() {

        assertNotNull(venda);
        assertEquals(data, venda.getData());
        assertEquals(cliente, venda.getCliente());
        assertEquals(metodoPagamento, venda.getMetodoPagamento());
        assertEquals(usarCashback, venda.isUsarCashBack());
    }

    @Test
    public void testAddItem() {

        venda.addItem(produto, quantidade);

        List<ItemVenda> itens = venda.getItens();
        assertEquals(1, itens.size());
        assertEquals(produto, itens.get(0).getProduto());
        assertEquals(quantidade, itens.get(0).getQuantidade());
    }

    @Test
    public void testCalculaTotalPedido() {
        venda.addItem(produto, quantidade);

        double totalPedidoEx = produto.getValorVenda() * quantidade;
        assertEquals(totalPedidoEx, totalPedido, 0.01);
    }

    @Test
    public void testCalculaFrete() {
        venda.addItem(produto, quantidade);

        double valorFreteEx = venda.valorFrete();

        assertEquals(valorFreteEx, valorFrete, 0.001);
    }

    @Test
    public void testCalculaImposto() {
        venda.addItem(produto, quantidade);

        double impostoEx = venda.calculaImposto();
        assertEquals(impostoEx, imposto, 0.001);
    }

    @Test
    public void testCalculaTotalNota() {
        venda.addItem(produto, quantidade);

        double valorTotalNotaEx = venda.calculaTotalNota();
        assertEquals(valorTotalNota, valorTotalNotaEx, 0.001);
    }
}
