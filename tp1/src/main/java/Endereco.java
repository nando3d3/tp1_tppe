public class Endereco {
    private String siglaEstado;
    private String siglaRegiao;
    private boolean isCapital;

    public Endereco(String siglaEstado, boolean isCapital){
        this.siglaEstado = siglaEstado;
        this.siglaRegiao = definirRegiao(this.siglaEstado);
        this.isCapital = isCapital;
    }

    private String definirRegiao(String estado) {
        switch (estado) {
            case "AC": case "AM": case "AP": case "PA": case "RO": case "RR": case "TO":
                return "NO";
            case "AL": case "BA": case "CE": case "MA": case "PB": case "PE": case "PI": case "RN": case "SE":
                return "NE";
            case "DF": case "GO": case "MT": case "MS":
                return "CO";
            case "ES": case "MG": case "RJ": case "SP":
                return "SE";
            case "PR": case "RS": case "SC":
                return "SU";
            default: return "";
        }
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

    public boolean isCapital() {
        return isCapital;
    }

    public void setCapital(boolean capital) {
        isCapital = capital;
    }
}
