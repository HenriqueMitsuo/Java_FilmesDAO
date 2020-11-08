import java.sql.*;
import javax.swing.*;

public class Conexao {
  public static void main(String[] args) {
    final String DRIVER = "org.gjt.mm.mysql.Driver";
    final String URL = "jdbc:mysql://localhost:3306/mysql";

    try {
      Class.forName(DRIVER);
      Connection connection = DriverManager.getConnection(URL, "root", "");

      JOptionPane.showMessageDialog(null, "Conexão realizada com sucesso");

      connection.close();
    } 
    catch (ClassNotFoundException e) {
      JOptionPane.showMessageDialog(null, "Driver não encontrado: " + e);
    } 
    catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "SQLException: " + e);
    }
  }
}
