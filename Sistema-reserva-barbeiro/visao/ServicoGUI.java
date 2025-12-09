package visao;

import modelo.Servico;
import persistencia.BancoDeDados;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ServicoGUI extends JFrame {
    private BancoDeDados banco;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoNome, campoPreco, campoTempo, campoId;

    public ServicoGUI(BancoDeDados banco) {
        super("Gerenciar Serviços");
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
        painelTopo.add(new JLabel("Preço (R$):"));
        campoPreco = new JTextField();
        painelTopo.add(campoPreco);
        painelTopo.add(new JLabel("Tempo (minutos):"));
        campoTempo = new JTextField();
        painelTopo.add(campoTempo);
        add(painelTopo, BorderLayout.NORTH);
        
        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "Preço (R$)", "Tempo (min)"}, 0) {
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
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnListar);
        add(painelBotoes, BorderLayout.SOUTH);
        
        atualizarTabela();
        
        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnRemover.addActionListener(e -> remover());
        btnBuscar.addActionListener(e -> buscarPorId());
        btnListar.addActionListener(e -> atualizarTabela());
        
        setVisible(true);
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Servico s : banco.getServicos().buscarTodos()) {
            modeloTabela.addRow(new Object[]{
                s.getId(), 
                s.getNome(), 
                String.format("%.2f", s.getPreco()), 
                String.format("%.1f", s.getTempo())
            });
        }
    }
    
    private void limparCampos() {
        campoId.setText("");
        campoNome.setText("");
        campoPreco.setText("");
        campoTempo.setText("");
    }
    
    private void preencherCamposDaLinha() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada != -1) {
            campoId.setText(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
            campoNome.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
            String preco = modeloTabela.getValueAt(linhaSelecionada, 2).toString();
            campoPreco.setText(preco);
            String tempo = modeloTabela.getValueAt(linhaSelecionada, 3).toString();
            campoTempo.setText(tempo);
        }
    }
    
    private void cadastrar() {
        try {
            String nome = campoNome.getText();
            String precoStr = campoPreco.getText().replace(",", ".");
            String tempoStr = campoTempo.getText().replace(",", ".");
            
            if (nome.isEmpty() || campoPreco.getText().isEmpty() || campoTempo.getText().isEmpty()) {
                throw new IllegalArgumentException("Todos os campos devem ser preenchidos!");
            }
            
            double preco = Double.parseDouble(precoStr);
            double tempo = Double.parseDouble(tempoStr);
            
            if (preco <= 0) {
                throw new IllegalArgumentException("O preço deve ser maior que zero!");
            }
            
            if (tempo <= 0) {
                throw new IllegalArgumentException("O tempo deve ser maior que zero!");
            }
            
            Integer id = banco.getProximoIdServico();
            Servico novo = new Servico(id, nome, preco, tempo);
            banco.getServicos().inserir(novo);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço e tempo devem ser números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizar() {
        try {
            int id = Integer.parseInt(campoId.getText());
            Servico s = banco.getServicos().buscarPorId(id);
            
            if (s == null) {
                JOptionPane.showMessageDialog(this, "Serviço com ID " + id + " não encontrado.");
                return;
            }
            
            if (!campoNome.getText().isEmpty()) {
                s.setNome(campoNome.getText());
            }
            
            if (!campoPreco.getText().isEmpty()) {
                double preco = Double.parseDouble(campoPreco.getText().replace(",", "."));
                if (preco <= 0) {
                    throw new IllegalArgumentException("O preço deve ser maior que zero!");
                }
                s.setPreco(preco);
            }
            
            if (!campoTempo.getText().isEmpty()) {
                double tempo = Double.parseDouble(campoTempo.getText().replace(",", "."));
                if (tempo <= 0) {
                    throw new IllegalArgumentException("O tempo deve ser maior que zero!");
                }
                s.setTempo(tempo);
            }
            
            banco.getServicos().alterar(s);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Serviço atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite valores numéricos válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void remover() {
        try {
            int id = Integer.parseInt(campoId.getText());
            Servico servico = banco.getServicos().buscarPorId(id);
            if (servico == null) {
                JOptionPane.showMessageDialog(this, "Serviço com ID " + id + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o serviço \"" + servico.getNome() + "\"?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirmacao == JOptionPane.YES_OPTION) {
                banco.getServicos().excluir(id);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Serviço removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um ID válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarPorId() {
        try {
            int id = Integer.parseInt(campoId.getText());
            Servico s = banco.getServicos().buscarPorId(id);
            modeloTabela.setRowCount(0);
            
            if (s != null) {
                modeloTabela.addRow(new Object[]{
                    s.getId(), 
                    s.getNome(), 
                    String.format("%.2f", s.getPreco()), 
                    String.format("%.1f", s.getTempo())
                });
            } else {
                JOptionPane.showMessageDialog(this, "Serviço com ID " + id + " não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um ID válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
