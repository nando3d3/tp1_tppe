import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Date;

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

    private static List<Venda> criaVendasClientePrime(Cliente cliente) {
        List<Venda> vendas = new ArrayList<>();

        vendas.add(criaVenda(LocalDate.now().minusMonths(1).withDayOfMonth(20), cliente, "cartao_loja", false, 40.0, 2));

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
        Cliente clienteEspecial = new ClienteEspecial(1, "831.997.200-05", "Cliente Especial", new Endereco("DF", true));
        Cliente clienteNaoEspecial = new ClientePadrao(2, "081.707.980-78", "Cliente Nao Especial", new Endereco("SP", true));
        Cliente clientePrime = new ClientePrime(3, "586.148.250-05", "Cliente Prime", new Endereco("GO", true));

        return Arrays.asList(new Object[][] {
            { clienteEspecial, criaVendasClienteEspecial(clienteEspecial), true},
            { clienteNaoEspecial, criaVendasClienteNaoEspecial(clienteNaoEspecial), false},
            { clientePrime, criaVendasClientePrime(clientePrime), false}
        });
    }

    @Test
    public void testVerificaClienteEspecial() {
        assertEquals(valorEsperado, relatorioVenda.verificaClienteEspecial(cliente));
        //Double totalVendasCliente = relatorioVenda.getVendasClientes().get(cliente);
        
        if (valorEsperado) {
            assertTrue(cliente instanceof ClienteEspecial);
        } else {
            assertTrue(!(cliente instanceof ClienteEspecial));
        }
    }

    @Test
    public void testCalculaVendasUltimoMes() {
        relatorioVenda.calculaVendasUltimoMes();
        Double totalVendasCliente = relatorioVenda.getVendasClientes().get(cliente);

        if (valorEsperado) {
            assertEquals(Double.valueOf(160.18), totalVendasCliente);
        } else if (cliente.getNome().equals("Cliente Nao Especial")) {
            assertEquals(Double.valueOf(90.6), totalVendasCliente);
        } else if (cliente.getNome().equals("Cliente Prime")) {
            assertEquals(Double.valueOf(92.8), totalVendasCliente);
        }
    }

    @Test
    public void testDataUltimoMes() {
        Date[] periodoUltimoMes = relatorioVenda.dataUltimoMes();
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");

        Calendar inicioEsperado = Calendar.getInstance();
        inicioEsperado.add(Calendar.MONTH, -1);
        inicioEsperado.set(Calendar.DAY_OF_MONTH, 1);

        Calendar fimEsperado = Calendar.getInstance();
        fimEsperado.add(Calendar.MONTH, -1);
        fimEsperado.set(Calendar.DAY_OF_MONTH, fimEsperado.getActualMaximum(Calendar.DAY_OF_MONTH));

        assertEquals(formatoData.format(inicioEsperado.getTime()), formatoData.format(periodoUltimoMes[0]));
        assertEquals(formatoData.format(fimEsperado.getTime()), formatoData.format(periodoUltimoMes[1]));

    }

    @Test
    public void testVendaUltimoMes() {
        Venda venda = criaVenda(LocalDate.now().minusMonths(1).withDayOfMonth(10), cliente, "cartao_empresa", false, 20.0, 2);
        Date[] periodoUltimoMes = relatorioVenda.dataUltimoMes();

        relatorioVenda.vendaUltimoMes(venda, periodoUltimoMes[0], periodoUltimoMes[1]);
        Double totalVendasCliente = relatorioVenda.getVendasClientes().get(cliente);

        assertTrue(totalVendasCliente != null && totalVendasCliente > 40.0);
    }

    @Test
    public void testAtualizaVendasCliente() {
        Venda venda = criaVenda(LocalDate.now().minusMonths(1).withDayOfMonth(10), cliente, "pix", false, 20.0, 2);

        relatorioVenda.atualizaVendasCliente(venda);
        Double totalVendasCliente = relatorioVenda.getVendasClientes().get(cliente);

        assertTrue(totalVendasCliente != null && totalVendasCliente > 40.0);

    }

}
