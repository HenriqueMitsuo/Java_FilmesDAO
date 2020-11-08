package principal;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import filmes.FilmesDAO;
import utils.BD;

public class GUIFilmes extends JFrame {
  /**
	 *
	 */
  private static final long serialVersionUID = 1L;
  
  private JButton btnGravar;
  private JButton btnAlterar;
  private JButton btnExcluir;
  private JButton btnNovo;
  private JButton btnLocalizar;
  private JButton btnCancelar;
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

  private FilmesDAO filmes;
  
  public static void main(String[] args) {
    JFrame frame = new GUIFilmes();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  public GUIFilmes() {
    inicializarComponentes();
    definirEventos();
  }

  private void inicializarComponentes() {
    setLayout(new FlowLayout(FlowLayout.LEFT));
    setTitle("Sistema CRUD - Filmes");

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

    btnGravar = new JButton("Gravar");
    btnNovo = new JButton("Novo");
    btnAlterar = new JButton("Alterar");
    btnExcluir = new JButton("Excluir");
    btnLocalizar = new JButton("Localizar");
    btnCancelar = new JButton("Cancelar");
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

    add(btnGravar);
    add(btnNovo);
    add(btnAlterar);
    add(btnExcluir);
    add(btnLocalizar);
    add(btnCancelar);
    add(btnSair);

    setBounds(200, 400, 620, 120);
    setResizable(false);
    
    filmes = new FilmesDAO();
    if (!filmes.bd.Connect()) {
      JOptionPane.showMessageDialog(null, "Falha ao conectar, o sistema será fechado");
      System.exit(0);
    }
  }

  private void definirEventos() {
    btnGravar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (txtTitulo.getText().equals("")) {
          JOptionPane.showMessageDialog(null, "O titulo não pode estar vazio");
          txtTitulo.requestFocus();
          return;
        }
        if (txtGenero.getText().equals("")) {
          JOptionPane.showMessageDialog(null, "O genero não pode estar vazio");
          txtGenero.requestFocus();
          return;
        }
        if (txtProdutora.getText().equals("")) {
          JOptionPane.showMessageDialog(null, "A produtora não pode estar vazio");
          txtProdutora.requestFocus();
          return;
        }
        if (txtData.getText().equals("")) {
          JOptionPane.showMessageDialog(null, "A data não pode estar vazio");
          txtData.requestFocus();
          return;
        }
        filmes.filme.setTitulo(txtTitulo.getText());
        filmes.filme.setGenero(txtGenero.getText());
        filmes.filme.setProdutora(txtProdutora.getText());
        filmes.filme.setData(txtData.getText());
        JOptionPane.showMessageDialog(null, filmes.atualizar(FilmesDAO.INCLUSAO));
        limparCampos();
      }
    });
    btnAlterar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        filmes.filme.setCodigo(Integer.parseInt(txtCodigo.getText()));
        filmes.filme.setTitulo(txtTitulo.getText());
        filmes.filme.setGenero(txtGenero.getText());
        filmes.filme.setProdutora(txtProdutora.getText());
        filmes.filme.setData(txtData.getText());
        JOptionPane.showMessageDialog(null, filmes.atualizar(FilmesDAO.ALTERACAO));
        limparCampos();
      }
    });
    btnExcluir.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        filmes.filme.setCodigo(Integer.parseInt(txtCodigo.getText()));
        filmes.localizar();
        int n = JOptionPane.showConfirmDialog(null, filmes.filme.getTitulo(), "Excluir filmes?", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_NO_OPTION) {
          JOptionPane.showMessageDialog(null, filmes.atualizar(FilmesDAO.EXCLUSAO));
          limparCampos();
        }
      }
    });
    btnNovo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        limparCampos();
      }
    });
    btnLocalizar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        atualizarCampos();
      }
    });
    btnCancelar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        limparCampos();
      }
    });
    btnSair.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        filmes.bd.Disconnect();
        System.exit(0);
      }
    });
  }

  public void atualizarCampos() {
    filmes.filme.setCodigo(Integer.parseInt(txtCodigo.getText()));

    if (filmes.localizar()) {
      txtCodigo.setText(Integer.toString(filmes.filme.getCodigo()));
      txtTitulo.setText(filmes.filme.getTitulo());
      txtGenero.setText(filmes.filme.getGenero());
      txtProdutora.setText(filmes.filme.getProdutora());
      txtData.setText(filmes.filme.getData());
    } else {
      JOptionPane.showMessageDialog(null, "Filme não encontrado");
      limparCampos();
    }
  }

  public void limparCampos() {
    txtCodigo.setText("");
    txtTitulo.setText("");
    txtGenero.setText("");
    txtProdutora.setText("");
    txtData.setText("");

    txtCodigo.requestFocus();
  }
}