import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ItemVendaTest {
    private Produto produto;
    private int quantidade;

    public ItemVendaTest(Produto produto, int quantidade){
        this.produto = produto;
        this.quantidade = quantidade;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        Produto produto1 = new Produto("P1", "Garrafa de Água", 3.50, "unidade");
        Produto produto2 = new Produto("P2", "Mouse sem fio", 150.0, "unidade");

        return Arrays.asList(new Object[][]{
            {produto1, 3},
            {produto2, 1}
        });
    }

    @Test
    public void testItemVenda(){
        ItemVenda itemVenda = new ItemVenda(produto, quantidade);

        assertNotNull(itemVenda);
        assertEquals(produto, itemVenda.getProduto());
        assertEquals(quantidade, itemVenda.getQuantidade());
    }
}
