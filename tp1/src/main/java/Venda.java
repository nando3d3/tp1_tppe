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

    
    public Venda(String data, Cliente cliente, String metodoPagamento){
        this.data = data;
        this.cliente = cliente;
        this.metodoPagamento = metodoPagamento;
        this.itens = new ArrayList<>();
        this.total = calculaTotalNota();
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
        double valorTotalNota;
        double totalPedido = calculaTotalPedido();
        double imposto = calculaImposto();
        double frete = valorFrete();
        double descontoPagamento = 1.0;

        
        if (cliente.getTipoCliente().equals("especial")) {
            totalPedido *= 0.90;
            if (metodoPagamento.equals("cartao_empresa")) {
                descontoPagamento = 0.90;
            }
            frete = frete * 0.70;
        } else if (cliente.getTipoCliente().equals("prime")) {
            if (metodoPagamento.equals("cartao_loja")) {
                totalPedido*= 0.95;
            }
            else{
                totalPedido *= 0.97;
            }
            frete = 0.0;
        }
    
        double valorTotal = totalPedido + imposto + frete;
        valorTotalNota = valorTotal * descontoPagamento;

        return valorTotalNota;
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
}
