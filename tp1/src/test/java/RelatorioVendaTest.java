import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class RelatorioVendaTest {

    private RelatorioVenda relatorioVenda;
    private List<Venda> vendas;

    private Cliente cliente;
    private boolean tipoEsperado;

    public RelatorioVendaTest(Cliente cliente, List<Venda> vendas, boolean tipoEsperado) {
        this.cliente = cliente;
        this.vendas = vendas;
        this.tipoEsperado = tipoEsperado;
    }

    @Before
    public void setUp() {
        relatorioVenda = new RelatorioVenda(vendas);
        relatorioVenda.calcularVendasUltimoMes();
    }

    private static List<Venda> criarVendasClienteEspecial(Cliente cliente) {
        return Arrays.asList(
            criaVenda("2024-06-01", cliente, "cartao_loja", false, 50.0, 2),
            criaVenda("2024-06-15", cliente, "cartao_empresa", true, 50.0, 1),
            criaVenda("2024-06-20", cliente, "cartao_loja", false, 50.0, 1)
        );
    }

    private static List<Venda> criarVendasClienteNaoEspecial(Cliente cliente) {
        return Arrays.asList(
            criaVenda("2024-06-01", cliente, "cartao", false, 30.0, 2),
            criaVenda("2024-06-15", cliente, "pix", false, 20.0, 1),
            criaVenda("2024-06-20", cliente, "cartao_loja", false, 10.0, 1)
        );
    }

    private static Venda criaVenda(String data, Cliente cliente, String metodoPagamento, boolean usarCashback, double valorItem, int quantidade) {
        Venda venda = new Venda(data, cliente, metodoPagamento, usarCashback);
        Produto produto = new Produto("001", "Produto de Teste", valorItem, "unidade");
        venda.addItem(produto, quantidade);
        return venda;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Cliente clienteEspecial = new Cliente(1, "831.997.200-05", "Cliente 1", new Endereco("DF", true), "padrao");
        Cliente clienteNaoEspecial = new Cliente(2, "081.707.980-78", "Cliente 2", new Endereco("SP", true), "padrao");

        return Arrays.asList(new Object[][] {
            { clienteEspecial, criarVendasClienteEspecial(clienteEspecial), true },
            { clienteNaoEspecial, criarVendasClienteNaoEspecial(clienteNaoEspecial), false }
        });
    }

    @Test
    public void testVerificarClienteEspecial() {
        assertEquals(tipoEsperado, relatorioVenda.verificarClienteEspecial(cliente));
    }
}
