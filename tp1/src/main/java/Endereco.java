public class Endereco {
    private String siglaEstado;
    private String siglaRegiao;
    private boolean isCapital;

    public Endereco(String siglaEstado, String siglaRegiao, boolean isCapital){
        this.siglaEstado = siglaEstado;
        this.siglaRegiao = siglaRegiao;
        this.isCapital = isCapital;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public String getSiglaRegiao() {
        return siglaRegiao;
    }

    public void setSiglaRegiao(String siglaRegiao) {
        this.siglaRegiao = siglaRegiao;
    }

    public boolean isCapital() {
        return isCapital;
    }

    public void setCapital(boolean capital) {
        isCapital = capital;
    }
}
