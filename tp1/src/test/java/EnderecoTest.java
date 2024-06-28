import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class EnderecoTest {
    private String siglaEstado;
    private String siglaRegiao;
    private boolean isCapital;

    public EnderecoTest(String siglaEstado, String siglaRegiao, double freteEntrega, boolean isCapital){
        this.siglaEstado = siglaEstado;
        this.siglaRegiao = siglaRegiao;
        this.isCapital = isCapital;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"DF", "DF", true},
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
    public void testCadastroEndereco(){
        Endereco endereco = new Endereco(siglaEstado, siglaRegiao, isCapital);

        assertNotNull(endereco);
        assertEquals(siglaEstado, endereco.getSiglaEstado());
        assertEquals(siglaRegiao, endereco.getSiglaRegiao());
        assertEquals(isCapital, endereco.isCapital());
    }
}
