import java.util.ArrayList;
import java.util.Scanner;

public class Jogo {
    public static void main(String[] args) {

        // --- Configuração do Grafo (AGORA MAIS COMPLEXO) ---

        Grafo<String> grafo = new Grafo<String>();
        // Suspeitos Principais
        grafo.adicionarSuspeito("Arielly");
        grafo.adicionarSuspeito("Eduardo"); // Ponto de partida
        grafo.adicionarSuspeito("Giulia");
        grafo.adicionarSuspeito("Camile");
        grafo.adicionarSuspeito("Alan");
        grafo.adicionarSuspeito("Jaine");   // A Culpada

        // --- NOVOS SUSPEITOS (Pistas Falsas / Distrações) ---
        grafo.adicionarSuspeito("Lucas");
        grafo.adicionarSuspeito("Beatriz");


        // --- ADICIONANDO AS PISTAS ---

        // Caminho Correto (O "caminho dourado" para a solução)
        grafo.adicionarPistas("O suspeito tem o cabelo castanho", "Eduardo", "Camile");
        grafo.adicionarPistas("O suspeito trabalha com a vítima", "Camile", "Jaine");

        // --- PISTAS FALSAS, DISTRAÇÕES E CICLOS ---

        // Pista "inútil" que não leva a lugar nenhum
        grafo.adicionarPistas("O suspeito sempre quer ir embora", "Giulia", "Arielly");

        // Pistas que levam a um BECO SEM SAÍDA (Dead End)
        grafo.adicionarPistas("O suspeito usa brincos como os de Alan", "Eduardo", "Alan");
        grafo.adicionarPistas("Alan disse que viu Lucas perto da cena", "Alan", "Lucas");
        // (Note que 'Lucas' não tem nenhuma pista saindo dele)

        grafo.adicionarPistas("Camile foi vista discutindo com Beatriz", "Camile", "Beatriz");
        // (Note que 'Beatriz' também não tem pistas saindo dela)

        // Criando um CICLO (Alan <-> Arielly)
        // O jogador pode ficar "preso" investigando os dois
        grafo.adicionarPistas("O suspeito sempre compra lanche na cantina", "Eduardo", "Arielly");
        grafo.adicionarPistas("Arielly disse que Alan estava agindo de modo estranho", "Arielly", "Alan");
        grafo.adicionarPistas("Alan insiste que Arielly sabe de mais alguma coisa", "Alan", "Arielly");


        // --- Lógica do Jogo Interativo (Exatamente como antes) ---

        Scanner sc = new Scanner(System.in);
        Vertice<String> suspeitoAtual = grafo.getVertice("Eduardo");
        String culpado = "Jaine";
        boolean investigando = true;

        System.out.println("=== JOGO DO DETETIVE ===");
        System.out.println("Você começa investigando: " + suspeitoAtual.getDado());
        System.out.println("Seu objetivo é encontrar o(a) culpado(a): " + culpado);
        System.out.println("Siga as pistas corretas!");

        while (investigando) {
            // 1. Verificar condição de vitória
            if (suspeitoAtual.getDado().equals(culpado)) {
                System.out.println("\n*** PARABÉNS! ***");
                System.out.println("Você seguiu as pistas e encontrou a culpada: " + suspeitoAtual.getDado());
                investigando = false;
                continue;
            }

            // 2. Obter pistas (arestas de saída)
            ArrayList<Aresta<String>> pistas = suspeitoAtual.getArestaSaida();

            // 3. Verificar condição de derrota (beco sem saída)
            if (pistas.isEmpty()) {
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println("Você chegou a um beco sem saída. O suspeito " + suspeitoAtual.getDado() + " não tem mais pistas para oferecer.");
                System.out.println("Parece que você seguiu uma pista falsa.");
                investigando = false;
                continue;
            }

            // 4. Mostrar opções para o usuário
            System.out.println("\nVocê está investigando: " + suspeitoAtual.getDado());
            System.out.println("Pistas disponíveis:");
            for (int i = 0; i < pistas.size(); i++) {
                Aresta<String> pista = pistas.get(i);
                System.out.println((i + 1) + ": " + pista.getPista() + " (Aponta para: " + pista.getFim().getDado() + ")");
            }

            // 5. Obter escolha do usuário
            int escolha = 0;
            while (escolha < 1 || escolha > pistas.size()) {
                System.out.print("Qual pista você quer seguir? (Digite o número 1-" + pistas.size() + "): ");
                try {
                    escolha = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Por favor, digite apenas o número da opção.");
                    sc.next();
                }
            }

            // 6. Atualizar o "suspeitoAtual" baseado na escolha
            Aresta<String> pistaEscolhida = pistas.get(escolha - 1);
            suspeitoAtual = pistaEscolhida.getFim();

            System.out.println("-------------------------------------------------");
            System.out.println("A pista '" + pistaEscolhida.getPista() + "' leva você até... " + suspeitoAtual.getDado());
        }

        System.out.println("Obrigado por jogar!");
        sc.close();
    }
}