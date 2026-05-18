package br.com.yurigobatto;

import br.com.yurigobatto.database.Conexao;
import br.com.yurigobatto.entities.ItemPedido;
import br.com.yurigobatto.entities.Pedido;
import br.com.yurigobatto.entities.Produto;
import br.com.yurigobatto.services.Menu;
import br.com.yurigobatto.services.PedidoService;
import br.com.yurigobatto.services.ProdutoService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Conexao conexao = Conexao.getInstancia();
            Scanner scanner = new Scanner(System.in);
            ProdutoService produtoService = new ProdutoService(conexao);
            PedidoService pedidoService = new PedidoService(produtoService, conexao);
            Menu menu = new Menu(produtoService, pedidoService, scanner);

            menu.menu();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}