public class Aresta<TIPO> {

    private String pista;
    private Vertice<TIPO> inicio;
    private Vertice<TIPO> fim;

    public Aresta(String pista, Vertice<TIPO> inicio, Vertice<TIPO> fim) {
        this.pista = pista;
        this.inicio = inicio;
        this.fim = fim;
    }

    public String getPista() {
        return pista;
    }

    public void setPista(String pista) {
        this.pista = pista;
    }


    public Vertice<TIPO> getInicio() {
        return inicio;
    }

    public void setInicio(Vertice<TIPO> inicio) {
        this.inicio = inicio;
    }

    public Vertice<TIPO> getFim() {
        return fim;
    }

    public void setFim(Vertice<TIPO> fim) {
        this.fim = fim;
    }

    @Override
    public String toString() {
        return inicio.getDado() + " --(" + pista + ")--> " + fim.getDado();
    }

}