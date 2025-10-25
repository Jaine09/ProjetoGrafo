import java.util.Scanner;

public class Jogo {
    public static void main(String[] args) {
        //Adicionar personagens
        Grafo<String> grafo = new Grafo<String>();
        grafo.adicionarSuspeito("Arielly");
        grafo.adicionarSuspeito("Eduardo");
        grafo.adicionarSuspeito("Giulia");
        grafo.adicionarSuspeito("Camile");
        grafo.adicionarSuspeito("Alan");
        grafo.adicionarSuspeito("Jaine");

        grafo.adicionarPistas("O suspeito tem o cabelo castanho","Eduardo","Camile");
        grafo.adicionarPistas("O suspeito sempre compra lanche","Eduardo","Arielly");
        grafo.adicionarPistas("O suspeito usa brincos", "Eduardo","Alan");
        grafo.adicionarPistas("O suspeito trabalha","Camile", "Jaine");
        grafo.adicionarPistas("O suspeito sempre quer ir embora", "Giulia", "Arielly");

        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do suspeito que deseja investigar:");
        String nome = sc.nextLine();
        Vertice<String> suspeito = grafo.getVertice(nome);

        if (suspeito != null) {
            for (Aresta<String> a : suspeito.getArestaSaida()) {
                System.out.println("Pista: " + a.getPista() + " → leva a " + a.getFim().getDado());
            }
        } else {
            System.out.println("Suspeito não encontrado!");
        }

        if (grafo.encontrarCulpado("Eduardo", "Jaine")) {
            System.out.println("Há uma sequência de pistas que leva até o culpado!");
        } else {
            System.out.println("Nenhum caminho leva até o culpado...");
        }

    }
}
