import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ProdutoTest {
    private String codigoItem;
    private String descricao;
    private double valorVenda;
    private String unidade;

    public ProdutoTest(String codigoItem, String descricao, double valorVenda, String unidade){
        this.codigoItem = codigoItem;
        this.descricao  = descricao;
        this.valorVenda = valorVenda;
        this.unidade    = unidade;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"0000", "Arroz", 8.5, "kg"},
            {"0001", "Feijao", 5.0, "kg"},
            {"0002", "Mandioca", 2.0, "kg"},
            {"0003", "Alcatra", 25.0, "peca"},
            {"0004", "Mamao", 9.5, "unidade"}
        });
    }

    // 0
    @Test
    public void testCadastroProduto() {
        Produto produto = new Produto(codigoItem, descricao, valorVenda, unidade);
        
        assertNotNull(produto);
        assertEquals(codigoItem, produto.getCodigoItem());
        assertEquals(descricao, produto.getDescricao());
        assertEquals(valorVenda, produto.getValorVenda(), 0.01);
        assertEquals(unidade, produto.getUnidade());
    }

    // 1
    @Test
    public void testCodigoItemVazio(){
        Produto produto = new Produto(codigoItem, descricao, valorVenda, unidade);
        assertNotNull(produto.getCodigoItem());
        assertFalse(produto.getCodigoItem().isEmpty());
    }

    // 2
    @Test
    public void testDescricaoVazia(){
        Produto produto = new Produto(codigoItem, descricao, valorVenda, unidade);
        assertNotNull(produto.getDescricao());
        assertFalse(produto.getDescricao().isEmpty());
    }

    // 3
    @Test
    public void testValorValorVendaZero(){
        Produto produto = new Produto(codigoItem, descricao, valorVenda, unidade);
        assertNotEquals(0.0 , produto.getValorVenda());
    }

    // 4
    @Test
    public void testUnidadeVazia(){
        Produto produto = new Produto(codigoItem, descricao, valorVenda, unidade);
        assertNotNull(produto.getUnidade());
        assertFalse(produto.getUnidade().isEmpty());
    }
}
