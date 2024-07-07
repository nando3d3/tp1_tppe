import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ProdutoTest {
    private String codigoItem;
    private String descricao;
    private double valorVenda;
    private String unidade;
    private boolean isExceptionThrowed;

    public ProdutoTest(String codigoItem, String descricao, double valorVenda, String unidade, boolean isExceptionThrowed) {
        this.codigoItem = codigoItem;
        this.descricao = descricao;
        this.valorVenda = valorVenda;
        this.unidade = unidade;
        this.isExceptionThrowed = isExceptionThrowed;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {"0000", "Arroz", 8.5, "kg", false},
            {"", "Feijao", 5.0, "kg", true},
            {"0002", "", 2.0, "kg", true},
            {"0003", "Mandioca", 0.0, "unidade", true},
            {"0004", "Alcatra", -5.0, "kg", true},
            {"0005", "Mamao", 9.5, "", true}
        });
    }

    @Test
    public void testProdutoCreation() {
        try {
            Produto produto = new Produto(codigoItem, descricao, valorVenda, unidade);
            if (isExceptionThrowed) {
                fail("Expected IllegalArgumentException to be thrown");
            }
            assertEquals(codigoItem, produto.getCodigoItem());
            assertEquals(descricao, produto.getDescricao());
            assertEquals(valorVenda, produto.getValorVenda(), 0.001);
            assertEquals(unidade, produto.getUnidade());
        } catch (IllegalArgumentException e) {
            if (!isExceptionThrowed) {
                fail("Unexpected IllegalArgumentException was thrown");
            }
        }
    }
}
