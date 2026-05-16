package br.com.yurigobatto.services;

import br.com.yurigobatto.entities.Produto;

import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    int opc = 0;
    private ProdutoService produtoService;

    public void menuProdutos(){
        do {
            System.out.println("------Menu------" +
                    "1 - Listar Produtos\n" +
                    "2 - Buscar Produto\n" +
                    "3 - Cadastrar Produto\n" +
                    "4 - Atualizar Produto\n" +
                    "5 - Excluir Produto\n" +
                    "6 - Voltar");
            opc = scanner.nextInt();
            switch (opc) {
                case 1:
                    for (Produto produto : produtoService.listar()) {
                        // Ao invés de imprimir o objeto inteiro, você imprime os pedaços dele:
                        System.out.print("Nome: " + produto.getNome());
                        System.out.print(" - Quantidade: " + produto.getQuantidade());
                        System.out.println(" - Preço: " + produto.getValorUnitario());
                    }
            }
        }while(opc != 6);
    }

    public void menuPedidos(){

    }

    public void menu(){
        do{
            System.out.println("-----Menu-----\n" +
                    "1 - Opções Produtos\n" +
                    "2 - Opções Pedidos\n" +
                    "3 - Sair");
            opc = scanner.nextInt();

            switch(opc){
                case 1:
                    menuProdutos();
            }
        }while (opc != 3);
    }
}
