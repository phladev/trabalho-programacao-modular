package visao;

import persistencia.BancoDeDados;
import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {
    private BancoDeDados bancoDeDados;

    public JanelaPrincipal() {
        super("Sistema de Reserva de Barbeiro");
        this.bancoDeDados = new BancoDeDados();
        
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel painelTitulo = new JPanel();
        painelTitulo.setBackground(new Color(41, 128, 185));
        JLabel titulo = new JLabel("Sistema de Reserva de Barbeiro");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        painelTitulo.add(titulo);
        add(painelTitulo, BorderLayout.NORTH);
        
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new GridLayout(5, 1, 10, 10));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        JButton btnClientes = criarBotao("Gerenciar Clientes", new Color(46, 204, 113));
        btnClientes.addActionListener(e -> abrirClienteGUI());
        
        JButton btnBarbeiros = criarBotao("Gerenciar Barbeiros", new Color(52, 152, 219));
        btnBarbeiros.addActionListener(e -> abrirBarbeiroGUI());
        
        JButton btnServicos = criarBotao("Gerenciar Serviços", new Color(155, 89, 182));
        btnServicos.addActionListener(e -> abrirServicoGUI());
        
        JButton btnAgendamentos = criarBotao("Gerenciar Agendamentos", new Color(230, 126, 34));
        btnAgendamentos.addActionListener(e -> abrirAgendamentoGUI());
        
        JButton btnSair = criarBotao("Sair", new Color(231, 76, 60));
        btnSair.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente sair do sistema?",
                "Confirmar Saída",
                JOptionPane.YES_NO_OPTION
            );
            if (resposta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        
        painelCentral.add(btnClientes);
        painelCentral.add(btnBarbeiros);
        painelCentral.add(btnServicos);
        painelCentral.add(btnAgendamentos);
        painelCentral.add(btnSair);
        
        add(painelCentral, BorderLayout.CENTER);
        
        JPanel painelRodape = new JPanel();
        painelRodape.setBackground(new Color(52, 73, 94));
        JLabel rodape = new JLabel("© 2025 - Sistema de Barbeiro v1.0");
        rodape.setForeground(Color.WHITE);
        painelRodape.add(rodape);
        add(painelRodape, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor);
            }
        });
        
        return botao;
    }
    
    private void abrirClienteGUI() {
        SwingUtilities.invokeLater(() -> {
            new ClienteGUI(bancoDeDados);
        });
    }
    
    private void abrirBarbeiroGUI() {
        SwingUtilities.invokeLater(() -> {
            new BarbeiroGUI(bancoDeDados);
        });
    }
    
    private void abrirServicoGUI() {
        JOptionPane.showMessageDialog(
            this,
            "Painel de Serviços ainda não implementado.\nEm desenvolvimento...",
            "Em Desenvolvimento",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void abrirAgendamentoGUI() {
        JOptionPane.showMessageDialog(
            this,
            "Painel de Agendamentos ainda não implementado.\nEm desenvolvimento...",
            "Em Desenvolvimento",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new JanelaPrincipal();
        });
    }
}
