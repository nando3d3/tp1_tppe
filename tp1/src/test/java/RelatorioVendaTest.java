import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class RelatorioVendaTest {

    private RelatorioVenda relatorioVenda;
    private List<Venda> vendas;

    private Cliente cliente;
    private boolean valorEsperado;

    public RelatorioVendaTest(Cliente cliente, List<Venda> vendas, boolean valorEsperado) {
        this.cliente = cliente;
        this.vendas = vendas;
        this.valorEsperado = valorEsperado;
    }

    @Before
    public void setUp() {
        relatorioVenda = new RelatorioVenda(vendas);
        relatorioVenda.calculaVendasUltimoMes();
    }

    private static List<Venda> criaVendasClienteEspecial(Cliente cliente) {
        List<Venda> vendas = new ArrayList<>();

        vendas.add(criaVenda(LocalDate.now().minusMonths(1).withDayOfMonth(5), cliente, "cartao_loja", false, 50.0, 2));
        vendas.add(criaVenda(LocalDate.now().minusMonths(1).withDayOfMonth(10), cliente, "cartao_empresa", false, 20.0, 2));

        return vendas;
    }

    private static List<Venda> criaVendasClienteNaoEspecial(Cliente cliente) {
        List<Venda> vendas = new ArrayList<>();

        vendas.add(criaVenda(LocalDate.now().minusMonths(1).withDayOfMonth(15), cliente, "cartao", false, 30.0, 1));
        vendas.add(criaVenda(LocalDate.now().minusMonths(1).withDayOfMonth(20), cliente, "pix", false, 20.0, 1));
        vendas.add(criaVenda(LocalDate.now().minusMonths(1).withDayOfMonth(25), cliente, "cartao_loja", false, 5.0, 2));

        return vendas;
    }

    private static Venda criaVenda(LocalDate data, Cliente cliente, String metodoPagamento, boolean usarCashback, double valorItem, int quantidade) {
        DateTimeFormatter formatdo = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = data.format(formatdo);
        
        Venda venda = new Venda(dataFormatada, cliente, metodoPagamento, usarCashback);
        Produto produto = new Produto("001", "Produto de Teste", valorItem, "unidade");
        venda.addItem(produto, quantidade);
        return venda;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Cliente clienteEspecial = new Cliente(1, "831.997.200-05", "Cliente Especial", new Endereco("DF", true), "padrao");
        Cliente clienteNaoEspecial = new Cliente(2, "081.707.980-78", "Cliente Nao Especial", new Endereco("SP", true), "padrao");

        return Arrays.asList(new Object[][] {
            { clienteEspecial, criaVendasClienteEspecial(clienteEspecial), true },
            { clienteNaoEspecial, criaVendasClienteNaoEspecial(clienteNaoEspecial), false }
        });
    }

    @Test
    public void testVerificaClienteEspecial() {
        assertEquals(valorEsperado, relatorioVenda.verificaClienteEspecial(cliente));
        Double totalVendasCliente = relatorioVenda.getVendasClientes().get(cliente);
        System.out.println("Cliente: " + cliente.getNome() + " - Total de Vendas: " + totalVendasCliente);
    }
}
