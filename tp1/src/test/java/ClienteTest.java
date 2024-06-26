import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ClienteTest {
    private String id;
    private String nome;
    private String endereco;
    private boolean isCapital;
    private String tipoCliente;

    public ClienteTest(String id, String nome, String endereco, boolean isCapital, String tipoCliente) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.isCapital = isCapital;
        this.tipoCliente = tipoCliente;
    }
    
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"1", "John Lennon", "Goiás", false, "Prime"},
            {"2", "Paul McCartney", "Mato Grosso", true, "Especial"},
            {"3", "George Harrison", "Rio Grande do Sul", true, "Padrão"}
        });
    }

    @Test 
    public void testCadastroCliente(){
        Cliente cliente = new Cliente(id, nome, endereco, isCapital, tipoCliente);

        assertNotNull(cliente);
        assertEquals(id, cliente.getId());
        assertEquals(nome, cliente.getNome());
        assertEquals(endereco, cliente.getEndereco());
        assertEquals(isCapital, cliente.getCapital());
        assertEquals(tipoCliente, cliente.getTipoCliente());
    }

}
