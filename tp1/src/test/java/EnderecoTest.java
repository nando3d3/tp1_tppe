import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class EnderecoTest {
    private String siglaEstado;
    private String siglaRegiao;
    private boolean isCapital;

    private Endereco endereco;

    public EnderecoTest(String siglaEstado, String siglaRegiao, boolean isCapital) {
        this.siglaEstado = siglaEstado.toUpperCase();
        this.siglaRegiao = siglaRegiao;
        this.isCapital = isCapital;
    }

    @Before
    public void setup() {
        endereco = new Endereco(siglaEstado, isCapital);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"DF", "CO", true},
                {"GO", "CO", true},
                {"MT", "CO", false},
                {"CE", "NE", true},
                {"MA", "NE", false},
                {"AM", "NO", true},
                {"RR", "NO", false},
                {"SP", "SE", true},
                {"MG", "SE", true},
                {"RS", "SU", true},
                {"PR", "SU", true},
        });
    }

    @Test
    public void testCadastroEndereco() {
        assertNotNull(endereco);
        assertEquals(siglaEstado, endereco.getSiglaEstado());
        assertEquals(siglaRegiao, endereco.getSiglaRegiao());
        assertEquals(isCapital, endereco.isCapital());
    }

    @Test
    public void testDefinirRegiao(){
        String regiao = endereco.getSiglaRegiao();
        assertEquals(siglaRegiao, regiao);
    }
}
