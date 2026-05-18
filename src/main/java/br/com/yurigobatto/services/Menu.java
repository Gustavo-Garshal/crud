package br.com.yurigobatto.services;

import br.com.yurigobatto.entities.Produto;

import java.math.BigDecimal;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    int opc = 0;
    private ProdutoService produtoService;
    private PedidoService pedidoService;

    public Menu(ProdutoService produtoService, PedidoService pedidoService, Scanner scanner) {
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;
        this.scanner = scanner;
    }

    public void menuProdutos(){
        do {
            System.out.println("" +
                    "---------Menu--------\n" +
                    "1 - Listar Produtos\n" +
                    "2 - Buscar Produto\n" +
                    "3 - Cadastrar Produto\n" +
                    "4 - Atualizar Produto\n" +
                    "5 - Excluir Produto\n" +
                    "6 - Voltar");
            opc = scanner.nextInt();
            String idProduto;
            String nome;
            int quantidade;
            BigDecimal valorUnitario;

            switch (opc) {
                case 1:
                    for (Produto produto : produtoService.listar()) {
                        System.out.println("Id: " + produto.getId());
                        System.out.println("Nome: " + produto.getNome());
                        System.out.println("Quantidade: " + produto.getQuantidade());
                        System.out.println("Preco: " + produto.getValorUnitario() + "\n");
                    }
                    break;
                case 2:
                    System.out.print("Digite o Id do Produto: ");
                    idProduto =  scanner.next();
                    produtoService.encontrar(idProduto);
                    break;
                case 3:
                    System.out.print("Digite o nome do produto: ");
                    nome = scanner.next();
                    System.out.print("Digite o quantidade do produto: ");
                    quantidade = scanner.nextInt();
                    System.out.println("Digite o valor do produto: ");
                    valorUnitario = scanner.nextBigDecimal();
                    produtoService.criar(nome, quantidade, valorUnitario);
                    System.out.println("Produto criado com sucesso!");
                    break;
                case 4:
                    System.out.print("Digite o Id do Produto: ");
                    idProduto = scanner.next();
                    System.out.println("Digite o nome do produto: ");
                    nome = scanner.next();
                    System.out.println("Digite o quantidade do produto: ");
                    quantidade = scanner.nextInt();
                    System.out.println("Digite o valor do produto: ");
                    valorUnitario = scanner.nextBigDecimal();
                    produtoService.atualizar(idProduto, nome, quantidade, valorUnitario);
                    System.out.println("Produto atualizado com sucesso!");
                    break;
                case 5:
                    System.out.print("Digite o Id do Produto: ");
                    idProduto =  scanner.next();
                    produtoService.excluir(idProduto);
                    break;
                case 6:
                    menu();
                    break;
            }
        }while(opc != 6);
    }

    public void menuPedidos(){
        do {

        }while(opc != 6);
    }

    public void menu(){
        do{
            System.out.println("" +
                    "--------Menu--------\n" +
                    "1 - Opcoes Produtos\n" +
                    "2 - Opcoes Pedidos\n" +
                    "3 - Sair");
            opc = scanner.nextInt();

            switch(opc){
                case 1:
                    menuProdutos();
                    break;
                case 2:
                    menuPedidos();
                    break;
                case 3:
                    System.out.print("Encerrando o sistema...");
                    System.exit(0);
            }
        }while (opc != 3);
    }
}
