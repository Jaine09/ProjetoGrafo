import java.util.*;

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
        grafo.adicionarMotivo("Alice", "Ia ser demitida pelo Sr. Arthur e estava desesperada para manter o emprego.");
        grafo.adicionarMotivo("Bob", "Tinha um caso com a esposa da vítima e temia ser descoberto.");
        grafo.adicionarMotivo("Carol", "Herdaria parte da fortuna do Sr. Arthur se ele morresse.");
        grafo.adicionarMotivo("David", "Foi acusado de roubar uma peça rara da coleção do Sr. Arthur.");


        for (String s : suspeitos) {
            grafo.adicionarSuspeito(s);
            grafo.adicionarCaracteristica(s, caracteristicas.get(s));
        }

        Map<String, List<String>> pistasMostradas = new HashMap<>();
        for (String s : suspeitos) {
            pistasMostradas.put(s, new ArrayList<>());
        }

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("  O Mistério da Propriedade Blackwell");
        System.out.println("╚══════════════════════════════════════╝\n");
        System.out.println("Na noite passada, um grito ecoou pelos corredores da Mansão...");
        System.out.println("O corpo do Sr. Arthur Lancaster foi encontrado em seu escritório — a porta estava trancada por dentro.");
        System.out.println("O relógio antigo da sala parou exatamente às 23h47.");
        System.out.println("\nQuatro pessoas estavam na mansão naquela noite — e todas têm algo a esconder.");
        System.out.println("Você, o detetive encarregado, deve desvendar o mistério antes que o culpado escape.");
        System.out.println("Observe os detalhes, questione os suspeitos e siga as pistas com cuidado...\n");



        boolean culpadoEncontrado = false;
        int rodada = 1;
        String culpado = suspeitos[random.nextInt(suspeitos.length)];

        String ultimoInterrogado = null;

        while (!culpadoEncontrado) {
            System.out.println("\n--- Rodada " + rodada + " ---");

            System.out.println("Suspeitos e informações:");
            for (String s : suspeitos) {
                System.out.println("• " + s + ": " + grafo.getCaracteristica(s));
                System.out.println("  Possível Motivo: " + grafo.getMotivo(s));
            }


            List<String> opcoes = new ArrayList<>(Arrays.asList(suspeitos));
            if (ultimoInterrogado != null) {
                opcoes.remove(ultimoInterrogado);
            }
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
            ultimoInterrogado = suspeitoEscolhido;


            List<String> jaMostradas = pistasMostradas.get(suspeitoEscolhido);
            String novaPista = gerarNovaPistaUnica(suspeitoEscolhido, culpado, jaMostradas);

            System.out.println("\nDurante o interrogatório, " + suspeitoEscolhido + " revelou:");
            System.out.println(" \"" + novaPista + "\"");


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

    private static String gerarNovaPistaUnica(String suspeito, String culpado, List<String> jaMostradas) {
        Random random = new Random();
        String[] todasPistas;

        switch (suspeito) {
            case "Alice":
                todasPistas = suspeito.equals(culpado) ?
                        new String[]{
                                "Alice disse que viu Bob escondendo algo no bolso, mas pareceu hesitar ao contar.",
                                "Alice afirmou que ouviu passos vindos do escritório, mas evitou comentar mais detalhes.",
                                "Alice mencionou que limpou uma taça quebrada — a mesma que desapareceu depois."
                        } :
                        new String[]{
                                "Alice comentou que viu " + culpado + " perto do escritório logo antes do grito.",
                                "Alice jurou que estava na cozinha, mas ouviu o som de algo caindo às 23h47.",
                                "Alice disse que achou o comportamento de " + culpado + " estranho durante o jantar."
                        };
                break;
            case "Bob":
                todasPistas = suspeito.equals(culpado) ?
                        new String[]{
                                "Bob garantiu que estava tocando piano às 23h47, mas ninguém ouviu música naquela noite.",
                                "Bob disse que Carol parecia agitada e trocou de roupa duas vezes durante a noite.",
                                "Bob comentou que David o ameaçou dias antes — mas parece ter inventado parte da história."
                        } :
                        new String[]{
                                "Bob comentou que ouviu " + culpado + " caminhando pelo corredor logo após o grito.",
                                "Bob afirmou que o piano desafinou por causa de uma batida forte — talvez algo tenha caído no andar de cima.",
                                "Bob disse que Alice parecia muito nervosa durante o jantar."
                        };
                break;
            case "Carol":

                todasPistas = suspeito.equals(culpado) ?
                        new String[]{
                                "Carol afirmou que viu Alice perto do escritório, mas Alice jura que estava na cozinha.",
                                "Carol disse ter ouvido Bob discutir com o Sr. Arthur, mas ninguém mais confirma isso.",
                                "Carol comentou que David estava limpando algo com um pano, mas o objeto nunca foi encontrado."
                        } :
                        new String[]{
                                "Carol contou que viu " + culpado + " mexendo no cofre do Sr. Arthur antes do jantar.",
                                "Carol afirmou que as luvas dela sumiram naquela noite — e alguém pode ter usado isso contra ela.",
                                "Carol disse que ouviu passos pesados vindo do corredor principal."
                        };
                break;
            case "David":
                todasPistas = suspeito.equals(culpado) ?
                        new String[]{
                                "David afirmou que Alice estava muito nervosa e evitava olhar para a sala do crime.",
                                "David contou que Bob pediu silêncio sobre algo que viu no corredor.",
                                "David disse que Carol mexeu no cofre do Sr. Arthur na noite anterior."
                        } :
                        new String[]{
                                "David comentou que viu " + culpado + " segurando algo metálico antes do grito.",
                                "David afirmou que ouviu um som de vidro quebrando, mas não achou nenhum estilhaço.",
                                "David disse que Alice estava no jardim antes do grito, mas voltou pálida e calada."
                        };
                break;
            default:
                return "Nada de novo foi revelado.";
        }


        List<String> disponiveis = new ArrayList<>();
        for (String p : todasPistas) {
            if (!jaMostradas.contains(p)) {
                disponiveis.add(p);
            }
        }


        if (disponiveis.isEmpty()) {
            jaMostradas.clear();
            disponiveis.addAll(Arrays.asList(todasPistas));
        }

        String escolhida = disponiveis.get(random.nextInt(disponiveis.size()));
        jaMostradas.add(escolhida);
        return escolhida;
    }
}
