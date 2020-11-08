import java.sql.*;
import javax.swing.*;
import utils.BD;

public class ConsultaFilmesComBD {
  public static void main(String[] args) {
    BD filmes = new BD();

    if (filmes.Connect()) {
      try {
        String sql = "SELECT * FROM filmes WHERE codigo > ? AND codigo < ?";

        PreparedStatement statement = filmes.connectionObj.prepareStatement(sql);

        statement.setInt(1, 0);
        statement.setInt(2, 10);

        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
          int codigo = resultSet.getInt("codigo");
          String titulo = resultSet.getString("titulo");
          String genero = resultSet.getString("genero");

          String filme = "Codigo: " + codigo + "\nTitulo: " + titulo + "\nGenero: " + genero;

          JOptionPane.showMessageDialog(null, filme);
        }

        resultSet.close();
        statement.close();
        filmes.Disconnect();

      } catch (SQLException e) {
          JOptionPane.showMessageDialog(null, "SQLException: " + e);
      }
    }
  }
}
