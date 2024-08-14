import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
    ClientePadraoTest.class,
    ClienteEspecialTest.class,
    ClientePrimeTest.class,
    EnderecoTest.class,
    ProdutoTest.class,
    ItemVendaTest.class,
    VendaTest.class,
    RelatorioVendaTest.class,
    CalculadoraNotaTest.class
})

public class AllTests {
    
}