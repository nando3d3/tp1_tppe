import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioVenda {
    private List<Venda> vendas;
    private Map<Cliente, Double> vendasClientes;

    public RelatorioVenda(List<Venda> vendas) {
        this.vendas = vendas;
        this.vendasClientes = new HashMap<>();
    }

    public void atualizaVendas(List<Venda> vendas) {
        this.vendas = vendas;
        calculaVendasUltimoMes();
    }

    public void calculaVendasUltimoMes() {
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");

        Calendar mesAnterior = Calendar.getInstance();
        mesAnterior.add(Calendar.MONTH, -1);

        vendasClientes.clear();

        for (Venda venda : vendas) {
            try {
                Date dataVenda = formatoData.parse(venda.getData());
                if (dataVenda.after(mesAnterior.getTime())) {
                    Cliente cliente = venda.getCliente();
                    double totalVenda = venda.calculaTotalNota();
                    vendasClientes.put(cliente, vendasClientes.getOrDefault(cliente, 0.0) + totalVenda);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean verificaClienteEspecial(Cliente cliente) {
        Double totalVendasCliente = vendasClientes.get(cliente);
        if (totalVendasCliente != null && totalVendasCliente >= 100) {
            return true;
        }
        return false;
    }

    public void imprimeClientesEspeciais() {
        for (Map.Entry<Cliente, Double> cliente : vendasClientes.entrySet()) {
            if (cliente.getValue() >= 100) {
                Cliente especial = cliente.getKey();
                System.out.println("O cliente de nome " + especial.getNome() + 
                    " e cpf/cnpj de numero " + especial.getCpfCnpj() + 
                    "esta elegivel a ser especial.");
            }
        }
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public Map<Cliente, Double> getVendasClientes() {
        return vendasClientes;
    }
    
}
