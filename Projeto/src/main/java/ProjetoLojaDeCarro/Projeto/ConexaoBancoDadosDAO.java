package ProjetoLojaDeCarro.Projeto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDadosDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/loja_carros";
    private static final String USER = "leona";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
