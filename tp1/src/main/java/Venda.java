import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Venda {
    private String data;
    private Cliente cliente;
    private String metodoPagamento;
    private List<ItemVenda> itens;
    private double total;
    private boolean usarCashback;

    
    public Venda(String data, Cliente cliente, String metodoPagamento, boolean usarCashback){
        this.data = data;
        this.cliente = cliente;
        this.metodoPagamento = metodoPagamento;
        this.itens = new ArrayList<>();
        this.usarCashback = usarCashback;
    }

    public void addItem(Produto produto, int quantidade){
        if(quantidade > 0){    
            for (ItemVenda item: itens){
                if(item.getProduto().equals(produto)){
                    item.setQuantidade(item.getQuantidade() + quantidade);
                    return;
                }
            }
            this.itens.add(new ItemVenda(produto, quantidade));
        } 
    }

    public double calculaTotalPedido(){
        double valorTotal = 0.0;

        for(ItemVenda item : itens){
            valorTotal += item.getProduto().getValorVenda() * item.getQuantidade();
        }

        return valorTotal;
    }

    public double valorFrete(){
        double valorFrete = 0.0;

        boolean isCapital = cliente.getEndereco().isCapital();
        String siglaRegiao = cliente.getEndereco().getSiglaRegiao();

        Map<String, Double> fretePorRegiao = new HashMap<>();
            fretePorRegiao.put("DF", 5.00);
            fretePorRegiao.put("CO", isCapital ? 10.00 : 13.00);
            fretePorRegiao.put("NE", isCapital ? 15.00 : 18.00);
            fretePorRegiao.put("NO", isCapital ? 20.00 : 25.00);
            fretePorRegiao.put("SE", isCapital ? 7.00 : 10.00);
            fretePorRegiao.put("SU", isCapital ? 10.00 : 13.00);

        if (fretePorRegiao.containsKey(siglaRegiao)) {
            valorFrete = fretePorRegiao.get(siglaRegiao);
        }

        return valorFrete;
    }

    public double calculaImposto(){
        double taxaICMS;
        double taxaMunicipal;
        double totalImposto = 0.00;

        if(cliente.getEndereco().getSiglaEstado().equals("DF")){
            taxaICMS = 0.18;
            taxaMunicipal = 0.0;
        } else{
            taxaICMS = 0.12;
            taxaMunicipal = 0.04;
        }

        for(ItemVenda item : itens){
            double valorProduto = item.getProduto().getValorVenda();
            double impostoICMSItem = valorProduto*taxaICMS;
            double impostoMunicipalItem = valorProduto*taxaMunicipal;
            
            item.setValorImpostoICMS(impostoICMSItem);
            item.setValorImpostoMunicipal(impostoMunicipalItem);
            
            double impostoItem = item.getValorImpostoICMS() + item.getValorImpostoMunicipal();
            
            totalImposto += impostoItem * item.getQuantidade();
        }

        return totalImposto;
    }

    public double calculaTotalNota() {
        double totalPedido = calculaTotalPedido();
        double imposto = calculaImposto();
        double frete = valorFrete();
        double descontoPagamento = 1.0;

        if (cliente instanceof ClientePrime) {
            ClientePrime clientePrime = (ClientePrime) cliente;
            if (metodoPagamento.equals("cartao_loja")) {
                clientePrime.setCashBack(clientePrime.getCashBack() + totalPedido * 0.05);
            } else {
                clientePrime.setCashBack(clientePrime.getCashBack() + totalPedido * 0.03);
            }
            frete = 0.0;

            if (usarCashback) {
                double cashback = clientePrime.getCashBack();
                if (cashback > totalPedido + imposto + frete) {
                    cashback -= totalPedido + imposto + frete;
                    totalPedido = 0.0;
                } else {
                    totalPedido -= cashback;
                    cashback = 0.0;
                }
                clientePrime.setCashBack(cashback);
            }
        } else if (cliente instanceof ClienteEspecial) {
            totalPedido *= 0.90;
            if (metodoPagamento.equals("cartao_empresa")) {
                descontoPagamento = 0.90;
            }
            frete = frete * 0.70;
        }

        return (totalPedido + imposto + frete) * descontoPagamento;
    }
    
    public List<ItemVenda> getItens() {
        return itens;
    }

    public String getData() {
        return data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public double getTotal() {
        return total;
    }

    public boolean isUsarCashBack(){
        return usarCashback;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
