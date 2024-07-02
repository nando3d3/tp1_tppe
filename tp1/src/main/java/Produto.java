public class Produto {
    private String codigoItem;
    private String descricao;
    private double valorVenda;
    private String unidade;

    public Produto(String codigoItem, String descricao, double valorVenda, String unidade){
        if (codigoItem.isEmpty()){
            throw new IllegalArgumentException("O campo Código do item não pode ser vazio");
        }
        if (descricao.isEmpty()){
            throw new IllegalArgumentException("O campo Descrição não pode ser vazio");
        }
        if (valorVenda <= 0.0){
            throw new IllegalArgumentException("O valor de venda deve ser maior que zero");
        }
        if (unidade.isEmpty()){
            throw new IllegalArgumentException("O campo unidade não pode ser vazio");
        }   
        
        this.codigoItem = codigoItem;
        this.descricao  = descricao;
        this.valorVenda = valorVenda;
        this.unidade    = unidade;
    }

    public String getCodigoItem() {
        return codigoItem;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
}
