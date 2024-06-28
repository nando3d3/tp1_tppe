import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    public VendaTest(String data, Cliente cliente, String metodoPagamento, Produto produto, int quantidade){
        this.data = data;
        this.cliente = cliente;
        this.metodoPagamento = metodoPagamento;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    Venda venda = new Venda(data, cliente, metodoPagamento);

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        Produto produto1 = new Produto("P1", "Camiseta", 50.0, "unidade");
        Produto produto2 = new Produto("P2", "Calça Jeans", 120.0, "unidade");

        Endereco end1 = new Endereco("SP", "SE", true);
        Endereco end2 = new Endereco("GO", "CO", false);
        Endereco end3 = new Endereco("AM", "NO", true);

        Cliente cliente1 = new Cliente("1", "José da Silva", end1, "Padrão");
        Cliente cliente2 = new Cliente("2", "Maria", end2, "Especial");
        Cliente cliente3 = new Cliente("3", "Alexandre o Grande", end3, "Prime");
        
        return Arrays.asList(new Object[][]{
            {"2024-03-25", cliente1, "dinheiro", produto1, 2},
            {"2024-02-26", cliente2, "cartão de débito", produto2, 1},
            {"2024-06-09", cliente3, "cartão de crédito", produto1, 3}
        });
    
    }

    @Test
    public void testCadastroVenda() {
        assertNotNull(venda);
        assertEquals(data, venda.getData());
        assertEquals(cliente, venda.getCliente());
        assertEquals(metodoPagamento, venda.getMetodoPagamento());
    }

    @Test
    public void testAddItem(){
        venda.addItem(produto, quantidade);

        List<ItemVenda> itens = venda.getItens();
        assertEquals(1, itens.size());
        assertEquals(produto, itens.get(0).getProduto());
        assertEquals(quantidade, itens.get(0).getQuantidade());
    }

    @Test
    public void testCalculaTotal(){
        
        venda.addItem(produto, quantidade);

        double valorTotalEx = produto.getValorVenda() * quantidade;
        assertEquals(valorTotalEx, venda.calculaTotal(), 0.01);
    }

}

