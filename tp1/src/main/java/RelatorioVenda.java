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

    public void atualizaVendas(List<Venda> novasVendas) {
        this.vendas = novasVendas;
        calculaVendasUltimoMes(); // Recalcula as vendas do ultimo mes ao atualizar o relatorio
    }

    // A refatoração do método calculaVendasUltimoMes abordou o mau-cheiro "método grande" dividindo-o em métodos menores e com responsabilidades específicas: dataUltimoMes, vendaUltimoMes e atualizaVendasCliente. Isso melhorou a legibilidade e a manutenção do código, tornando-o mais modular e fácil de entender. A clareza na função de cada método facilita futuras modificações e aprimora a testabilidade do código, resultando em uma base de código mais limpa e eficiente.
    public void calculaVendasUltimoMes() {
        Date[] periodoUltimoMes = dataUltimoMes();
        Date inicioMesAnterior = periodoUltimoMes[0];
        Date fimMesAnterior = periodoUltimoMes[1];

        vendasClientes.clear();

        for (Venda venda : vendas) {
            Cliente cliente = venda.getCliente();
            vendaUltimoMes(venda, inicioMesAnterior, fimMesAnterior);
            if(verificaClienteEspecial(cliente)){
                cliente.setTipoCliente("especial");
            }
        }


    }

    public Date[] dataUltimoMes(){
        Calendar inicioMesAnterior = Calendar.getInstance();
        inicioMesAnterior.add(Calendar.MONTH, -1);
        inicioMesAnterior.set(Calendar.DAY_OF_MONTH, 1);

        Calendar fimMesAnterior = Calendar.getInstance();
        fimMesAnterior.add(Calendar.MONTH, -1);
        fimMesAnterior.set(Calendar.DAY_OF_MONTH, fimMesAnterior.getActualMaximum(Calendar.DAY_OF_MONTH));

        return new Date[]{inicioMesAnterior.getTime(), fimMesAnterior.getTime()};
    }

    public void vendaUltimoMes(Venda venda, Date inicio, Date fim) {
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date dataVenda = formatoData.parse(venda.getData());
            if (!dataVenda.before(inicio) && !dataVenda.after(fim)) {
                atualizaVendasCliente(venda);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected void atualizaVendasCliente(Venda venda){
        Cliente cliente = venda.getCliente();
        double totalVenda = venda.calculaTotalNota();
        vendasClientes.put(cliente, vendasClientes.getOrDefault(cliente, 0.0) + totalVenda);
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
