import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import utils.BD;

public class ConsultaSql extends JFrame {
  /**
	 *
	 */
  private static final long serialVersionUID = 1L;
  
  private JLabel lblSql;
  private JTextField txtSql;
  private JButton btnExecute;
  private JScrollPane scrlTable;
  private JTable table;
  private BD bd;
  private PreparedStatement statement;
  private ResultSet resultSet;

  public static void main(String[] args) {
    JFrame frame = new ConsultaSql();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  public ConsultaSql() {
    inicializarComponentes();
    definirEventos();
  }

  private void inicializarComponentes() {
    setLayout(null);
    setTitle("Consulta SQL");
    setBounds(200, 200, 600, 500);
    setResizable(false);

    lblSql = new JLabel("Digite o comando SQL");
    lblSql.setBounds(50, 10, 200, 25);

    txtSql = new JTextField(50);
    txtSql.setBounds(50, 35, 500, 25);

    scrlTable = new JScrollPane();
    scrlTable.setBounds(50, 100, 500, 300);

    btnExecute = new JButton("Executar");
    btnExecute.setBounds(50, 70, 100, 25);

    add(scrlTable);
    add(lblSql);
    add(txtSql);
    add(btnExecute);

    bd = new BD();
  }

  private void definirEventos() {
    btnExecute.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (txtSql.getText().equals("")) {
          return;
        }
        try {
          if (!bd.Connect()) {
            JOptionPane.showMessageDialog(null, "Falha na conexão");
            System.exit(0);
          }
          
          statement = bd.connectionObj.prepareStatement(txtSql.getText());
          resultSet = statement.executeQuery();

          DefaultTableModel tableModel = new DefaultTableModel(new String []{}, 0);

          int qtdColunas = resultSet.getMetaData().getColumnCount();
          for (int index = 1; index <= qtdColunas; index++) {
            tableModel.addColumn(resultSet.getMetaData().getColumnName(index));
          }

          table = new JTable(tableModel);
          DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();

          while (resultSet.next()) {
            try {
              String[] dados = new String[qtdColunas];
              for (int i = 1; i <= qtdColunas; i++) {
                dados[i - 1] = resultSet.getString(i);
                System.out.println(resultSet.getString(i));
              }
              defaultTableModel.addRow(dados);
              System.out.println();
            } catch (SQLException sqlException) {
              System.out.println(sqlException);
            }
            scrlTable.setViewportView(table);
          }
          resultSet.close();
          statement.close();
          bd.Disconnect();
        } catch (Exception exception) {
          System.out.println(exception);
        }
      }
    });
  }
}
