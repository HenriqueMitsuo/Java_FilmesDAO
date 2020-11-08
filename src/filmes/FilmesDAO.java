package filmes;

import java.sql.*;
import utils.BD;

public class FilmesDAO {
  public Filmes filme;
  public BD bd;

  private PreparedStatement statement;
  private ResultSet resultSet;
  private String sql;
  private String menssagem;

  public static final byte INCLUSAO = 1;
  public static final byte ALTERACAO = 2;
  public static final byte EXCLUSAO = 3;

  public FilmesDAO() {
    bd = new BD();
    filme = new Filmes();
  }

  public boolean localizar() {
    sql = "SELECT * FROM filmes WHERE codigo = ?";
    try {
      statement = bd.connectionObj.prepareStatement(sql);
      statement.setInt(1, filme.getCodigo());

      resultSet = statement.executeQuery();
      resultSet.next();

      filme.setCodigo(resultSet.getInt(1));
      filme.setTitulo(resultSet.getString(2));
      filme.setGenero(resultSet.getString(3));
      filme.setProdutora(resultSet.getString(4));
      filme.setData(""+resultSet.getDate(5));

      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  public String atualizar(int operacao) {
    menssagem = "Operação realizada com sucesso";
    try {
      if (operacao == INCLUSAO) {
        sql = "INSERT INTO filmes(titulo, genero, produtora, data) VALUES (?, ?, ?, ?)";
        statement = bd.connectionObj.prepareStatement(sql);
        statement.setString(1, filme.getTitulo());
        statement.setString(2, filme.getGenero());
        statement.setString(3, filme.getProdutora());
        statement.setString(4, filme.getData());
      } else if (operacao == ALTERACAO) {
        sql = "UPDATE filmes SET titulo=?, genero=?, produtora=?, data=? WHERE codigo=?";
        statement = bd.connectionObj.prepareStatement(sql);
        statement.setString(1, filme.getTitulo());
        statement.setString(2, filme.getGenero());
        statement.setString(3, filme.getProdutora());
        statement.setString(4, filme.getData());
        statement.setInt(5, filme.getCodigo());
      } else if (operacao == EXCLUSAO) {
        sql = "DELETE FROM filmes WHERE codigo=?";
        statement = bd.connectionObj.prepareStatement(sql);
        statement.setInt(1, filme.getCodigo());
      }
      if (statement.executeUpdate() == 0) {
        menssagem = "Falha na operação";
      }
    } catch (SQLException sqlException) {
      menssagem = "Falha no banco de dados: " + sqlException.toString();
    }

    return menssagem;
  }
}
