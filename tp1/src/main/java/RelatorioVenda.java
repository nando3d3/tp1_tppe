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
        calculaVendasUltimoMes(); // Calcula as vendas do último mês ao criar o relatório
    }

    public void atualizaVendas(List<Venda> vendas) {
        this.vendas = vendas;
        calculaVendasUltimoMes(); // Recalcula as vendas do último mês ao atualizar o relatório
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

        for (Cliente cliente : vendasClientes.keySet()) {
            if (verificaClienteEspecial(cliente)) {
                cliente.setTipoCliente("especial");
            }
        }
    }

    public boolean verificaClienteEspecial(Cliente cliente) {
        if (cliente.getTipoCliente().equals("prime")) {
            return false;
        }
    
        Double totalVendasCliente = vendasClientes.get(cliente);
        return totalVendasCliente != null && totalVendasCliente >= 100;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public Map<Cliente, Double> getVendasClientes() {
        return vendasClientes;
    }
}
