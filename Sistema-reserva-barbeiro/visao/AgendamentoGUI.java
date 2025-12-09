package visao;

import modelo.*;
import persistencia.BancoDeDados;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AgendamentoGUI extends JFrame {
    private BancoDeDados banco;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoId;

    public AgendamentoGUI(BancoDeDados banco) {
        super("Gerenciar Agendamentos");
        this.banco = banco;
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new FlowLayout());
        painelTopo.add(new JLabel("ID do Agendamento:"));
        campoId = new JTextField(10);
        painelTopo.add(campoId);
        add(painelTopo, BorderLayout.NORTH);
        
        modeloTabela = new DefaultTableModel(
            new String[]{"ID", "Cliente", "Barbeiro", "Data/Hora", "Status", "Valor Total", "Tempo Total"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnNovo = new JButton("Novo Agendamento");
        JButton btnDetalhes = new JButton("Ver Detalhes");
        JButton btnEditar = new JButton("Editar");
        JButton btnCancelar = new JButton("Cancelar Agendamento");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnListar = new JButton("Listar Todos");
        
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnDetalhes);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnListar);
        add(painelBotoes, BorderLayout.SOUTH);
        
        atualizarTabela();
        
        btnNovo.addActionListener(e -> novoAgendamento());
        btnDetalhes.addActionListener(e -> verDetalhes());
        btnEditar.addActionListener(e -> editarAgendamento());
        btnCancelar.addActionListener(e -> cancelarAgendamento());
        btnBuscar.addActionListener(e -> buscarPorId());
        btnListar.addActionListener(e -> atualizarTabela());
        
        setVisible(true);
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Agendamento a : banco.getAgendamentos().buscarTodos()) {
            modeloTabela.addRow(new Object[]{
                a.getId(),
                a.getCliente().getNome(),
                a.getBarbeiro().getNome(),
                a.getDataHora(),
                a.getStatus(),
                String.format("R$ %.2f", a.getValorTotal()),
                String.format("%.0f min", a.getTempoTotal())
            });
        }
    }

    private void novoAgendamento() {
        if (banco.getClientes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre clientes antes de criar agendamentos!");
            return;
        }
        
        if (banco.getBarbeiros().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre barbeiros antes de criar agendamentos!");
            return;
        }
        
        if (banco.getServicos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre serviços antes de criar agendamentos!");
            return;
        }
        
        Cliente cliente = selecionarCliente();
        if (cliente == null) return;
        
        Barbeiro barbeiro = selecionarBarbeiro();
        if (barbeiro == null) return;
        
        LocalDateTime dataHora = selecionarDataHora(barbeiro);
        if (dataHora == null) return;
        
        Integer id = banco.getProximoIdAgendamento();
        Agendamento agendamento = new Agendamento(id, cliente, barbeiro, dataHora);
        
        boolean adicionouServicos = gerenciarServicosNovoAgendamento(agendamento);
        if (!adicionouServicos) {
            JOptionPane.showMessageDialog(this, "Agendamento cancelado. Nenhum serviço foi adicionado.");
            return;
        }
        
        barbeiro.removerDiaDisponivel(dataHora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        banco.getAgendamentos().inserir(agendamento);
        atualizarTabela();
        
        JOptionPane.showMessageDialog(this, 
            "Agendamento criado com sucesso!\n" +
            "Cliente: " + cliente.getNome() + "\n" +
            "Barbeiro: " + barbeiro.getNome() + "\n" +
            "Data/Hora: " + agendamento.getDataHora() + "\n" +
            "Valor Total: R$ " + String.format("%.2f", agendamento.getValorTotal()) + "\n" +
            "Tempo Total: " + agendamento.getTempoTotal() + " min"
        );
    }

    private Cliente selecionarCliente() {
        List<Cliente> clientes = banco.getClientes().buscarTodos();
        String[] opcoesClientes = new String[clientes.size()];
        
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            opcoesClientes[i] = c.getId() + " - " + c.getNome();
        }
        
        String escolha = (String) JOptionPane.showInputDialog(
            this,
            "Selecione o cliente:",
            "Cliente",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoesClientes,
            opcoesClientes[0]
        );
        
        if (escolha == null) return null;
        
        int idCliente = Integer.parseInt(escolha.split(" - ")[0]);
        return banco.getClientes().buscarPorId(idCliente);
    }

    private Barbeiro selecionarBarbeiro() {
        List<Barbeiro> barbeiros = banco.getBarbeiros().buscarTodos();
        String[] opcoesBarbeiros = new String[barbeiros.size()];
        
        for (int i = 0; i < barbeiros.size(); i++) {
            Barbeiro b = barbeiros.get(i);
            opcoesBarbeiros[i] = b.getId() + " - " + b.getNome();
        }
        
        String escolha = (String) JOptionPane.showInputDialog(
            this,
            "Selecione o barbeiro:",
            "Barbeiro",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoesBarbeiros,
            opcoesBarbeiros[0]
        );
        
        if (escolha == null) return null;
        
        int idBarbeiro = Integer.parseInt(escolha.split(" - ")[0]);
        return banco.getBarbeiros().buscarPorId(idBarbeiro);
    }

    private LocalDateTime selecionarDataHora(Barbeiro barbeiro) {
        List<String> horariosDisponiveis = barbeiro.getHorarioDisponivelList();
        
        if (horariosDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Este barbeiro não possui horários disponíveis!");
            return null;
        }
        
        String escolha = (String) JOptionPane.showInputDialog(
            this,
            "Selecione o horário:",
            "Data e Hora",
            JOptionPane.QUESTION_MESSAGE,
            null,
            horariosDisponiveis.toArray(),
            horariosDisponiveis.get(0)
        );
        
        if (escolha == null) return null;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(escolha, formatter);
    }

    private boolean gerenciarServicosNovoAgendamento(Agendamento agendamento) {
        boolean continuarAdicionando = true;
        
        while (continuarAdicionando) {
            List<Servico> servicosDisponiveis = banco.getServicos().buscarTodos();
            String[] opcoesServicos = new String[servicosDisponiveis.size()];
            
            for (int i = 0; i < servicosDisponiveis.size(); i++) {
                Servico s = servicosDisponiveis.get(i);
                opcoesServicos[i] = s.getNome() + " - R$ " + String.format("%.2f", s.getPreco()) + 
                                   " - " + s.getTempo() + " min";
            }
            
            String escolha = (String) JOptionPane.showInputDialog(
                this,
                "Selecione um serviço para adicionar:\n\n" +
                "Serviços atuais: " + agendamento.getServicos().size() + "\n" +
                "Valor total: R$ " + String.format("%.2f", agendamento.getValorTotal()),
                "Adicionar Serviço",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesServicos,
                opcoesServicos[0]
            );
            
            if (escolha == null) {
                if (agendamento.getServicos().isEmpty()) {
                    int resposta = JOptionPane.showConfirmDialog(
                        this,
                        "Nenhum serviço foi adicionado. Deseja cancelar o agendamento?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION
                    );
                    if (resposta == JOptionPane.YES_OPTION) {
                        return false;
                    }
                    continue;
                }
                break;
            }
            
            String nomeServico = escolha.split(" - ")[0];
            Servico servicoSelecionado = null;
            
            for (Servico s : servicosDisponiveis) {
                if (s.getNome().equals(nomeServico)) {
                    servicoSelecionado = s;
                    break;
                }
            }
            
            if (servicoSelecionado != null) {
                Integer idServicoAgendamento = banco.getProximoIdAgendamento();
                ServicoAgendamento sa = new ServicoAgendamento(idServicoAgendamento, servicoSelecionado);
                agendamento.adicionarServico(sa);
            }
            
            int resposta = JOptionPane.showConfirmDialog(
                this,
                "Serviço adicionado!\n\nDeseja adicionar outro serviço?",
                "Adicionar mais?",
                JOptionPane.YES_NO_OPTION
            );
            
            continuarAdicionando = (resposta == JOptionPane.YES_OPTION);
        }
        
        return !agendamento.getServicos().isEmpty();
    }

    private void verDetalhes() {
        int linhaSelecionada = tabela.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            if (!campoId.getText().isEmpty()) {
                try {
                    int id = Integer.parseInt(campoId.getText());
                    Agendamento agendamento = banco.getAgendamentos().buscarPorId(id);
                    
                    if (agendamento == null) {
                        JOptionPane.showMessageDialog(this, "Agendamento não encontrado!");
                        return;
                    }
                    
                    mostrarDetalhes(agendamento);
                    return;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID inválido!");
                    return;
                }
            }
            
            JOptionPane.showMessageDialog(this, "Selecione um agendamento na tabela ou digite o ID!");
            return;
        }
        
        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        Agendamento agendamento = banco.getAgendamentos().buscarPorId(id);
        mostrarDetalhes(agendamento);
    }

    private void mostrarDetalhes(Agendamento agendamento) {
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("ID: ").append(agendamento.getId()).append("\n");
        detalhes.append("Cliente: ").append(agendamento.getCliente().getNome()).append("\n");
        detalhes.append("Barbeiro: ").append(agendamento.getBarbeiro().getNome()).append("\n");
        detalhes.append("Data/Hora: ").append(agendamento.getDataHora()).append("\n");
        detalhes.append("Status: ").append(agendamento.getStatus()).append("\n\n");
        detalhes.append("Serviços:\n");
        
        for (ServicoAgendamento sa : agendamento.getServicos()) {
            detalhes.append("  • ").append(sa.getServico().getNome())
                   .append(" - R$ ").append(String.format("%.2f", sa.getPreco()))
                   .append(" - ").append(sa.getTempo()).append(" min\n");
        }
        
        detalhes.append("\nValor Total: R$ ").append(String.format("%.2f", agendamento.getValorTotal()));
        detalhes.append("\nTempo Total: ").append(agendamento.getTempoTotal()).append(" min");
        
        JTextArea textArea = new JTextArea(detalhes.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Detalhes do Agendamento", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editarAgendamento() {
        int linhaSelecionada = tabela.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento na tabela!");
            return;
        }
        
        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        Agendamento agendamento = banco.getAgendamentos().buscarPorId(id);
        
        String[] opcoes = {"Alterar Data/Hora", "Alterar Status", "Gerenciar Serviços", "Cancelar"};
        int escolha = JOptionPane.showOptionDialog(
            this,
            "O que deseja editar?",
            "Editar Agendamento",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            opcoes[0]
        );
        
        switch (escolha) {
            case 0:
                alterarDataHora(agendamento);
                break;
            case 1:
                alterarStatus(agendamento);
                break;
            case 2:
                gerenciarServicosAgendamento(agendamento);
                break;
        }
        
        atualizarTabela();
    }

    private void alterarStatus(Agendamento agendamento) {
        StatusAgendamento[] statuses = StatusAgendamento.values();
        StatusAgendamento novoStatus = (StatusAgendamento) JOptionPane.showInputDialog(
            this,
            "Selecione o novo status:",
            "Alterar Status",
            JOptionPane.QUESTION_MESSAGE,
            null,
            statuses,
            agendamento.getStatus()
        );
        
        if (novoStatus != null && novoStatus != agendamento.getStatus()) {
            agendamento.setStatus(novoStatus);
            banco.getAgendamentos().alterar(agendamento);
            JOptionPane.showMessageDialog(this, "Status alterado com sucesso!");
        }
    }

    private void alterarDataHora(Agendamento agendamento) {
        Barbeiro barbeiro = agendamento.getBarbeiro();
        List<String> horariosDisponiveis = barbeiro.getHorarioDisponivelList();
        
        if (horariosDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Este barbeiro não possui horários disponíveis!\n" +
                "Não é possível alterar a data/hora do agendamento.",
                "Sem Horários",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        String escolha = (String) JOptionPane.showInputDialog(
            this,
            "Data/Hora atual: " + agendamento.getDataHora() + "\n\nSelecione o novo horário:",
            "Alterar Data e Hora",
            JOptionPane.QUESTION_MESSAGE,
            null,
            horariosDisponiveis.toArray(),
            horariosDisponiveis.get(0)
        );
        
        if (escolha != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime novaDataHora = LocalDateTime.parse(escolha, formatter);
            
            barbeiro.adicionarDiaDisponivel(agendamento.getDataHora());
            barbeiro.removerDiaDisponivel(escolha);
            agendamento.setDataHora(novaDataHora);
            
            banco.getAgendamentos().alterar(agendamento);
            atualizarTabela();
            
            JOptionPane.showMessageDialog(
                this,
                "Data e hora alteradas com sucesso!\n" +
                "Nova data/hora: " + agendamento.getDataHora(),
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void gerenciarServicosAgendamento(Agendamento agendamento) {
        boolean continuar = true;
        
        while (continuar) {
            StringBuilder mensagem = new StringBuilder("Serviços atuais:\n\n");
            
            for (int i = 0; i < agendamento.getServicos().size(); i++) {
                ServicoAgendamento sa = agendamento.getServicos().get(i);
                mensagem.append((i + 1)).append(". ")
                       .append(sa.getServico().getNome())
                       .append(" - R$ ").append(String.format("%.2f", sa.getPreco()))
                       .append("\n");
            }
            
            mensagem.append("\nTotal: R$ ").append(String.format("%.2f", agendamento.getValorTotal()));
            
            String[] opcoes = {"Adicionar Serviço", "Remover Serviço", "Concluir"};
            int escolha = JOptionPane.showOptionDialog(
                this,
                mensagem.toString(),
                "Gerenciar Serviços",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[2]
            );
            
            switch (escolha) {
                case 0:
                    adicionarServicoAoAgendamento(agendamento);
                    break;
                case 1:
                    removerServicoDoAgendamento(agendamento);
                    break;
                default:
                    continuar = false;
            }
        }
        
        banco.getAgendamentos().alterar(agendamento);
        atualizarTabela();
    }

    private void adicionarServicoAoAgendamento(Agendamento agendamento) {
        List<Servico> servicosDisponiveis = banco.getServicos().buscarTodos();
        String[] opcoesServicos = new String[servicosDisponiveis.size()];
        
        for (int i = 0; i < servicosDisponiveis.size(); i++) {
            Servico s = servicosDisponiveis.get(i);
            opcoesServicos[i] = s.getNome() + " - R$ " + String.format("%.2f", s.getPreco());
        }
        
        String escolha = (String) JOptionPane.showInputDialog(
            this,
            "Selecione o serviço:",
            "Adicionar Serviço",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoesServicos,
            opcoesServicos[0]
        );
        
        if (escolha != null) {
            String nomeServico = escolha.split(" - ")[0];
            
            for (Servico s : servicosDisponiveis) {
                if (s.getNome().equals(nomeServico)) {
                    Integer id = banco.getProximoIdAgendamento();
                    ServicoAgendamento sa = new ServicoAgendamento(id, s);
                    agendamento.adicionarServico(sa);
                    JOptionPane.showMessageDialog(this, "Serviço adicionado com sucesso!");
                    break;
                }
            }
        }
    }

    private void removerServicoDoAgendamento(Agendamento agendamento) {
        if (agendamento.getServicos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há serviços para remover!");
            return;
        }
        
        String[] opcoesServicos = new String[agendamento.getServicos().size()];
        
        for (int i = 0; i < agendamento.getServicos().size(); i++) {
            ServicoAgendamento sa = agendamento.getServicos().get(i);
            opcoesServicos[i] = sa.getServico().getNome() + " - R$ " + String.format("%.2f", sa.getPreco());
        }
        
        String escolha = (String) JOptionPane.showInputDialog(
            this,
            "Selecione o serviço para remover:",
            "Remover Serviço",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoesServicos,
            opcoesServicos[0]
        );
        
        if (escolha != null) {
            for (int i = 0; i < agendamento.getServicos().size(); i++) {
                ServicoAgendamento sa = agendamento.getServicos().get(i);
                String opcao = sa.getServico().getNome() + " - R$ " + String.format("%.2f", sa.getPreco());
                
                if (opcao.equals(escolha)) {
                    agendamento.getServicos().remove(i);
                    JOptionPane.showMessageDialog(this, "Serviço removido com sucesso!");
                    break;
                }
            }
        }
    }

    private void cancelarAgendamento() {
        int linhaSelecionada = tabela.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento na tabela!");
            return;
        }
        
        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        Agendamento agendamento = banco.getAgendamentos().buscarPorId(id);
        
        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            JOptionPane.showMessageDialog(this, "Este agendamento já está cancelado!");
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente cancelar este agendamento?\n" +
            "Cliente: " + agendamento.getCliente().getNome() + "\n" +
            "Data/Hora: " + agendamento.getDataHora(),
            "Confirmar Cancelamento",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            agendamento.setStatus(StatusAgendamento.CANCELADO);
            banco.getAgendamentos().alterar(agendamento);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Agendamento cancelado com sucesso!");
        }
    }

    private void buscarPorId() {
        try {
            int id = Integer.parseInt(campoId.getText());
            Agendamento agendamento = banco.getAgendamentos().buscarPorId(id);
            
            modeloTabela.setRowCount(0);
            
            if (agendamento != null) {
                modeloTabela.addRow(new Object[]{
                    agendamento.getId(),
                    agendamento.getCliente().getNome(),
                    agendamento.getBarbeiro().getNome(),
                    agendamento.getDataHora(),
                    agendamento.getStatus(),
                    String.format("R$ %.2f", agendamento.getValorTotal()),
                    String.format("%.0f min", agendamento.getTempoTotal())
                });
            } else {
                JOptionPane.showMessageDialog(this, "Agendamento não encontrado!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um ID válido!");
        }
    }
}
