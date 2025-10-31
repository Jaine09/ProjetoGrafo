
import java.util.HashMap;
import java.util.Random;

import java.util.*;

class Grafo<T> {

    private Map<T, List<String>> pistas = new HashMap<>();
    private Map<T, String> caracteristicas = new HashMap<>();
    private Map<T, String> motivos = new HashMap<>();

    public void adicionarSuspeito(T suspeito) {
        pistas.putIfAbsent(suspeito, new ArrayList<>());
    }

    public void adicionarCaracteristica(T suspeito, String descricao) {
        caracteristicas.put(suspeito, descricao);
    }

    public void adicionarMotivo(T suspeito, String motivo) {
        motivos.put(suspeito, motivo);
    }

    public String getCaracteristica(T suspeito) {
        return caracteristicas.get(suspeito);
    }

    public String getMotivo(T suspeito) {
        return motivos.get(suspeito);
    }

    public void adicionarPistas(String pista, T de, T para) {
        pistas.get(de).add("De " + de + " para " + para + ": " + pista);
    }

    public String getPistaAleatoria(T suspeito) {
        List<String> lista = pistas.get(suspeito);
        if (lista == null || lista.isEmpty()) return "Nenhuma pista disponível.";
        Random random = new Random();
        return lista.get(random.nextInt(lista.size()));
    }
}

