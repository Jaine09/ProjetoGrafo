import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Jogo {
    public static void main(String[] args) {

        Grafo<String> grafo = new Grafo<String>();

        // --- SUSPEITOS PRINCIPAIS ---
        grafo.adicionarSuspeito("Arielly");
        grafo.adicionarSuspeito("Eduardo");
        grafo.adicionarSuspeito("Giulia");
        grafo.adicionarSuspeito("Camile");
        grafo.adicionarSuspeito("Alan");
        grafo.adicionarSuspeito("Jaine");

        // --- CARACTERÍSTICAS DE CADA PERSONAGEM ---
        grafo.adicionarCaracteristica("Arielly", "Calma e gentil, sempre tenta ajudar os outros. Ultimamente anda nervosa.");
        grafo.adicionarCaracteristica("Eduardo", "Trabalha com tecnologia. Observador e analítico, tenta resolver tudo com lógica.");
        grafo.adicionarCaracteristica("Giulia", "Extrovertida e distraída. Vive chegando atrasada nas reuniões.");
        grafo.adicionarCaracteristica("Camile", "Criativa e detalhista, costuma perceber o que ninguém mais nota.");
        grafo.adicionarCaracteristica("Alan", "Brincalhão, mas tem dificuldade em guardar segredos.");
        grafo.adicionarCaracteristica("Jaine", "Reservada e perfeccionista. Evita conflitos e prefere trabalhar sozinha.");

        // --- PISTAS (CONEXÕES ENTRE OS SUSPEITOS) ---
        grafo.adicionarPistas("O suspeito tem cabelo castanho.", "Eduardo", "Camile");
        grafo.adicionarPistas("O suspeito trabalha diretamente com a vítima.", "Camile", "Jaine");
        grafo.adicionarPistas("O suspeito parecia tenso ultimamente.", "Arielly", "Alan");
        grafo.adicionarPistas("O suspeito sempre quer ir embora primeiro.", "Giulia", "Arielly");
        grafo.adicionarPistas("O suspeito usa acessórios parecidos com os de Alan.", "Eduardo", "Alan");
        grafo.adicionarPistas("O suspeito demonstrava nervosismo durante as reuniões.", "Camile", "Eduardo");
        grafo.adicionarPistas("O suspeito fez comentários estranhos sobre o projeto.", "Alan", "Jaine");
        grafo.adicionarPistas("O suspeito e Giulia discutiram recentemente.", "Giulia", "Alan");
        grafo.adicionarPistas("O suspeito parece saber mais do que demonstra.", "Jaine", "Arielly");
        grafo.adicionarPistas("O suspeito tem informações que não deveria saber.", "Alan", "Camile");

        // --- LISTA DE SUSPEITOS PARA SORTEIO ---
        List<String> suspeitos = new ArrayList<>();
        suspeitos.add("Arielly");
        suspeitos.add("Eduardo");
        suspeitos.add("Giulia");
        suspeitos.add("Camile");
        suspeitos.add("Alan");
        suspeitos.add("Jaine");

        Random random = new Random();

        // --- SORTEAR CULPADO ALEATÓRIO ---
        String culpado = suspeitos.get(random.nextInt(suspeitos.size()));

        // --- ESCOLHER SUSPEITO INICIAL ALEATÓRIO ---
        Vertice<String> suspeitoAtual = grafo.getVertice(
                suspeitos.get(random.nextInt(suspeitos.size()))
        );

        // --- INÍCIO DO JOGO ---
        Scanner sc = new Scanner(System.in);
        boolean investigando = true;

        System.out.println("=== JOGO DO DETETIVE ===");
        System.out.println("Um sabotador causou um grande problema no projeto da equipe!");
        System.out.println("Você precisa descobrir quem é o culpado observando as pistas e características.\n");

        System.out.println("Você começa investigando: " + suspeitoAtual.getDado());
        System.out.println("Características: " + grafo.getCaracteristica(suspeitoAtual.getDado()));

        while (investigando) {
            ArrayList<Aresta<String>> pistas = suspeitoAtual.getArestaSaida();

            if (pistas.isEmpty()) {
                System.out.println("\n--- Nenhuma pista disponível ---");
                System.out.println("Parece que essa linha de investigação terminou.");
                break;
            }

            System.out.println("\nVocê está investigando: " + suspeitoAtual.getDado());
            System.out.println("Características: " + grafo.getCaracteristica(suspeitoAtual.getDado()));
            System.out.println("Pistas disponíveis:");

            for (int i = 0; i < pistas.size(); i++) {
                Aresta<String> pista = pistas.get(i);
                System.out.println((i + 1) + ": " + pista.getPista());
            }

            int escolha = 0;
            while (escolha < 1 || escolha > pistas.size()) {
                System.out.print("Escolha a pista (1-" + pistas.size() + "): ");
                try {
                    escolha = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Digite apenas números!");
                    sc.next();
                }
            }

            Aresta<String> pistaEscolhida = pistas.get(escolha - 1);
            suspeitoAtual = pistaEscolhida.getFim();

            System.out.println("\nA pista leva você até " + suspeitoAtual.getDado() + "...");
            System.out.println("-------------------------------------------------");

            // 🔍 Se o jogador encontrar o culpado, o jogo acaba
            if (suspeitoAtual.getDado().equalsIgnoreCase(culpado)) {
                System.out.println("\n🕵️ Você encontrou o(a) verdadeiro(a) culpado(a): " + culpado + "!");
                System.out.println("Excelente trabalho, detetive!");
                investigando = false;
                break;
            }
        }

        // --- ACUSAÇÃO FINAL (caso o jogador não tenha encontrado antes) ---
        if (investigando) {
            System.out.print("\nQuem você acha que é o culpado? ");
            sc.nextLine(); // limpar buffer
            String palpite = sc.nextLine().trim();

            if (palpite.equalsIgnoreCase(culpado)) {
                System.out.println("\n🎉 PARABÉNS! Você analisou bem as pistas e descobriu que " + culpado + " é o(a) verdadeiro(a) culpado(a)!");
            } else {
                System.out.println("\n❌ Não foi dessa vez!");
                System.out.println("Você acusou " + palpite + ", mas o(a) verdadeiro(a) culpado(a) era " + culpado + ".");
                System.out.println("Tente novamente e analise melhor as relações entre os suspeitos.");
            }
        }

        System.out.println("\nFim de jogo. Obrigado por jogar!");
        sc.close();
    }
}
