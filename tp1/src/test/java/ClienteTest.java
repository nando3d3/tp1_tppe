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
public class ClienteTest {
    private int id;
    private String cpf_cnpj;
    private String nome;
    private Endereco endereco;
    private String tipoCliente;
    private double cash_back;

    private Cliente cliente;

    public ClienteTest(int id, String cpf_cnpj, String nome, Endereco endereco, String tipoCliente, double cash_back) {
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
        this.cash_back = cash_back;
    }

    @Before
    public void setup(){
        cliente = new Cliente(id, cpf_cnpj, nome, endereco, tipoCliente);
    }
    
    @Parameters
    public static Collection<Object[]> data() {
        Endereco end1 = new Endereco("GO", false);
        Endereco end2 = new Endereco("DF", true);
        Endereco end3 = new Endereco("RS", false);

        return Arrays.asList(new Object[][]{
            {1, "12345678900", "John Lennon", end1, "prime", 0.00},
            {2, "12345678911", "Paul McCartney", end2, "especial", 0.00},
            {3, "00987654123", "George Harrison", end3, "padrao", 0.00}
        });
    }

    @Test 
    public void testCadastroCliente(){

        assertNotNull(cliente);
        assertEquals(id, cliente.getId());
        assertEquals(nome, cliente.getNome());
        assertEquals(endereco, cliente.getEndereco());
        assertEquals(tipoCliente, cliente.getTipoCliente());
        assertEquals(cash_back, cliente.getCashBack(), 0.001);
    }
}