import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
    ClienteTest.class, 
    EnderecoTest.class,
    ProdutoTest.class,
    ItemVendaTest.class,
    VendaTest.class,
    RelatorioVendaTest.class
})
public class AllTests {

}
