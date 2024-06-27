public class Produto {
    private String codigoItem;
    private String descricao;
    private double valorVenda;
    private String unidade;

    public Produto(String codigoItem, String descricao, double valorVenda, String unidade){
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
