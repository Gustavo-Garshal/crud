package br.com.yurigobatto.services;

import br.com.yurigobatto.database.Conexao;
import br.com.yurigobatto.entities.Produto;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProdutoService {

    private final Conexao conexao;

    public ProdutoService(Conexao conexao) {
        this.conexao = conexao;
    }

    public List<Produto> listar() {
        ResultSet resultSet = conexao.execute("SELECT * FROM produtos");
        List<Produto> produtos = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Produto item = new Produto();
                item.setId(resultSet.getString("id"));
                item.setNome(resultSet.getString("nome"));
                item.setQuantidade(resultSet.getInt("qtd"));
                item.setValorUnitario(resultSet.getBigDecimal("valor"));
                produtos.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produtos;
    }

    public Produto encontrar(String id) {
        PreparedStatement statement = conexao.createPreparedStatement("SELECT * FROM produtos WHERE id = ?");
        try {
            statement.setString(1, id);
            ResultSet resultSet = conexao.execute(statement);
            if (!resultSet.next()) {
                return null;
            }
            Produto item = new Produto();
            item.setId(resultSet.getString("id"));
            item.setNome(resultSet.getString("nome"));
            item.setQuantidade(resultSet.getInt("qtd"));
            item.setValorUnitario(resultSet.getBigDecimal("valor"));
            return item;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Produto criar(String nome, Integer quantidade, BigDecimal valorUnitario) {
        Produto produto = new Produto();
        produto.setId(UUID.randomUUID().toString());
        produto.setNome(nome);
        produto.setQuantidade(quantidade);
        produto.setValorUnitario(valorUnitario);

        PreparedStatement statement = conexao.createPreparedStatement("INSERT INTO produtos VALUES (?, ?, ?, ?)");
        try {
            statement.setString(1, produto.getId());
            statement.setString(2, produto.getNome());
            statement.setInt(3, produto.getQuantidade());
            statement.setBigDecimal(4, produto.getValorUnitario());
            statement.execute();
            return produto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Produto atualizar(String id, String nome, Integer quantidade, BigDecimal valorUnitario) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setQuantidade(quantidade);
        produto.setValorUnitario(valorUnitario);

        PreparedStatement statement = conexao.createPreparedStatement("UPDATE produtos SET nome = ?, qtd = ?, valor = ? WHERE id = ?)");
        try {
            statement.setString(1, produto.getNome());
            statement.setInt(2, produto.getQuantidade());
            statement.setBigDecimal(3, produto.getValorUnitario());
            statement.setString(4, produto.getId());
            statement.execute();
            return produto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean excluir(String id) {
        try {
            conexao.createStatement().execute("DELETE FROM produtos WHERE id = '" + id + "'");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
