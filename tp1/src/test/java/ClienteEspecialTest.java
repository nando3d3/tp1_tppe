import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ClienteEspecialTest {
    private int id;
    private String cpf_cnpj;
    private String nome;
    private Endereco endereco;

    private Cliente cliente;

    public ClienteEspecialTest(int id, String cpf_cnpj, String nome, Endereco endereco) {
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.nome = nome;
        this.endereco = endereco;
    }

    @Before
    public void setup() throws Exception {
        cliente = new ClienteEspecial(id, cpf_cnpj, nome, endereco);
    }

    @Parameters
    public static Collection<Object[]> data() {
        Endereco end1 = new Endereco("GO", false);
        Endereco end2 = new Endereco("DF", true);

        return Arrays.asList(new Object[][]{
            {1, "12345678900", "Cliente Especial 1", end1},
            {2, "12345678911", "Cliente Especial 2", end2}
        });
    }

    @Test
    public void testCadastroClienteEspecial() {
        assertNotNull(cliente);
        assertEquals(id, cliente.getId());
        assertEquals(nome, cliente.getNome());
        assertEquals(endereco, cliente.getEndereco());
        assertEquals(cpf_cnpj, cliente.getCpfCnpj());
    }
}
