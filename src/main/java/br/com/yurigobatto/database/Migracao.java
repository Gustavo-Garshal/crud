package br.com.yurigobatto.database;

public class Migracao {

    private final Conexao conexao;

    Migracao(Conexao conexao) {
        this.conexao = conexao;
        initDatabase();
    }

    private void initDatabase() {
        try {
            conexao.createStatement().execute("CREATE TABLE IF NOT EXISTS produtos (" +
                    "id TEXT PRIMARY KEY, " +
                    "nome TEXT(60) NOT NULL, " +
                    "qtd INTEGER NOT NULL, " +
                    "valor REAL NOT NULL)");
            conexao.createStatement().execute("CREATE TABLE IF NOT EXISTS pedidos (" +
                    "id TEXT PRIMARY KEY, " +
                    "numero_pedido INT DEFAULT 0, " +
                    "cliente TEXT(60), " +
                    "valor_total REAL(10,02))");
            conexao.createStatement().execute("CREATE TABLE IF NOT EXISTS item_pedido (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_pedido INT, " +
                    "id_produto INT, " +
                    "FOREIGN KEY " +
                    "(id_pedido) " +
                    "REFERENCES pedidos (id), " +
                    "FOREIGN KEY " +
                    "(id_produto) " +
                    "REFERENCES produtos (id))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
