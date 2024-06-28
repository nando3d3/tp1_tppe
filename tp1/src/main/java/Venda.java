import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Venda {
    private String data;
    private Cliente cliente;
    private String metodoPagamento;
    private List<ItemVenda> itens;
    
    public Venda(String data, Cliente cliente, String metodoPagamento){
        this.data = data;
        this.cliente = cliente;
        this.metodoPagamento = metodoPagamento;
        this.itens = new ArrayList<>();
    }

    public void addItem(Produto produto, int quantidade){
        if(quantidade > 0){
            this.itens.add(new ItemVenda(produto, quantidade));
        }
    }

    public double calculaTotal(){
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


}
