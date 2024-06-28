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
    private Endereco endereco;
    private String tipoCliente;

    public ClienteTest(String id, String nome, Endereco endereco, String tipoCliente) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
    }
    
    @Parameters
    public static Collection<Object[]> data() {
        Endereco end1 = new Endereco("GO", "CO", false);
        Endereco end2 = new Endereco("DF", "DF", true);
        Endereco end3 = new Endereco("RS", "SU", false);

        return Arrays.asList(new Object[][]{
            {"1", "John Lennon", end1, "Prime"},
            {"2", "Paul McCartney", end2, "Especial"},
            {"3", "George Harrison", end3, "Padrão"}
        });
    }

    @Test 
    public void testCadastroCliente(){
        Cliente cliente = new Cliente(id, nome, endereco, tipoCliente);

        assertNotNull(cliente);
        assertEquals(id, cliente.getId());
        assertEquals(nome, cliente.getNome());
        assertEquals(endereco, cliente.getEndereco());
        assertEquals(tipoCliente, cliente.getTipoCliente());
    }
}