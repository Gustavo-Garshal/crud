package br.com.yurigobatto.services;

import br.com.yurigobatto.database.Conexao;
import br.com.yurigobatto.entities.ItemPedido;
import br.com.yurigobatto.entities.Pedido;
import br.com.yurigobatto.entities.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PedidoService {

    private final ProdutoService produtoService;
    private final Conexao conexao;

    public PedidoService(ProdutoService produtoService, Conexao conexao) {
        this.produtoService = produtoService;
        this.conexao = conexao;
    }

    public List<Pedido> listar() {
        ResultSet resultSet = conexao.execute("select * from pedidos");
        List<Pedido> pedidos = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Pedido item = new Pedido();
                item.setId(resultSet.getString("id"));
                item.setNumeroPedido(resultSet.getInt("numero_pedido"));
                item.setCliente(resultSet.getString("cliente"));
                pedidos.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pedidos",e);
        }
        return pedidos;
    }

    public Pedido encontrar(String id) {
        PreparedStatement statement = conexao.createPreparedStatement("SELECT * FROM pedidos WHERE id = ?");
        try{
            statement.setString(1, id);
            ResultSet resultSet = conexao.execute(statement);
            if (!resultSet.next()) {
                return null;
            }
            Pedido item = new Pedido();
            item.setId(resultSet.getString("id"));
            item.setNumeroPedido(resultSet.getInt("numero_pedido"));
            item.setCliente(resultSet.getString("cliente"));
            return item;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pedido",e);
        }
    }

    private int ultimoPedido(){
        try{
            Statement statement = conexao.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(numero_pedido) FROM pedidos");
            if (resultSet.next()) {
                return resultSet.getInt("MAX(numero_pedido)");
            }
        }catch(Exception e){
            throw new RuntimeException("Erro ao buscar ultimo numero de pedido",e);
        }
        return 0;
    }

    public Pedido criar(String cliente, Map<String, Integer> produtos) {
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID().toString());
        pedido.setNumeroPedido(ultimoPedido() + 1);
        pedido.setCliente(cliente);
        for(Map.Entry<String, Integer> entry : produtos.entrySet()){
            String produtoId = entry.getKey();
            int quantidade = entry.getValue();

            Produto produto = produtoService.encontrar(produtoId);
            ItemPedido item = new ItemPedido(pedido, produto);
            item.setQuantidade(quantidade);
            pedido.addItem(item);
        }
        PreparedStatement statement = conexao.createPreparedStatement("INSERT INTO pedidos(" +
                "id, numero_pedido, cliente, valor_total) VALUES (?, ?, ?, ?)");
        try{
            statement.setString(1, pedido.getId());
            statement.setInt(2, pedido.getNumeroPedido());
            statement.setString(3, pedido.getCliente());
            statement.setBigDecimal(4, pedido.getValorTotal());
            statement.execute();
        }catch(Exception e){
            throw new RuntimeException("Erro ao tentar adicionar pedido",e);
        }
        PreparedStatement statementItens = conexao.createPreparedStatement("INSERT INTO item_pedido(" +
                "id_pedido, id_produto, qtd, valor) VALUES (?, ?, ?, ?)");
        try{
            for(ItemPedido item : pedido.getItens()){
                statementItens.setString(1, item.getPedidoId());
                statementItens.setString(2, item.getProdutoId());
                statementItens.setInt(3, item.getQuantidade());
                statementItens.setBigDecimal(4, item.getValorUnitario());
                statementItens.execute();
            }
        }catch(Exception e){
            throw new RuntimeException("Erro ao tentar adicionar item do pedido",e);
        }
        return pedido;
    }

    public Pedido adicionarItem(String pedidoId, String produtoId, Integer quantidade) {
        if (encontrar(pedidoId) != null){
            PreparedStatement statement = conexao.createPreparedStatement("SELECT * FROM item_pedido " +
                    "WHERE id_pedido = ? AND id_produto = ?");
            try{
                statement.setString(1, pedidoId);
                statement.setString(2, produtoId);
                ResultSet resultSet = conexao.execute(statement);
                //Verifica se já tem o produto no pedido
                if (!resultSet.next()) {
                    PreparedStatement statementAdd = conexao.createPreparedStatement("INSERT INTO item_pedido(" +
                            "id, id_pedido, id_produto, qtd, valor) VALUES (?, ?, ?, ?, ?)");
                    Produto produto = produtoService.encontrar(produtoId);
                    try {
                        statementAdd.setString(1, UUID.randomUUID().toString());
                        statementAdd.setString(2, pedidoId);
                        statementAdd.setString(3, produtoId);
                        statementAdd.setInt(4, quantidade);
                        statementAdd.setBigDecimal(5, produto.getValorUnitario());
                        statementAdd.execute();
                    }catch (SQLException e){
                        throw new RuntimeException("Erro ao adicionar item do pedido",e);
                    }
                    System.out.println("Produto adicionado com sucesso");
                }
                else {
                    PreparedStatement statementAtt = conexao.createPreparedStatement("UPDATE item_pedido set " +
                            "qtd = qtd + ? WHERE id_pedido = ? AND id_produto = ?");
                    statementAtt.setInt(1, quantidade);
                    statementAtt.setString(2, pedidoId);
                    statementAtt.setString(3, produtoId);
                    statementAtt.execute();
                    System.out.println("Quantidade adicionada com sucesso");
                }
            }catch (Exception e){
                throw new RuntimeException("Erro ao adicionar pedido",e);
            }
            attValorPedido(pedidoId);
        }
        else{
            throw new RuntimeException("Pedido não encontrado!");
        }
        return null;
    }
/*
    public Pedido removerItem(String id, String produtoId) {
        for (int i = 0; i < database.size(); i++) {
            Pedido pedido = database.get(i);
            if (!pedido.getId().equals(id))
                continue;
            pedido.getItens()
                    .removeIf(item -> item.getProdutoId().equals(produtoId));
            database.set(i, pedido);
            return pedido;
        }
        return null;
    }
*/
    public void attValorPedido(String pedidoId){
        PreparedStatement statement = conexao.createPreparedStatement("UPDATE pedidos SET valor_total = " +
                "(SELECT COALESCE(SUM(qtd * valor), 0) FROM item_pedido WHERE id_pedido = ?) WHERE id = ?");
        try {
            statement.setString(1, pedidoId);
            statement.setString(2, pedidoId);
            statement.execute();
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar pedido",e);
        }
    }

    public boolean excluir(String id) {
        PreparedStatement statement = conexao.createPreparedStatement("DELETE FROM pedidos WHERE id = ?");
        try {
            statement.setString(1, id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao tentar excluir",e);
        }return true;
    }
}
