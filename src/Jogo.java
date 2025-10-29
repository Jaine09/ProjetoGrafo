
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


import java.util.*;

class Grafo<T> {
    private Map<T, List<String>> pistas = new HashMap<>();
    private Map<T, String> caracteristicas = new HashMap<>();

    public void adicionarSuspeito(T suspeito) {
        pistas.putIfAbsent(suspeito, new ArrayList<>());
    }

    public void adicionarCaracteristica(T suspeito, String descricao) {
        caracteristicas.put(suspeito, descricao);
    }

    public String getCaracteristica(T suspeito) {
        return caracteristicas.get(suspeito);
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

public class Jogo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        Grafo<String> grafo = new Grafo<>();

        String[] suspeitos = {"Alice", "Bob", "Carol", "David"};
        Map<String, String> caracteristicas = new HashMap<>();
        caracteristicas.put("Alice", "Usava chapéu vermelho e relógio antigo");
        caracteristicas.put("Bob", "Tem tatuagem no braço e gosta de música clássica");
        caracteristicas.put("Carol", "Estava perto do local do crime e tem luvas");
        caracteristicas.put("David", "Não tem álibi e coleciona facas");

        for (String s : suspeitos) {
            grafo.adicionarSuspeito(s);
            grafo.adicionarCaracteristica(s, caracteristicas.get(s));
        }

      
        grafo.adicionarPistas("Alice contou a Bob sobre o estranho barulho.", "Alice", "Bob");
        grafo.adicionarPistas("Bob viu Carol saindo do local do crime.", "Bob", "Carol");
        grafo.adicionarPistas("Carol suspeitou de David.", "Carol", "David");
        grafo.adicionarPistas("David mentiu sobre seu álibi para Alice.", "David", "Alice");

        System.out.println("Bem-vindo ao Jogo do Detetive!");
        System.out.println("Um crime aconteceu na mansão Sombria. Cabe a você descobrir o culpado!");

        boolean culpadoEncontrado = false;
        int rodada = 1;
        String culpado = suspeitos[random.nextInt(suspeitos.length)];

        while (!culpadoEncontrado) {
            System.out.println("\n--- Rodada " + rodada + " ---");

            System.out.println("Suspeitos e características:");
            for (String s : suspeitos) {
                System.out.println("• " + s + ": " + grafo.getCaracteristica(s));
            }

           
            List<String> opcoes = new ArrayList<>(Arrays.asList(suspeitos));
            Collections.shuffle(opcoes);
            String opcao1 = opcoes.get(0);
            String opcao2 = opcoes.get(1);

            System.out.println("\nQuem você quer interrogar?");
            System.out.println("1 - " + opcao1);
            System.out.println("2 - " + opcao2);
            System.out.print("Digite 1 ou 2: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            String suspeitoEscolhido = (escolha == 1) ? opcao1 : opcao2;

            
            System.out.println("\n Durante o interrogatório, " + suspeitoEscolhido + " revelou:");
            System.out.println(" \"" + grafo.getPistaAleatoria(suspeitoEscolhido) + "\"");

           
            System.out.print("\nVocê acha que " + suspeitoEscolhido + " é o culpado? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();

            if (resposta.equals("s")) {
                if (suspeitoEscolhido.equals(culpado)) {
                    System.out.println("\n Parabéns! Você encontrou o culpado: " + culpado + "!");
                    culpadoEncontrado = true;
                } else {
                    System.out.println("\n " + suspeitoEscolhido + " não é o culpado. Continue investigando!");
                }
            } else {
                System.out.println("\nVocê decidiu não acusar " + suspeitoEscolhido + " por enquanto...");
            }

            rodada++;
        }

        System.out.println("\n Fim do jogo. Obrigado por jogar o Jogo do Detetive!");
        scanner.close();
    }
}
