import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class CalculadoraNotaTest {
    private CalculadoraNota calculadora;
    private Venda venda;
    private double valorEsperado;

    public CalculadoraNotaTest(Venda venda, double valorEsperado){
        this.venda = venda;
        this.valorEsperado = valorEsperado;
    }

    @Before
    public void setUp() {
        calculadora = new CalculadoraNota(venda);
    }

    @Parameters
    public static Collection<Object[]> data() {
        Produto produto1 = new Produto("P1", "Camiseta", 50.0, "unidade");
        Produto produto2 = new Produto("P2", "Calça Jeans", 120.0, "unidade");

        Endereco end1 = new Endereco("SP", true);
        Endereco end2 = new Endereco("GO", false);
        Endereco end3 = new Endereco("AM", true);

        Cliente cliente1 = new ClientePadrao(1, "12345678900", "José da Silva", end1);
        Cliente cliente2 = new ClienteEspecial(2, "98765432111", "Maria", end2);
        Cliente cliente3 = new ClientePrime(3, "12398765433", "Alexandre o Grande", end3);

        Venda venda1 = new Venda("2023-08-13", cliente2, "cartao_loja", false);
        venda1.addItem(produto1, 1);

        Venda venda2 = new Venda("2024-12-12", cliente1, "pix", false);
        venda2.addItem(produto2, 1);

        Venda venda3 = new Venda("2024-01-01", cliente3, "cartao_loja", true);
        venda3.addItem(produto1, 1);

        return Arrays.asList(new Object[][]{
            {venda1, 62.10},
            {venda2, 146.20},
            {venda3, 55.50}
        });
    }

    @Test
    public void testCalculaNota(){
        assertEquals(valorEsperado, calculadora.calcularNota(), 0.01);
    }
}
