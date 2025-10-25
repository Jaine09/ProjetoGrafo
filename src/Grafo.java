import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Grafo<TIPO>{
    private ArrayList<Vertice<TIPO>> suspeito;
    private ArrayList<Aresta<TIPO>> pistas;

    public Grafo() {
        this.suspeito = new ArrayList<Vertice<TIPO>>();
        this.pistas = new ArrayList<Aresta<TIPO>>();
    }

    public void adicionarSuspeito(TIPO dado){
        Vertice<TIPO> novoSuspeito = new Vertice<TIPO>(dado);
        this.suspeito.add(novoSuspeito);
    }

    public void adicionarPistas(String pista, TIPO dadoInicio, TIPO dadoFim){
        Vertice<TIPO> inicio = this.getVertice(dadoInicio);
        Vertice<TIPO> fim = this.getVertice(dadoFim);
        Aresta<TIPO> aresta = new Aresta<TIPO>(pista,inicio,fim);
        inicio.adicionarArestaSaida(aresta);
        fim.adicionarArestaEntrada(aresta);
        this.pistas.add(aresta);
    }

    public Vertice<TIPO> getVertice(TIPO dado){
        Vertice<TIPO> vertice = null;
        for (int i=0; i < this.suspeito.size(); i++){
            if (this.suspeito.get(i).getDado().equals(dado)){
                vertice = this.suspeito.get(i);
                break;
            }
        }
        return vertice;
    }

    public boolean encontrarCulpado(TIPO inicio, TIPO culpado) {
        Vertice<TIPO> vInicio = getVertice(inicio);
        Vertice<TIPO> vCulpado = getVertice(culpado);
        if (vInicio == null || vCulpado == null) return false;
        return buscar(vInicio, vCulpado, new HashSet<>());
    }

    private boolean buscar(Vertice<TIPO> atual, Vertice<TIPO> alvo, Set<Vertice<TIPO>> visitados) {
        if (atual.equals(alvo)) return true;
        visitados.add(atual);
        for (Aresta<TIPO> a : atual.getArestaSaida()) {
            if (!visitados.contains(a.getFim()) && buscar(a.getFim(), alvo, visitados)) {
                return true;
            }
        }
        return false;
    }

}
