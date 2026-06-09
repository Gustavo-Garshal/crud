package br.com.yurigobatto.orm;

import br.com.yurigobatto.database.Conexao;
import br.com.yurigobatto.entities.Produto;
import br.com.yurigobatto.orm.annotation.Id;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlOrm {
    private final Conexao conexao;

    public SqlOrm(Conexao conexao) {
        this.conexao = conexao;
    }
    //CRUD
    public Entidade criar(Entidade entidade) {

    }

    public List<Entidade> listar() {

    }

    public Entidade encontrar(Class<?> clazz, String id) {
        Entidade entidade = criarInstancia(clazz);
        PreparedStatement statement = conexao.createPreparedStatement("SELECT * FROM %s WHERE id = ?".formatted(entidade.getTabela()));
        try {
            statement.setString(1, id);
            ResultSet resultSet = conexao.execute(statement);
            if (!resultSet.next()) {
                throw  new RuntimeException("Nenhum produto encontrado");
            }
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String nome = field.getName();
                Class<?> type = field.getType();
                Object valor = null;
                if (type.isAssignableFrom(String.class)) {
                    valor = resultSet.getString(nome);
                }else if (type.isAssignableFrom(Integer.class)) {
                    valor = resultSet.getInt(nome);
                }else if (type.isAssignableFrom(Double.class)) {
                    valor = resultSet.getDouble(nome);
                }
                field.setAccessible(true);
                field.set(entidade, valor);
            }
            return entidade;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Entidade atualizar(Entidade entidade) {

    }

    public void excluir(Entidade entidade) {
        String tabela = entidade.getTabela();
        try {
            String id = getId(entidade);
            conexao.createStatement().execute("DELETE FROM %s WHERE id = '%s'".formatted())
        }
    }

    private String getId(Entidade entidade) throws IllegalAccessException {
        Class<? extends Entidade> clazz = entidade.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Id.class)){
                continue;
            }
            field.setAccessible(true);
            Object o = field.get(entidade);
            return o == null ? null : o.toString();
        }
    }

    private Entidade criarInstancia(Class<?> clazz) {
        try {
            return (Entidade) clazz.newInstance();
        }catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
