import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AppTest {
    private Cliente cliente;
    private Produto produto;
    private Venda venda;
    private List<Cliente> clientes;
    private List<Produto> produtos;
    private List<Venda> vendas;

    @Before
    public void inicializaTeste() {
        clientes = new ArrayList<>();
        produtos = new ArrayList<>();
        vendas = new ArrayList<>();

        Endereco endereco = new Endereco("DF", true);
        cliente = new Cliente(0, "16546", "Fulano", endereco, "padrao");
        clientes.add(cliente);

        produto = new Produto("0000", "Arroz", 20.0, "kg");
        produtos.add(produto);

        venda = new Venda("02-05-2024", cliente, "pix");
        venda.addItem(produto, 6);
        vendas.add(venda);
    }

    @Test
    public void testCadastrarCliente(){
        assertEquals(1, clientes.size());
        assertEquals(0, clientes.get(0).getId());
        assertEquals("16546", clientes.get(0).getCpfCnpj());
        assertEquals("Fulano", clientes.get(0).getNome());
        assertEquals("padrao", clientes.get(0).getTipoCliente());
        assertEquals("DF", clientes.get(0).getEndereco().getSiglaEstado());
    }

    @Test
    public void testCadastrarProduto(){
        assertEquals(1, produtos.size());
        assertEquals("0000", produtos.get(0).getCodigoItem());
        assertEquals("Arroz", produtos.get(0).getDescricao());
        assertEquals("kg", produtos.get(0).getUnidade());
    }

    @Test
    public void testRealizarVenda(){
        assertEquals(1, vendas.size());
        assertEquals(cliente, vendas.get(0).getCliente());
    }

    @Test
    public void testCalcularVendasUltimoMes() {
        String identificadorCliente = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date mesAnterior = cal.getTime();

        double totalVendas = 0.0;
        for (Cliente cliente: clientes) {
            if (cliente.getCpfCnpj().equalsIgnoreCase("16546")){
                totalVendas = vendas.stream()
                        .filter(v -> {
                            try {
                                Date dataVenda = dateFormat.parse(v.getData());
                                return dataVenda.after(mesAnterior);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return false;
                            }
                        })
                        .filter(v -> v.getCliente().equals(cliente))
                        .mapToDouble(Venda::calculaTotalNota)
                        .sum();

                if (totalVendas > 100.0) {
                    System.out.println("Cliente " + 
                        cliente.getNome() + 
                        " com cpf/cnpj: " + 
                        cliente.getCpfCnpj() + 
                        ", voce esta elegivel para se tornar cliente especial.");
                    identificadorCliente =  cliente.getCpfCnpj();
                }
                else {
                    identificadorCliente =  "";
                }
            }
        }

        assertEquals("16546", identificadorCliente);
    }
   
}