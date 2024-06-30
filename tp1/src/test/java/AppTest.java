import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AppTest {
    private Cliente cliente;
    private Produto produto;
    private Venda venda;
    private List<Cliente> clientes;
    private List<Produto> produtos;
    private List<Venda> vendas;

    @Before
    public void inicializaTeste() {
        clientes = new ArrayList<>();
        produtos = new ArrayList<>();
        vendas = new ArrayList<>();

        Endereco endereco = new Endereco("DF", true);
        cliente = new Cliente(0, "16546", "Fulano", endereco, "padrao");
        clientes.add(cliente);

        produto = new Produto("0000", "Pao", 10.0, "kg");
        produtos.add(produto);

        venda = new Venda(new Date().toString(), cliente, "cartao_loja");
        venda.addItem(produto, 5);
        vendas.add(venda);
    }

    @Test
    public void testCadastrarCliente(){
        assertEquals(1, clientes.size());
        assertEquals(0, clientes.get(0).getId());
        assertEquals("16546", clientes.get(0).getCpfCnpj());
        assertEquals("Fulano", clientes.get(0).getNome());
        assertEquals("padrao", clientes.get(0).getTipoCliente());
        assertEquals("DF", clientes.get(0).getEndereco().getSiglaEstado());
    }

    @Test
    public void testCadastrarProduto(){
        assertEquals(1, produtos.size());
        assertEquals("0000", produtos.get(0).getCodigoItem());
        assertEquals("Pao", produtos.get(0).getDescricao());
        assertEquals("kg", produtos.get(0).getUnidade());
    }

    @Test
    public void testRealizarVenda(){
        assertEquals(1, vendas.size());
        assertEquals(cliente, vendas.get(0).getCliente());
    }

    @Test
    public void testCalcularVendasUltimoMes() {
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.MONTH, -1);
        Date mesAnterior = calendario.getTime();
        SimpleDateFormat dataSimples = new SimpleDateFormat("yyyy-MM-dd");

        double totalConsumo = 0.0;
        for (Cliente cliente : clientes) {
        
            for (Venda venda : vendas) {
                Date dataVenda;
                try {
                    dataVenda = dataSimples.parse(venda.getData());
                } catch (ParseException e) {
                    continue;
                }
                
                if (dataVenda.after(mesAnterior)) {
                    if (venda.getCliente().equals(cliente)) {
                        totalConsumo += venda.calculaTotalNota();
                    }
                }
            }
        }
        assertTrue(totalConsumo > 0);
    }
   
}