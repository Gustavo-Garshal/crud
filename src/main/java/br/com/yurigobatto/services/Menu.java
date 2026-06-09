package br.com.yurigobatto.services;

import br.com.yurigobatto.dto.ItemPedidoDto;
import br.com.yurigobatto.dto.PedidoDto;
import br.com.yurigobatto.dto.ProdutoDto;
import br.com.yurigobatto.entities.ItemPedido;
import br.com.yurigobatto.entities.Pedido;
import br.com.yurigobatto.entities.Produto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
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
                    Produto produto = produtoService.encontrar(idProduto);
                        System.out.println("Id do Produto: " + idProduto);
                        System.out.println("Nome: " + produto.getNome());
                        System.out.println("Quantidade: " + produto.getQuantidade());
                        System.out.println("Preco: " + produto.getValorUnitario());
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.print("Digite o nome do produto: ");
                    nome = scanner.nextLine();
                    System.out.print("Digite o quantidade do produto: ");
                    quantidade = scanner.nextInt();
                    System.out.print("Digite o valor do produto: ");
                    valorUnitario = scanner.nextBigDecimal();
                    produtoService.criar(new ProdutoDto(nome, quantidade, valorUnitario));
                    System.out.println("Produto criado com sucesso!");
                    break;
                case 4:
                    scanner.nextLine();
                    System.out.print("Digite o Id do Produto: ");
                    idProduto = scanner.next();
                    System.out.print("Digite o nome do produto: ");
                    nome = scanner.next();
                    System.out.print("Digite o quantidade do produto: ");
                    quantidade = scanner.nextInt();
                    System.out.print("Digite o valor do produto: ");
                    valorUnitario = scanner.nextBigDecimal();
                    produtoService.atualizar(idProduto, new ProdutoDto(nome, quantidade, valorUnitario));
                    System.out.println("Produto atualizado com sucesso!");
                    break;
                case 5:
                    System.out.print("Digite o Id do Produto: ");
                    idProduto =  scanner.next();
                    produtoService.excluir(idProduto);
                    System.out.println("Produto excluido com sucesso!");
                    break;
                case 6:
                    break;
            }
        }while(opc != 6);
    }

    public void menuPedidos(){
        do {
            System.out.println("" +
                    "------Menu------\n" +
                    "1 - Lista Pedidos\n" +
                    "2 - Buscar Pedido\n" +
                    "3 - Gerar Pedido\n" +
                    "4 - Adicionar Item ao Pedido\n" +
                    "5 - Remover Item do Pedido\n" +
                    "6 - Excluir Pedido\n" +
                    "7 - Voltar");
            opc = scanner.nextInt();
            String pedidoId;
            String produtoId;
            int quantidade;
            String cliente;

            switch (opc) {
                case 1:
                    for (Pedido pedido : pedidoService.listar()) {
                        System.out.println("Id: " + pedido.getId());
                        System.out.println("Numero do pedido: " + pedido.getNumeroPedido());
                        System.out.println("Cliente: " + pedido.getCliente());
                        System.out.println("Valor do pedido: " + pedido.getValorTotal() + "\n");
                    }
                    break;
                case 2:
                    System.out.print("Digite o Id do Pedido: ");
                    pedidoId = scanner.next();
                    Pedido pedido = pedidoService.encontrar(pedidoId);
                        System.out.println("Id do Pedido: " + pedidoId);
                        System.out.println("Numero do pedido: " + pedido.getNumeroPedido());
                        System.out.println("Cliente: " + pedido.getCliente());
                        System.out.println("Valor do pedido: " + pedido.getValorTotal());
                       /* System.out.println("Itens:");
                    for (ItemPedido item : pedido.getItens()) {
                        System.out.println("Produto ID: " + item.getProdutoId() + "\n" +
                                "Quantidade: " + item.getQuantidade() + "\n" +
                                "Subtotal: " + item.getValorTotal());
                    }*/
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.print("Cliente: ");
                    cliente = scanner.nextLine();
                    String continua = "s";
                    while (continua.equals("s")) {
                        System.out.print("Digite o ID do Produto: ");
                        produtoId = scanner.next();
                        System.out.print("Digite o quantidade do Produto: ");
                        quantidade = scanner.nextInt();
                        System.out.println("Deseja adicionar mais algum produto? (s/n)");
                        continua = scanner.next();
                        Produto produto = produtoService.encontrar(produtoId);
                        pedidoService.criar(new PedidoDto(cliente)
                                .addItem(new ItemPedidoDto(produto.getId(), quantidade)));
                    }
                    System.out.println("Produto adicionado com sucesso!");
                    break;
                case 4:
                    scanner.nextLine();
                    System.out.print("Digite o ID do Pedido: ");
                    pedidoId = scanner.next();
                    System.out.print("Digite o ID do Produto: ");
                    produtoId = scanner.next();
                    System.out.print("Digite o quantidade do Pedido: ");
                    quantidade = scanner.nextInt();
                    pedidoService.adicionarItem(pedidoId, produtoId, quantidade);
                    break;
                case 5:
                    scanner.nextLine();
                    System.out.println("Digite o ID do Pedido: ");
                    pedidoId = scanner.next();
                    System.out.println("Digite o ID do Produto: ");
                    produtoId = scanner.next();
                    pedidoService.removerItem(pedidoId, produtoId);
                    break;
                case 6:
                    scanner.nextLine();
                    System.out.print("Digite o ID do Pedido: ");
                    pedidoId = scanner.next();
                    pedidoService.excluir(pedidoId);
                    break;
                case 7:
                    break;
            }
        }while(opc != 7);
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
