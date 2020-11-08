package utils;

import java.sql.*;

public class BD {
  final private String DATABASE = "cinema1";
  final private String USER = "root";
  final private String PASSWORD = "";
  final private String DRIVER = "org.gjt.mm.mysql.Driver";
  final private String URL = "jdbc:mysql://localhost:3306/" + DATABASE;

  public Connection connectionObj;

  public boolean Connect() {
    try {
      Class.forName(DRIVER);
      connectionObj = DriverManager.getConnection(URL, USER, PASSWORD);
      
      System.out.println("\nDATABASE: " + DATABASE + " Conectado!");
      return true;
    } catch (ClassNotFoundException classNotFoundException) {
        System.out.println("Driver não encontrado: " + classNotFoundException);
        return false;
    } catch (SQLException sqlException) {
        System.out.println("Erro ao conectar: " + sqlException);
        return false;
    }
  }

  public void Disconnect() {
    try {
      connectionObj.close();
      System.out.println("\nDATABASE: " + DATABASE + " Disconectado!");
    } catch (SQLException sqlException) {
        System.out.println("Erro ao conectar: " + sqlException);
    }
  }
}
