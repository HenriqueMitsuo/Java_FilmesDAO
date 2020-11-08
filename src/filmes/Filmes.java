package filmes;

public class Filmes {
  private int codigo;
  private String Titulo;
  private String Genero;
  private String Produtora;
  private String Data;

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public String getTitulo() {
    return Titulo;
  }
  public void setTitulo(String titulo) {
    this.Titulo = titulo;
  }
  public String getGenero() {
    return Genero;
  }
  public void setGenero(String genero) {
    this.Genero = genero;
  }  
  public String getProdutora() {
	  return Produtora;
  }
  public void setProdutora(String produtora) {
    this.Produtora = produtora;
  }
  public String getData() {
	  return Data;
  }
  public void setData(String data) {
    this.Data = data;
  }
}
