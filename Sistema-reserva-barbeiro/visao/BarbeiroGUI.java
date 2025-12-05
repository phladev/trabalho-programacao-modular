package visao;

import modelo.Barbeiro;
import persistencia.BancoDeDados;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BarbeiroGUI extends JFrame {
    private BancoDeDados banco;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoNome, campoCpf, campoTelefone, campoId;

    public BarbeiroGUI(BancoDeDados banco) {
        super("Gerenciar Barbeiros");
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
        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "CPF", "Telefone"}, 0);
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnRemover = new JButton("Remover");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnListar = new JButton("Listar Todos");
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnListar);
        add(painelBotoes, BorderLayout.SOUTH);
        atualizarTabela();
        btnCadastrar.addActionListener(e -> { cadastrar(); limparCampos(); });
        btnAtualizar.addActionListener(e -> { atualizar(); limparCampos(); });
        btnRemover.addActionListener(e -> { remover(); limparCampos(); });
        btnBuscar.addActionListener(e -> { buscarPorId(); limparCampos(); });
        btnListar.addActionListener(e -> atualizarTabela());
        setVisible(true);
    }
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Barbeiro b : banco.getBarbeiros().buscarTodos()) {
            modeloTabela.addRow(new Object[]{b.getNome(), b.getId(), b.getCpf(), b.getTelefone()});
        }
    }
    private void limparCampos() {
        campoId.setText("");
        campoNome.setText("");
        campoCpf.setText("");
        campoTelefone.setText("");
    }
    private void cadastrar() {
        try {
            String nome = campoNome.getText();
            String cpf = campoCpf.getText();
            String telefone = campoTelefone.getText();
            if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty()) {
                throw new IllegalArgumentException("Todos os campos devem ser preenchidos!");
            }
            Integer id = banco.getProximoIdBarbeiro();
            Barbeiro novo = new Barbeiro(id, nome, cpf, telefone);
            banco.getBarbeiros().inserir(novo);
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
    private void atualizar() {
        try {
            int id = Integer.parseInt(campoId.getText());
            Barbeiro b = banco.getBarbeiros().buscarPorId(id);
            if (b == null) {
                JOptionPane.showMessageDialog(this, "Barbeiro com ID " + id + " não encontrado.");
                return;
            }
            if (!campoNome.getText().isEmpty()) b.setNome(campoNome.getText());
            if (!campoCpf.getText().isEmpty()) b.setCpf(campoCpf.getText());
            if (!campoTelefone.getText().isEmpty()) b.setTelefone(campoTelefone.getText());
            banco.getBarbeiros().alterar(b);
            atualizarTabela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um ID válido!");
        }
    }
    private void remover() {
        try {
            int id = Integer.parseInt(campoId.getText());
            boolean sucesso = banco.getBarbeiros().excluir(id);
            if (!sucesso) {
                JOptionPane.showMessageDialog(this, "Barbeiro com ID " + id + " não encontrado.");
            }
            atualizarTabela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um ID válido!");
        }
    }
    private void buscarPorId() {
        try {
            int id = Integer.parseInt(campoId.getText());
            Barbeiro b = banco.getBarbeiros().buscarPorId(id);
            modeloTabela.setRowCount(0);
            if (b != null) {
                modeloTabela.addRow(new Object[]{b.getId(), b.getNome(), b.getCpf(), b.getTelefone()});
            } else {
                JOptionPane.showMessageDialog(this, "Barbeiro com ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um ID válido!");
        }
    }
}
