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
        calculaVendasUltimoMes(); // Calcula as vendas do utimo mes ao criar o relatorio
    }

    public void atualizaVendas(List<Venda> vendas) {
        this.vendas = vendas;
        calculaVendasUltimoMes(); // Recalcula as vendas do ultimo mes ao atualizar o relatorio
    }

    public void calculaVendasUltimoMes() {
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");

        Calendar inicioMesAnterior = Calendar.getInstance();
        inicioMesAnterior.add(Calendar.MONTH, -1);
        inicioMesAnterior.set(Calendar.DAY_OF_MONTH, 1);

        Calendar fimMesAnterior = Calendar.getInstance();
        fimMesAnterior.add(Calendar.MONTH, -1);
        fimMesAnterior.set(Calendar.DAY_OF_MONTH, fimMesAnterior.getActualMaximum(Calendar.DAY_OF_MONTH));

        vendasClientes.clear();

        for (Venda venda : vendas) {
            try {
                Date dataVenda = formatoData.parse(venda.getData());
                if (!dataVenda.before(inicioMesAnterior.getTime()) && !dataVenda.after(fimMesAnterior.getTime())) {
                    Cliente cliente = venda.getCliente();
                    double totalVenda = venda.calculaTotalNota();
                    vendasClientes.put(cliente, vendasClientes.getOrDefault(cliente, 0.0) + totalVenda);
                    if (totalVenda >= 100 && !cliente.getTipoCliente().equals("prime") ) {
                        cliente.setTipoCliente("especial");
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }

    public boolean verificaClienteEspecial(Cliente cliente) {
        if (cliente.getTipoCliente() == "prime") return false;
        
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
