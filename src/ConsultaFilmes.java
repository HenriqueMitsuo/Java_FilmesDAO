import java.sql.*;
import javax.swing.*;

public class ConsultaFilmes {
  public static void main(String[] args) {
    final String DRIVER = "org.gjt.mm.mysql.Driver";
    final String URL = "jdbc:mysql://localhost:3306/cinema";

    try {
      Class.forName(DRIVER);
      Connection connection = DriverManager.getConnection(URL, "root", "");

      String sql = "SELECT * FROM filmes WHERE codigo > ? AND codigo < ?";

      PreparedStatement statement = connection.prepareStatement(sql);

      statement.setInt(1, 0);
      statement.setInt(2, 10);

      System.out.println(sql);
      System.out.println(statement);

      ResultSet resultSet = statement.executeQuery();

      while(resultSet.next()) {
        int codigo = resultSet.getInt("codigo");
        String titulo = resultSet.getString("titulo");
        String genero = resultSet.getString("genero");

        String filme = "Codigo: " + codigo + "\nTitulo: " + titulo + "\nGenero: " + genero;

        JOptionPane.showMessageDialog(null, filme);
      }

      JOptionPane.showMessageDialog(null, "Conexão realizada com sucesso");
      resultSet.close();
      statement.close();
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
