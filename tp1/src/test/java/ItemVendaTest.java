import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ItemVendaTest {
    private Produto produto;
    private int quantidade;
    private double valorImpostoICMS;
    private double valorImpostoMunicipal;


    private ItemVenda itemVenda;

    public ItemVendaTest(Produto produto, int quantidade, double valorImpostoICMS, double valorImpostoMunicipal) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorImpostoICMS = valorImpostoICMS;
        this.valorImpostoMunicipal = valorImpostoMunicipal;
    }

    @Before
    public void setup(){
        itemVenda = new ItemVenda(produto, quantidade);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Produto produto1 = new Produto("P1", "Camiseta", 50.0, "unidade");
        Produto produto2 = new Produto("P2", "Calça Jeans", 120.0, "unidade");
        Produto produto3 = new Produto("P3", "Tênis", 200.0, "unidade");

        return Arrays.asList(new Object[][]{
            {produto1, 2, 2 * 50.0 * 0.12, 2 * 50.0 * 0.04},
            {produto2, 1, 120.0 * 0.12, 120.0 * 0.04},
            {produto3, 3, 3 * 200.0 * 0.12, 3 * 200.0 * 0.04},
            {produto1, 5, 5 * 50.0 * 0.12, 5 * 50.0 * 0.04},
            {produto2, 4, 4 * 120.0 * 0.12, 4 * 120.0 * 0.04}
        });
    }

    @Test
    public void testItemVenda(){
        itemVenda.setValorImpostoICMS(valorImpostoICMS);
        itemVenda.setValorImpostoMunicipal(valorImpostoMunicipal);

        assertNotNull(itemVenda);
        assertEquals(produto, itemVenda.getProduto());
        assertEquals(quantidade, itemVenda.getQuantidade());
        assertEquals(valorImpostoICMS, itemVenda.getValorImpostoICMS(), 0.001);
        assertEquals(valorImpostoMunicipal, itemVenda.getValorImpostoMunicipal(),0.001);
    }
}
