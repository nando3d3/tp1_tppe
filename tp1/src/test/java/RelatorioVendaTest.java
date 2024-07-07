import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(Parameterized.class)
public class RelatorioVendaTest {

    private RelatorioVenda relatorioVenda;
    private List<Venda> vendas;
    private Cliente cliente;
    private boolean valorEsperado;
    private Map<Cliente, Double> vendasEsperadas;

    public RelatorioVendaTest(Cliente cliente, List<Venda> vendas, boolean valorEsperado, Map<Cliente, Double> vendasEsperadas) {
        this.cliente = cliente;
        this.vendas = vendas;
        this.valorEsperado = valorEsperado;
        this.vendasEsperadas = vendasEsperadas;
    }

    @Before
    public void setUp() {
        relatorioVenda = new RelatorioVenda(vendas);
    }

    private static List<Venda> criaVendasClienteEspecial(Cliente cliente) {
        List<Venda> vendas = new ArrayList<>();

        vendas.add(criaVenda(LocalDate.now().minusMonths(1).withDayOfMonth(5), cliente, "cartao", false, 50.0, 2));

        return vendas;
    }

    private static List<Venda> criaVendasClienteNaoEspecial(Cliente cliente) {
        List<Venda> vendas = new ArrayList<>();

        vendas.add(criaVenda(LocalDate.now().minusMonths(1).withDayOfMonth(15), cliente, "cartao", false, 30.0, 1));

        return vendas;
    }

    private static List<Venda> criaVendasClientePrime(Cliente cliente) {
        List<Venda> vendas = new ArrayList<>();

        vendas.add(criaVenda(LocalDate.now().minusMonths(1).withDayOfMonth(20), cliente, "cartao", false, 40.0, 2));

        return vendas;
    }

    private static Venda criaVenda(LocalDate data, Cliente cliente, String metodoPagamento, boolean usarCashback, double valorItem, int quantidade) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = data.format(formato);
        
        Venda venda = new Venda(dataFormatada, cliente, metodoPagamento, usarCashback);
        Produto produto = new Produto("001", "Produto de Teste", 10.00, "unidade");
        venda.addItem(produto, quantidade);
        return venda;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Cliente clienteEspecial = new Cliente(1, "831.997.200-05", "Cliente Especial", new Endereco("DF", true), "padrao");
        Cliente clienteNaoEspecial = new Cliente(2, "081.707.980-78", "Cliente Nao Especial", new Endereco("SP", true), "padrao");
        Cliente clientePrime = new Cliente(3, "586.148.250-05", "Cliente Prime", new Endereco("GO", true), "prime");

        Map<Cliente, Double> vendasEsperadasClienteEspecial = new HashMap<>();
        vendasEsperadasClienteEspecial.put(clienteEspecial, 116.0);

        Map<Cliente, Double> vendasEsperadasClienteNaoEspecial = new HashMap<>();
        vendasEsperadasClienteNaoEspecial.put(clienteNaoEspecial, 196.5); 

        Map<Cliente, Double> vendasEsperadasClientePrime = new HashMap<>();
        vendasEsperadasClientePrime.put(clientePrime, 92.8);  
        return Arrays.asList(new Object[][] {
            { clienteEspecial, criaVendasClienteEspecial(clienteEspecial), true, vendasEsperadasClienteEspecial },
            { clienteNaoEspecial, criaVendasClienteNaoEspecial(clienteNaoEspecial), false, vendasEsperadasClienteNaoEspecial },
            { clientePrime, criaVendasClientePrime(clientePrime), false, vendasEsperadasClientePrime }
        });
    }

    @Test
    public void testVerificaClienteEspecial() {
        assertEquals(valorEsperado, relatorioVenda.verificaClienteEspecial(cliente));
    }

    @Test
    public void testCalculaVendasUltimoMes() {
        assertEquals(vendasEsperadas, relatorioVenda.getVendasClientes());
    }
}
