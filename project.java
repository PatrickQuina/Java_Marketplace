package Java_Marketplace;

import java.util.Scanner;
import java.lang.Integer;

public class project {
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

        while (!baseStart) {
            escolha = displayMenu();
            if (escolha == 1) {
                System.out.println("Base inicializada!");
                baseStart = true;
            } else {
                System.out.println("ERRO: A base deve ser inicializada antes de tudo!");
            }
            System.out.println();
        }

        String[] historicoIdsPedidos = new String[0];
        double[] historicoValoresPedidos = new double[0];
        String[][] historicoItensVendidos = new String[0][0];
        int[] vendaAtualIds = new int[0];
        int[] vendaAtualQtds = new int[0];

        do {
            escolha = displayMenu();
            switch (escolha) {
                case 1:
                    System.out.println("A base já foi inicializada!");
                    break;
                case 2:
                    exibirCatalogo(nomesProdutos, idsProdutos, precosProdutos, estoquesProdutos);
                    System.out.println();
                    break;
                case 3:
                    vendaAtualIds = addItemID(idsProdutos, estoquesProdutos, vendaAtualIds);
                    vendaAtualQtds = addItemQtd(vendaAtualQtds, estoquesProdutos, vendaAtualIds, idsProdutos);
                    estoquesProdutos = atualizaEstoque(vendaAtualIds, idsProdutos, vendaAtualQtds, estoquesProdutos);
                    System.out.println();
                    System.out.println();
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
                default:
                    System.out.println("ERRO: Opção Inválida!");
                    System.out.println();
            }
        } while (escolha != 0);
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

    public static void exibirCatalogo(String[] nomes, int[] idsProd, double[] precos, int[] estoque) {
        int emEstoque = 0;
        for (int q : estoque) {
            if (q > 0) {
                emEstoque++;
            }
        }
        int[] prodsEstoque = new int[emEstoque];
        int j = 0;
        for (int i = 0; i < estoque.length; i++) {
            if (estoque[i] > 0) {
                prodsEstoque[j] = i;
                j++;
            }
        }
        System.out.println();
        System.out.println("**********CATÁLOGO DE PRODUTOS**********");
        System.out.printf("%-3s | %-25s | %s\n", "ID", "PRODUTO", "PREÇO");
        System.out.println("----------------------------------------");
        for (int a : prodsEstoque) {
            System.out.printf("%d | %-25s | %.2f\n", idsProd[a], nomes[a], precos[a]);
        }
    }

    public static int[] addItemID(int[] idsProd, int[] estoqueProd, int[] atualIds) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            boolean idExists = false;
            int qtdIdEstoque = -1;
            int totalEstoque = 0;
            for(int a : estoqueProd){
                if(a > 0){
                    totalEstoque ++;
                }
            }
            if(totalEstoque == 0){
                System.out.println("ERRO: O estoque está totalmente vazio!");
                int[] emptyreturn = new int[atualIds.length];
                for(int i = 0; i < atualIds.length; i++){
                    emptyreturn[i] = atualIds[i];
                }
                return emptyreturn;
            }

            System.out.print("Digite o ID do produto: ");
            int addId = sc.nextInt();
            sc.nextLine();

            for (int i = 0; i < idsProd.length; i++) {
                if (addId == idsProd[i]) {
                    idExists = true;
                    qtdIdEstoque = estoqueProd[i];
                }
            }
            if (!idExists) {
                System.out.println("ERRO: Esse ID não existe no catálogo!");
                continue;
            } else if (qtdIdEstoque == 0) {
                System.out.println("ERRO: O produto não está mais em estoque!");
                continue;
            } else {
                int[] newAtualIds = new int[atualIds.length + 1];
                for (int i = 0; i < atualIds.length; i++) {
                    newAtualIds[i] = atualIds[i];
                }
                newAtualIds[atualIds.length] = addId;
                return newAtualIds;
            }
        }
    }

    public static int[] addItemQtd(int[] atualQtds, int[] estoqueAtual, int[] atualIds, int[] idsCatalogo) {
        int lastId = atualIds[atualIds.length - 1];
        int indexCatalog = -1;
        for (int i = 0; i < idsCatalogo.length; i++) {
            if (idsCatalogo[i] == lastId) {
                indexCatalog = i;
            }
        }
        int qtdEstoque = estoqueAtual[indexCatalog];

        while (true) {
            Scanner sc = new Scanner(System.in);
            int totalEstoque = 0;
            for(int a : estoqueAtual){
                if(a > 0){
                    totalEstoque ++;
                }
            }
            if(totalEstoque == 0){
                int[] emptyreturn = new int[atualQtds.length];
                for(int i = 0; i < atualQtds.length; i++){
                    emptyreturn[i] = atualQtds[i];
                }
                return emptyreturn;
            }

            System.out.print("Digite a Quantidade do produto: ");
            int addQtd = sc.nextInt();
            sc.nextLine();

            if (qtdEstoque - addQtd < 0) {
                System.out.println("ERRO: Estoque insuficiente!");
                System.out.printf("Há %d unidades desse produto disponíveis!\n", qtdEstoque);
                continue;
            } else {
                int[] newAtualQtds = new int[atualQtds.length + 1];
                for (int i = 0; i < atualQtds.length; i++) {
                    newAtualQtds[i] = atualQtds[i];
                }
                newAtualQtds[atualQtds.length] = addQtd;
                return newAtualQtds;
            }
        }
    }

    public static int[] atualizaEstoque(int[] atualIds, int[] idsCatalogo, int[] atualQtds, int[] estoqueAtual) {
        int lastId = atualIds[atualIds.length - 1];
        int lastQtd = atualQtds[atualQtds.length - 1];
        int getNewIndex = -1;
        int novaQtd;

        for (int i = 0; i < idsCatalogo.length; i++) {
            if (idsCatalogo[i] == lastId) {
                getNewIndex = i;
            }
        }
        novaQtd = estoqueAtual[getNewIndex] - lastQtd;

        int[] newEstoque = new int[estoqueAtual.length];
        for (int el = 0; el < estoqueAtual.length; el++) {
            newEstoque[el] = estoqueAtual[el];
        }
        if(novaQtd < 0){
            novaQtd = 0;
        }
        newEstoque[getNewIndex] = novaQtd;

        return newEstoque;
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