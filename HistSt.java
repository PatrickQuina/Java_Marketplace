package project_java.Java_Marketplace;

import java.util.Scanner;
import java.lang.Integer;

public class HistSt {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Cria o Scanner
        boolean baseStart = false; // Define verificador de inicialização das bases
        int escolha; // Cria variável que recebe opção do usuário

        String[] nomesProdutos = {
                "Teclado Gamer", // id = 101 | preço = 250.00 | estoque = 8
                "Mouse Gamer", // id = 102 | preço = 200.50 | estoque = 10
                "Monitor Gamer", // id = 103 | preço = 520.50 | estoque = 5
                "Headset Gamer", // id = 104 | preço = 130.50 | estoque = 7
                "Teclado de Escritório" // id = 105 | preço = 120.00 | estoque = 3
        };
        int[] idsProdutos = { 101, 102, 103, 104, 105 }; // Id dos produtos em catálogo
        double[] precosProdutos = { 250.00, 200.50, 520.50, 130.50, 120.00 }; // Preços dos produtos em catálogo
        int[] estoquesProdutos = { 8, 10, 5, 7, 3 }; // Estoque dos produtos em catálogo

        String[] historicoIdsPedidos = new String[0];
        double[] historicoValoresPedidos = new double[0];
        String[][] historicoItensVendidos = new String[0][0];

        while (!baseStart) {
            escolha = displayMenu();
            if (escolha == 1) {
                baseStart = true;
                System.out.println("inicializaBase()");
                System.out.println("Base inicializada!");
            } else {
                System.out.println("ERRO: A base deve ser inicializada antes de tudo!");
            }
            System.out.println();
        }

        while (true) {
            escolha = displayMenu();
            switch (escolha) {
                case 1:
                    System.out.println("A base já foi inicializada!");
                    break;
                case 2:
                    System.out.println("catalogoProdutos()");
                    break;
                case 3:
                    System.out.println("adicionarNoCarrinho()");
                    break;
                case 4:
                    System.out.println("resumoVenda()");
                    break;
                case 5:
                    System.out.println("finalizarVenda()");
                    break;
                case 6:
                    System.out.println("historicoVendas()");
                    break;
                case 7:
                    System.out.println("buscaVendaEspecifica()");
                    break;
                case 8:
                    System.out.println("reporEstoque()");
                    break;
                case 9:
                    System.out.println("relatorioEstoqueBaixo()");
                    break;
            }
        }
    }

    public static int displayMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Olá, seja bem vindo ao MackShop!");
        System.out.println("Escolha uma das opções abaixo:");
        System.out.println("1 - Inicializar base");
        System.out.println("2 - Exibir catálogo de produtos");
        System.out.println("3 - Adicionar item à venda");
        System.out.println("4 - Ver resumo da venda atual");
        System.out.println("5 - Finalizar venda");
        System.out.println("6 - Ver histórico de vendas");
        System.out.println("7 - Buscar venda específica do histórico");
        System.out.println("8 - (Admin) Repor estoque");
        System.out.println("9 - (Admin) Relatório de estoque baixo");

        System.out.print("---- Opção: ");
        int escolha = sc.nextInt();

        return escolha;
    }

    public static String[] defIdPedido(String[] histIds) {
        int idNum;
        String newId;
        String[] newHistIds;

        if (histIds.length == 0) {
            idNum = 1;
        } else {
            idNum = Integer.parseInt(histIds[histIds.length - 1]) + 1;
        }

        newHistIds = new String[histIds.length + 1]; // AI Help
        newId = String.format("%04d", idNum); // AI Help

        if (histIds.length > 0) {
            for (int i = 0; i < histIds.length; i++) {
                newHistIds[i] = histIds[i];
            }
        }
        newHistIds[histIds.length] = newId;
        return newHistIds;
    }

    public static double[] defValTotal(double[] histVals) {
        Scanner sc = new Scanner(System.in);
        double valorVenda;
        double[] newTotalVls;

        System.out.println("Digite o valor total: ");
        valorVenda = sc.nextDouble();
        sc.nextLine();

        newTotalVls = new double[histVals.length + 1];
        for (int i = 0; i < histVals.length; i++) {
            newTotalVls[i] = histVals[i];
        }
        newTotalVls[histVals.length] = valorVenda;
        return newTotalVls;
    }

    public static String[][] defHistItems(String[][] histIts, String[] histIdsPed, int contId) {
        Scanner sc = new Scanner(System.in);
        String prodId;
        int vendQtd;
        int newMatrizSize = histIts.length + 1;
        String[][] newHistItems = new String[newMatrizSize][3];

        System.out.println("Digite o ID de produto: ");
        prodId = sc.nextLine();
        System.out.println("Digite a qtd do produto: ");
        vendQtd = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < histIts.length; i++) {
            for (int j = 0; j < histIts[0].length; j++) {
                newHistItems[i][j] = histIts[i][j];
            }
        }

        String pedId = histIdsPed[contId];
        newHistItems[newMatrizSize - 1][0] = pedId;
        newHistItems[newMatrizSize - 1][1] = prodId;
        newHistItems[newMatrizSize - 1][2] = String.valueOf(vendQtd);

        return newHistItems;
    }
}