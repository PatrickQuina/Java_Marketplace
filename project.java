package Java_Marketplace;

import java.util.Scanner;
import java.lang.Integer;
import java.time.LocalDateTime; // AI Help
import java.time.format.DateTimeFormatter; // AI Help

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
                    resumoVenda(vendaAtualIds, vendaAtualQtds, idsProdutos, nomesProdutos, precosProdutos);
                    break;
                case 5:
                    if(vendaAtualIds.length == 0){
                        System.out.println("ERRO: Não há venda sendo realizada no momento!");
                    } else{
                        System.out.println("finalizarVenda()");
                        historicoIdsPedidos = defIdPedido(historicoIdsPedidos);
                        historicoValoresPedidos = defValTotal(historicoValoresPedidos, vendaAtualIds, idsProdutos, vendaAtualQtds, precosProdutos);
                        historicoItensVendidos = defHistItems(historicoItensVendidos, historicoIdsPedidos, vendaAtualIds, vendaAtualQtds);
                        finalizarVenda(historicoIdsPedidos, idsProdutos, nomesProdutos, vendaAtualIds, vendaAtualQtds, precosProdutos, historicoValoresPedidos);
                        vendaAtualIds = new int[0];
                        vendaAtualQtds = new int[0];
                    }
                    break;
                case 6:
                    historicoVendas(historicoIdsPedidos, historicoValoresPedidos);
                    break;
                case 7:
                    buscaVendaEspecifica(historicoIdsPedidos, historicoValoresPedidos, historicoItensVendidos, idsProdutos, nomesProdutos, precosProdutos);
                    break;
                case 8:
                    System.out.println("reporEstoque()");
                    break;
                case 9:
                    relatorioEstoqueBaixo(estoquesProdutos, nomesProdutos);
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
            for (int a : estoqueProd) {
                if (a > 0) {
                    totalEstoque++;
                }
            }
            if (totalEstoque == 0) {
                System.out.println("ERRO: O estoque está totalmente vazio!");
                int[] emptyreturn = new int[atualIds.length];
                for (int i = 0; i < atualIds.length; i++) {
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
            for (int a : estoqueAtual) {
                if (a > 0) {
                    totalEstoque++;
                }
            }
            if (totalEstoque == 0) {
                int[] emptyreturn = new int[atualQtds.length];
                for (int i = 0; i < atualQtds.length; i++) {
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
    //Parte 4
    public static void resumoVenda(int[] vendaIds, int[] vendaQtds, int[] idsCatalogo, String[] nomes, double[] precos) {
        if (vendaIds.length == 0) {
            System.out.println("Nenhum item adicionado à venda atual.");
            return;
        }

        System.out.println("********** RESUMO DA VENDA ATUAL **********");
        System.out.printf("%-25s | %-5s | %-10s | %-10s\n", "PRODUTO", "QTD", "PREÇO", "SUB/"); // Ia para formatar
        System.out.println("-------------------------------------------------------------");

        double total = 0.0;

        for (int i = 0; i < vendaIds.length; i++) {
            int id = vendaIds[i];
            int qtd = vendaQtds[i];
            int index = -1;

        // Procura o índice do produto no catálogo
            for (int j = 0; j < idsCatalogo.length; j++) {
                if (idsCatalogo[j] == id) {
                index = j;
                break;
            }
        }

        if (index != -1) {
            String nome = nomes[index];
            double preco = precos[index];
            double subtotal = preco * qtd;
            total += subtotal;

            System.out.printf("%-25s | %-5d | R$ %-8.2f | R$ %-8.2f\n", nome, qtd, preco, subtotal);
        }
        System.out.println("-------------------------------------------------------------");
        System.out.printf("TOTAL DA VENDA: R$ %.2f\n", total);
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
        if (novaQtd < 0) {
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

    public static double[] defValTotal(double[] histVals, int[] atualIds, int[] idsCatalogo, int[] atualQtds, double[] precos) {
        double valorTotal = 0;
        double[] newTotalVls;
        int getQtdIndex = 0;

        for(int id : atualIds){
            int qtdProd = atualQtds[getQtdIndex];
            getQtdIndex ++;
            int getIndex = -1;
            for(int i = 0; i < idsCatalogo.length; i++){
                if(idsCatalogo[i] == id){
                    getIndex = i;
                }
            }
            double valorProd = precos[getIndex] * qtdProd;
            valorTotal += valorProd;
        }

        newTotalVls = new double[histVals.length + 1];
        for (int i = 0; i < histVals.length; i++) {
            newTotalVls[i] = histVals[i];
        }
        newTotalVls[histVals.length] = valorTotal;
        return newTotalVls;
    }

    public static String[][] defHistItems(String[][] histItems, String[] histIdsPed, int[] atualIds, int[] atualQtds) {
        String pedId = histIdsPed[histIdsPed.length - 1];
        int toAdd = atualIds.length;
        int newMatrizSize = histItems.length + toAdd;
        String[][] newHistItems = new String[newMatrizSize][3];

        for (int i = 0; i < histItems.length; i++) {
            for (int j = 0; j < histItems[0].length; j++) {
                newHistItems[i][j] = histItems[i][j];
            }
        }

        for (int c = 0; c < toAdd; c++){
            newHistItems[histItems.length + c][0] = pedId;
            newHistItems[histItems.length + c][1] = String.valueOf(atualIds[c]);
            newHistItems[histItems.length + c][2] = String.valueOf(atualQtds[c]);
        }

        return newHistItems;
    }

    public static void historicoVendas(String[] newHistIds, double[] newTotalVls){

        if(newHistIds.length == 0){
            System.out.println("Nenhuma venda foi registrada!");
        }
        else{
            for (int i = 0; i < newHistIds.length; i++){
                System.out.println("Pedido ID: " + newHistIds[i] + " | Valor total: R$ "+ newTotalVls[i]);
            }
        }
    }

    public static void finalizarVenda(String[] histIdsPed, int[] idsCatalogo, String[] nomesCatalogo, int[] atualIds, int[] atualQtds, double[] precos, double[] histValPeds) {
        String pedId = histIdsPed[histIdsPed.length - 1]; // ID do pedido
        double pedVal = histValPeds[histValPeds.length - 1]; // Valor total do pedido
        String line = "*********************************************************************************************";
        String line2 = "---------------------------------------------------------------------------------------------";
        int contRow = 1; // Índice linha

        // Define data de emissão no modelo desejado
        LocalDateTime agora = LocalDateTime.now(); // AI Help
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // AI Help
        String dataFormatada = agora.format(formatter); // AI Help

        // Imprime nota fiscal no modelo esperado
        System.out.println(line);
        System.out.printf("* %-90s*\n", "MACKSHOP");
        System.out.printf("* %-90s*\n", "CNPJ: 12.345.678/0001-99");
        System.out.println(line);
        System.out.printf("* %-90s*\n", "NOTA FISCAL - VENDA AO CONSUMIDOR");
        System.out.printf("* %-90s*\n", ("Pedido ID: " + pedId));
        System.out.printf("* %-90s*\n", ("Data de Emissão: " + dataFormatada));
        System.out.println(line);
        System.out.printf("* %-3s| %-5s| %-30s| %-5s| %-13s| %-24s*\n", "#", "ID", "DESCRIÇÃO", "QTD", "VL. UNIT.", "VL. TOTAL");
        System.out.println(line2);
        // Verifica os dados de cada produto no pedido por seu ID
        for(int i = 0; i < atualIds.length; i++){
            int idProd = atualIds[i];
            int qtdProd = atualQtds[i];
            int getProdIndex = -1;
            for(int a = 0; a < idsCatalogo.length; a++){
                if(idsCatalogo[a] == idProd){
                    getProdIndex = a;
                }
            }
            String nomeProd = nomesCatalogo[getProdIndex];
            double vlProd = precos[getProdIndex];
            double vlProdTotal = (vlProd * qtdProd); // AI Help 

            System.out.printf("* %-3s| %-5d| %-30s| %-5d| R$ %-10.2f| R$ %-21.2f*\n", contRow, idProd, nomeProd, qtdProd, vlProd, vlProdTotal);
            contRow ++;
        }
        System.out.println(line2);
        System.out.printf("* %-64s| R$ %-21.2f*\n", "SUBTOTAL", pedVal);
        System.out.printf("* %-64s| R$ %-21.2f*\n", "TOTAL", pedVal);
        System.out.println(line);
        System.out.printf("* %-90s*\n", "OBRIGADO PELA PREFERÊNCIA! VOLTE SEMPRE!");
        System.out.println(line);
    }

    public static void buscaVendaEspecifica(String[] histIds, double[] histVals, String[][] histItems, int[] idsCatalogo, String[] nomesCatalogo, double[] precos) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID da compra: ");
        String idCompra = scanner.nextLine();

        boolean encontrado = false;
        double totalPedido = 0;
        int contRow = 1;

        // Verifica se o histórico está vazio
        if (histIds.length == 0) {
            System.out.println("Nenhuma venda com ID " + idCompra + " encontrado!");
            return;
        }

        // percorre o histórico de vendas
        for (int i = 0; i < histIds.length; i++) {
            if (histIds[i].equals(idCompra)) {
                encontrado = true;
                System.out.println("Venda encontrada!");
                System.out.println("Pedido ID: " + histIds[i]);

                // Nota fiscal case 5
                LocalDateTime agora = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String dataFormatada = agora.format(formatter);
    
                System.out.println("*********************************************************************************************");
                System.out.printf("* %-90s*\n", "MACKSHOP");
                System.out.printf("* %-90s*\n", "CNPJ: 12.345.678/0001-99");
                System.out.println("*********************************************************************************************");
                System.out.printf("* %-90s*\n", "NOTA FISCAL - VENDA AO CONSUMIDOR");
                System.out.printf("* %-90s*\n", "Pedido ID: " + idCompra);
                System.out.printf("* %-90s*\n", "Data de Emissão: " + dataFormatada);
                System.out.println("*********************************************************************************************");
                System.out.printf("* %-3s| %-5s| %-30s| %-5s| %-13s| %-24s*\n", "#", "ID", "DESCRIÇÃO", "QTD", "VL. UNIT.", "VL. TOTAL");
                System.out.println("---------------------------------------------------------------------------------------------");
                
                // verifica os itens da venda 
                for (int j = 0; j < histItems.length; j++) {
                    // Cada linha da matriz é um item
                    if (histItems[j][0].equals(idCompra)){ // ve se é igual a compra
                        int idProd = Integer.parseInt(histItems[j][1]); // ajuda da IA
                        int qtdProd = Integer.parseInt(histItems[j][2]); // ajuda da IA
    
                        // Pega o índice do produto no catálogo
                        int getProdIndex = -1;
                        for (int a = 0; a < idsCatalogo.length; a++) {
                            if (idsCatalogo[a] == idProd) {
                                getProdIndex = a;
                            }
                        }
    
                        String nomeProd = nomesCatalogo[getProdIndex];
                        double vlProd = precos[getProdIndex];
                        double vlProdTotal = vlProd * qtdProd;
                        totalPedido += vlProdTotal; // ajuda da IA
    
                        System.out.printf("* %-3d| %-5d| %-30s| %-5d| R$ %-10.2f| R$ %-21.2f*\n",
                                contRow, idProd, nomeProd, qtdProd, vlProd, vlProdTotal);
                        contRow++;
                    }
                }
    
                System.out.println("---------------------------------------------------------------------------------------------");
                System.out.printf("* %-64s| R$ %-21.2f*\n", "SUBTOTAL", totalPedido);
                System.out.printf("* %-64s| R$ %-21.2f*\n", "TOTAL", totalPedido);
                System.out.println("*********************************************************************************************");
                System.out.printf("* %-90s*\n", "OBRIGADO PELA PREFERÊNCIA! VOLTE SEMPRE!");
                System.out.println("*********************************************************************************************");
    
                break; //encontrou a venda
            }
        }
    
        if (!encontrado) {
            System.out.println("ID" + idCompra + " não existe no histórico de vendas!");
        }
    }

    public static void relatorioEstoqueBaixo(int[] estoqueAtual, String[] nomesProdutos){
        System.out.println("Produtos com menos de 7 unidades");
        
        for(int i = 0; i < estoqueAtual.length; i++){
            if(estoqueAtual[i] <=7 ){
                System.out.println(" " + nomesProdutos[i] + " = " + estoqueAtual[i]);
            }
        }
    }
}
