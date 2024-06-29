import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class SistemaCadastroTest {
    private String cpf_cnpj;
    private String nome;
    private String estado;
    private boolean isCapital;
    private String tipoCliente;

    private SistemaCadastro sysCadastro;

    @Before
    public void setup(){
        sysCadastro = new SistemaCadastro();
    }

    public SistemaCadastroTest(String cpf_cnpj, String nome, String estado, boolean isCapital, String tipoCliente){
        this.cpf_cnpj = cpf_cnpj;
        this.nome = nome;
        this.estado = estado;
        this.isCapital = isCapital;
        this.tipoCliente = tipoCliente;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"12345678900" , "Pedro Álvares Cabral", "SP", true, "prime"},
            {"98765432100", "João Terceiro da Lua", "MG", false, "padrao"},
            {"58963510200", "Sidney Fernando", "GO", false, "padrao"}
        });
    }

    @Test
    public void testCadastroCliente() {
        String[] inputs = {cpf_cnpj, nome, estado, isCapital ? "s" : "n", tipoCliente.equals("prime") ? "s" : "n"};
        sysCadastro.setScanner(new java.util.Scanner(String.join("\n", inputs) + "\n"));
        
        sysCadastro.cadastrarCliente();

        Cliente cliente = sysCadastro.getClientes().get(sysCadastro.getClientes().size() - 1);
        
        assertNotNull(cliente);
        assertEquals(cpf_cnpj, cliente.getCpfCnpj());
        assertEquals(nome, cliente.getNome());
        assertEquals(estado, cliente.getEndereco().getSiglaEstado());
        assertEquals(isCapital, cliente.getEndereco().isCapital());
        assertEquals(tipoCliente, cliente.getTipoCliente());

    }

}
