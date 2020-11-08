import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import utils.BD;

public class NavegarFilmes extends JFrame {
  /**
	 *
	 */
	private static final long serialVersionUID = 1L;
  private JButton btnProximo;
  private JButton btnAnterior;
  private JButton btnPrimeiro;
  private JButton btnUltimo;
  private JButton btnMais10;
  private JButton btnMenos10;
  private JButton btnSair;

  private JLabel lblCodigo;
  private JLabel lblTitulo;
  private JLabel lblGenero;
  private JLabel lblProdutora;
  private JLabel lblData;

  private JTextField txtCodigo;
  private JTextField txtTitulo;
  private JTextField txtGenero;
  private JTextField txtProdutora;
  private JTextField txtData;

  private BD bd;
  private PreparedStatement statement;
  private ResultSet resultSet;

  public static void main(String[] args) {
    JFrame frame = new NavegarFilmes();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  public NavegarFilmes() {
    inicializarComponentes();
    definirEventos();
  }

  private void inicializarComponentes() {
    setLayout(new FlowLayout(FlowLayout.LEFT));

    lblCodigo = new JLabel("Codigo");
    lblTitulo = new JLabel("Titulo");
    lblGenero = new JLabel("Gênero");
    lblProdutora = new JLabel("Produtora");
    lblData = new JLabel("Data da compra");

    txtCodigo = new JTextField(10);
    txtTitulo = new JTextField(35);
    txtGenero = new JTextField(10);
    txtProdutora = new JTextField(15);
    txtData = new JTextField(8);

    btnProximo = new JButton("Proximo");
    btnAnterior = new JButton("Anterior");
    btnPrimeiro = new JButton("Primeiro");
    btnUltimo = new JButton("Ultimo");
    btnMais10 = new JButton("Mais 10");
    btnMenos10 = new JButton("Menos 10");
    btnSair = new JButton("Sair");

    add(lblCodigo);
    add(txtCodigo);
    add(lblTitulo);
    add(txtTitulo);
    add(lblGenero);
    add(txtGenero);
    add(lblProdutora);
    add(txtProdutora);
    add(lblData);
    add(txtData);

    add(btnPrimeiro);
    add(btnAnterior);
    add(btnProximo);
    add(btnUltimo);
    add(btnMais10);
    add(btnMenos10);
    add(btnSair);

    setBounds(200, 400, 620, 120);
    setResizable(false);
    bd = new BD();
    if (!bd.Connect()) {
      JOptionPane.showMessageDialog(null, "Falha ao conectar com a base de dados");
      System.exit(0);
    }
    carregarTabela();
    atualizarCampos();
  }

  private void definirEventos() {
    btnProximo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          resultSet.next();
          atualizarCampos();
        } catch(SQLException sqlException) {
          JOptionPane.showMessageDialog(null, "Erro:" + sqlException.toString());
        }
      }
    });
    btnAnterior.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          resultSet.previous();
          atualizarCampos();
        } catch(SQLException sqlException) {
          JOptionPane.showMessageDialog(null, "Erro:" + sqlException.toString());
        }
      }
    });
    btnPrimeiro.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          resultSet.first();
          atualizarCampos();
        } catch(SQLException sqlException) {
          JOptionPane.showMessageDialog(null, "Erro:" + sqlException.toString());
        }
      }
    });
    btnUltimo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          resultSet.last();
          atualizarCampos();
        } catch(SQLException sqlException) {
          JOptionPane.showMessageDialog(null, "Erro:" + sqlException.toString());
        }
      }
    });
    btnMais10.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          resultSet.relative(10);
          atualizarCampos();
        } catch(SQLException sqlException) {
          JOptionPane.showMessageDialog(null, "Erro:" + sqlException.toString());
        }
      }
    });
    btnMenos10.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          if (resultSet.getRow() > 10) {
            resultSet.relative(-10);
          } else {
            resultSet.first();
          }
          atualizarCampos();
        } catch(SQLException sqlException) {
          JOptionPane.showMessageDialog(null, "Erro:" + sqlException.toString());
        }
      }
    });
    btnSair.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          resultSet.close();
          statement.close();
        } catch(SQLException sqlException) {
          JOptionPane.showMessageDialog(null, "Erro:" + sqlException.toString());
        }

        bd.Disconnect();
        System.exit(0);
      }
    });
  }

  public void carregarTabela() {
    String query = "SELECT * FROM filmes";

    try{
      statement = bd.connectionObj.prepareCall(query);
      resultSet = statement.executeQuery();
    } catch(SQLException sqlException) {
      JOptionPane.showMessageDialog(null, "Erro:" + sqlException.toString());
    }
  }

  public void atualizarCampos() {
    try {
      if (resultSet.isAfterLast()) {
        resultSet.last();
      }
      if (resultSet.isBeforeFirst()) {
        resultSet.first();
      }
      txtCodigo.setText("" + resultSet.getInt("codigo"));
      txtTitulo.setText(resultSet.getString("titulo"));
      txtGenero.setText(resultSet.getString("genero"));
      txtProdutora.setText(resultSet.getString("produtora"));
      txtData.setText("" + resultSet.getDate("data_compra"));
    } catch(SQLException sqlException) {
      JOptionPane.showMessageDialog(null, "Erro:" + sqlException.toString());
    }
  }
}