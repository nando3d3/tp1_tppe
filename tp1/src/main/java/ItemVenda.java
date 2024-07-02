public class ItemVenda {
    private Produto produto;
    private int quantidade;
    private double valorImpostoICMS;
    private double valorImpostoMunicipal;

    public ItemVenda(Produto produto, int quantidade){
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public void setProduto(Produto produto){
        this.produto = produto;
    }

    public Produto getProduto(){
        return this.produto;
    }

    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }

    public int getQuantidade(){
        return this.quantidade;
    }

    public void setValorImpostoICMS(double valorImpostoICMS){
        this.valorImpostoICMS = valorImpostoICMS;
    }

    public double getValorImpostoICMS(){
        return this.valorImpostoICMS;
    }

    public void setValorImpostoMunicipal(double valorImpostoMunicipal){
        this.valorImpostoMunicipal = valorImpostoMunicipal;
    }

    public double getValorImpostoMunicipal(){
        return this.valorImpostoMunicipal;
    }
}
