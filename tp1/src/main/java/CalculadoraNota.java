public class CalculadoraNota {
    private double totalPedido;
    private double imposto;
    private double frete;
    private double descontoPagamento;

    private Venda venda;

    public CalculadoraNota(Venda venda){
        this.venda = venda;
        this.totalPedido = venda.calculaTotalPedido();
        this.imposto = venda.calculaImposto();
        this.frete = venda.valorFrete();
        this.descontoPagamento = 1.0;
    }

    public double calcularNota(){
        aplicarDesconto();
        aplicarFrete();
        aplicarCashBack();

        return (totalPedido + imposto + frete) * descontoPagamento;
    }

    private void aplicarDesconto() {
        if (venda.getCliente() instanceof ClienteEspecial){
            totalPedido *= 0.90;
            if (venda.getMetodoPagamento().equals("cartao_empresa")){
                descontoPagamento = 0.90;
            }
        }
    }

    private void aplicarFrete() {
        if(venda.getCliente() instanceof ClientePrime) {
            frete = 0.0;
        } else if (venda.getCliente() instanceof ClienteEspecial) {
            frete *= 0.70;
        }
    }

    private void aplicarCashBack(){
        if (venda.getCliente() instanceof ClientePrime){
            ClientePrime clientePrime = (ClientePrime) venda.getCliente();
            if (venda.getMetodoPagamento().equals("cartao_loja")) {
                clientePrime.setCashBack(clientePrime.getCashBack() + totalPedido * 0.05);
            } else {
                clientePrime.setCashBack(clientePrime.getCashBack() + totalPedido * 0.03);
            }
            if(venda.isUsarCashBack()){
                double cashback = clientePrime.getCashBack();
                if (cashback > totalPedido + imposto + frete){
                    cashback -= totalPedido + imposto + frete;
                    totalPedido = 0.0;
                } else {
                    totalPedido -= cashback;
                    cashback = 0.0;
                }
                clientePrime.setCashBack(cashback);
            }
        }
    }
}
