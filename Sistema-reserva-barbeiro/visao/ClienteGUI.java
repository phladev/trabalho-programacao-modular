package visao;


import modelo.Cliente;
import persistencia.BancoDeDados;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClienteGUI extends JFrame {
    private BancoDeDados banco;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoNome, campoCpf, campoTelefone, campoId;


    public ClienteGUI(BancoDeDados banco) {
        super("Gerenciar Clientes");
        this.banco = banco;
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new GridLayout(4, 2, 5, 5));
        painelTopo.add(new JLabel("ID (para buscar/editar/remover):"));
        campoId = new JTextField();
        painelTopo.add(campoId);
        painelTopo.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painelTopo.add(campoNome);
        painelTopo.add(new JLabel("CPF:"));
        campoCpf = new JTextField();
        painelTopo.add(campoCpf);
        painelTopo.add(new JLabel("Telefone:"));
        campoTelefone = new JTextField();
        painelTopo.add(campoTelefone);
        add(painelTopo, BorderLayout.NORTH);
        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "CPF", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
                preencherCamposDaLinha();
            }
        });
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnRemover = new JButton("Remover");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnListar = new JButton("Listar Todos");
        JButton btnLimpar = new JButton("Limpar Campos");
        
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnListar);
        painelBotoes.add(btnLimpar);
        add(painelBotoes, BorderLayout.SOUTH);
        atualizarTabela();
        
        campoId.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { atualizarBotoes(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { atualizarBotoes(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { atualizarBotoes(); }
            private void atualizarBotoes() {
                boolean temId = !campoId.getText().trim().isEmpty();
                btnAtualizar.setEnabled(temId);
                btnRemover.setEnabled(temId);
                btnBuscar.setEnabled(temId);
            }
        });
        
        btnAtualizar.setEnabled(false);
        btnRemover.setEnabled(false);
        btnBuscar.setEnabled(false);
        
        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnRemover.addActionListener(e -> remover());
        btnBuscar.addActionListener(e -> buscarPorId());
        btnListar.addActionListener(e -> atualizarTabela());
        btnLimpar.addActionListener(e -> limparCampos());
        setVisible(true);
    }
  

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Cliente b : banco.getClientes().buscarTodos()) {
            modeloTabela.addRow(new Object[]{b.getId(), b.getNome(), b.getCpf(), b.getTelefone()});
        }
    }
    private void limparCampos() {
        campoId.setText("");
        campoNome.setText("");
        campoCpf.setText("");
        campoTelefone.setText("");
    }
    
    private void preencherCamposDaLinha() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada != -1) {
            campoId.setText(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
            campoNome.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
            campoCpf.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
            campoTelefone.setText(modeloTabela.getValueAt(linhaSelecionada, 3).toString());
        }
    }
    
    private void cadastrar() {
        try {
            String nome = campoNome.getText();
            String cpf = campoCpf.getText();
            String telefone = campoTelefone.getText();
            if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty()) {
                throw new IllegalArgumentException("Todos os campos devem ser preenchidos!");
            }
            Integer id = banco.getProximoIdCliente();
            Cliente novo = new Cliente(id, nome, cpf, telefone);
            banco.getClientes().inserir(novo);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void atualizar() {
        try {
            int id = Integer.parseInt(campoId.getText());
            Cliente b = banco.getClientes().buscarPorId(id);
            if (b == null) {
                JOptionPane.showMessageDialog(this, "Cliente com ID " + id + " não encontrado.");
                return;
            }
            if (!campoNome.getText().isEmpty()) b.setNome(campoNome.getText());
            if (!campoCpf.getText().isEmpty()) b.setCpf(campoCpf.getText());
            if (!campoTelefone.getText().isEmpty()) b.setTelefone(campoTelefone.getText());
            banco.getClientes().alterar(b);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um ID válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void remover() {
        try {
            int id = Integer.parseInt(campoId.getText());
            Cliente cliente = banco.getClientes().buscarPorId(id);
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente com ID " + id + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o cliente \"" + cliente.getNome() + "\"?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirmacao == JOptionPane.YES_OPTION) {
                banco.getClientes().excluir(id);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um ID válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void buscarPorId() {
        try {
            int id = Integer.parseInt(campoId.getText());
            Cliente b = banco.getClientes().buscarPorId(id);
            modeloTabela.setRowCount(0);
            if (b != null) {
                modeloTabela.addRow(new Object[]{b.getId(), b.getNome(), b.getCpf(), b.getTelefone()});
            } else {
                JOptionPane.showMessageDialog(this, "Cliente com ID " + id + " não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um ID válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
