package visao;

import persistencia.BancoDeDados;
import persistencia.Persistente;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.Agendamento;
import modelo.Barbeiro;
import modelo.Cliente;
import modelo.Servico;
import modelo.ServicoAgendamento;
import modelo.StatusAgendamento;

public class MenuAgendamento {
  private Persistente<Agendamento> agendamentos;
  private BancoDeDados bancoDeDados;
  private Scanner scanner;
  
  public MenuAgendamento(BancoDeDados bancoDeDados, Scanner scanner) {
    this.agendamentos = bancoDeDados.getAgendamentos();
    this.bancoDeDados = bancoDeDados;
    this.scanner = scanner;
  }
  
  public void agendarServico(MenuCliente menuCliente, MenuBarbeiro menuBarbeiro, MenuServico menuServico) {
    if (bancoDeDados.getClientes().isEmpty() || bancoDeDados.getBarbeiros().isEmpty() || bancoDeDados.getServicos().isEmpty()) {
      System.out.println("É necessário ter clientes, barbeiros e serviços cadastrados para fazer agendamentos.");
      return;
    }
    
    System.out.println("\n=== NOVO AGENDAMENTO ===");
    
    Cliente cliente = menuCliente.selecionarCliente();
    if (cliente == null) {
      return;
    }
    
    Barbeiro barbeiro = menuBarbeiro.selecionarBarbeiro();
    if (barbeiro == null) {
      return;
    }
    
    List<Servico> servicos = menuServico.selecionarServico();
    if (servicos == null) {
      return;
    }

    LocalDateTime dataHoraSelecionada = selecionarHorarioDisponivel(barbeiro);
    if (dataHoraSelecionada == null) {
      return;
    }
    
    Integer novoId = bancoDeDados.getProximoId();
    Agendamento agendamento = new Agendamento(novoId, cliente, barbeiro, dataHoraSelecionada);

    for (Servico servico : servicos) {
      Integer idServicoAgendamento = bancoDeDados.getProximoId() + 1;
      ServicoAgendamento servicoAgendamento = new ServicoAgendamento(idServicoAgendamento, servico);
      agendamento.adicionarServico(servicoAgendamento);
    }

    agendamentos.inserir(agendamento);
    cliente.adicionarAgendamento(agendamento);
    barbeiro.adicionarAgendamento(agendamento);
    
    System.out.println("\nAgendamento realizado com sucesso!");
    System.out.println("Detalhes:");
    System.out.println(agendamento.toString());
  }
 
  public void atualizarServico() {
    if (agendamentos.isEmpty()) {
      System.out.println("Nenhum agendamento encontrado.");
      return;
    }
    
    System.out.println("\n=== AGENDAMENTOS ATIVOS ===");
    List<Agendamento> agendamentosAtivos = new ArrayList<>();
    
    for (Agendamento agendamento : agendamentos.buscarTodos()) {
      if (agendamento.getStatus() != StatusAgendamento.CANCELADO) {
        agendamentosAtivos.add(agendamento);
        System.out.println((agendamentosAtivos.size()) + " - ID: " + agendamento.getId() + 
                          " | Cliente: " + agendamento.getCliente().getNome() + 
                          " | Barbeiro: " + agendamento.getBarbeiro().getNome() +
                          " | Data e Hora: " + agendamento.getDataHora());
      }
    }

    if (agendamentosAtivos.isEmpty()) {
      System.out.println("Nenhum agendamento ativo encontrado.");
      return;
    }
    
    System.out.print("Escolha o agendamento para atualizar: ");
    
    if (!scanner.hasNextInt()) {
      System.out.println("Por favor, digite apenas números!");
      scanner.nextLine();
      return;
    }
    
    int escolha = scanner.nextInt();
    scanner.nextLine();
    
    if (escolha < 1 || escolha > agendamentosAtivos.size()) {
      System.out.println("Opção inválida!");
      return;
    }
    
    Agendamento agendamento = agendamentosAtivos.get(escolha - 1);
    
    System.out.println("\nAgendamento selecionado:");
    System.out.println(agendamento);
    
    System.out.println("\nO que deseja atualizar?");
    System.out.println("1 - Data e Hora");
    System.out.println("2 - Status");
    System.out.print("Escolha uma opção: ");
    
    if (!scanner.hasNextInt()) {
      System.out.println("Por favor, digite apenas números!");
      scanner.nextLine();
      return;
    }
    
    int opcaoAtualizacao = scanner.nextInt();
    scanner.nextLine();
    
    switch (opcaoAtualizacao) {
      case 1:
        Barbeiro barbeiro = agendamento.getBarbeiro();
        LocalDateTime novaDataHora = selecionarHorarioDisponivel(barbeiro);
        if (novaDataHora != null) {
          agendamento.getBarbeiro().removerDiaDisponivel(novaDataHora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
          agendamento.getBarbeiro().adicionarDiaDisponivel(agendamento.getDataHora());
          agendamento.setDataHora(novaDataHora);
          agendamentos.alterar(agendamento);
          System.out.println("Data e hora atualizadas com sucesso!");
        } else {
          System.out.println("Atualização de data e hora cancelada.");
        }
      case 2:
        System.out.println("Escolha o novo status:");
        for (StatusAgendamento status : StatusAgendamento.values()) {
          System.out.println(" - " + status);
        }
        String novoStatus = scanner.nextLine();
        agendamento.setStatus(StatusAgendamento.valueOf(novoStatus));
        agendamentos.alterar(agendamento);
        System.out.println("Status atualizado com sucesso!");
        break;
      default:
        System.out.println("Opção inválida!");
    }
  }

  public void desagendarServico() {
    if (agendamentos.isEmpty()) {
      System.out.println("Nenhum agendamento encontrado.");
      return;
    }
    
    System.out.println("\n=== AGENDAMENTOS ATIVOS ===");
    List<Agendamento> agendamentosAtivos = new ArrayList<>();

    for (Agendamento agendamento : agendamentos.buscarTodos()) {
      if (agendamento.getStatus() != StatusAgendamento.CANCELADO) {
        agendamentosAtivos.add(agendamento);
        System.out.println((agendamentosAtivos.size()) + " - ID: " + agendamento.getId() + 
                          " | Cliente: " + agendamento.getCliente().getNome() + 
                          " | Barbeiro: " + agendamento.getBarbeiro().getNome() +
                          " | Data e Hora: " + agendamento.getDataHora());
      }
    }

    if (agendamentosAtivos.isEmpty()) {
      System.out.println("Nenhum agendamento ativo encontrado.");
      return;
    }
    
    System.out.print("Escolha o agendamento para cancelar: ");
    
    if (!scanner.hasNextInt()) {
      System.out.println("Por favor, digite apenas números!");
      scanner.nextLine();
      return;
    }
    
    int escolha = scanner.nextInt();
    scanner.nextLine();
    
    if (escolha > 0 && escolha <= agendamentosAtivos.size()) {
      Agendamento agendamento = agendamentosAtivos.get(escolha - 1);
      agendamento.setStatus(StatusAgendamento.CANCELADO);
      agendamento.getBarbeiro().adicionarDiaDisponivel(agendamento.getDataHora());
      
      agendamentos.alterar(agendamento);
      
      System.out.println("Agendamento cancelado com sucesso!");
    } else {
      System.out.println("Opção inválida!");
    }
  }

  public void buscarAgendamentoPorId() {
    System.out.print("Digite o ID do agendamento que deseja consultar: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    Agendamento agendamento = agendamentos.buscarPorId(id);
    if (agendamento == null) {
      System.out.println("Agendamento com ID " + id + " não encontrado.");
      return;
    }
    System.out.println("Informações do Agendamento:");
    System.out.println(agendamento);
  }

  public void listarTodosAgendamentos() {
    System.out.println("Lista de todos os agendamentos:");
    List<Agendamento> listaAgendamentos = agendamentos.buscarTodos();
    if (listaAgendamentos.isEmpty()) {
      System.out.println("Nenhum agendamento cadastrado.");
      return;
    }
    for (Agendamento agendamento : listaAgendamentos) {
      System.out.println("\n" + agendamento);
    }
  }
  
  private LocalDateTime selecionarHorarioDisponivel(Barbeiro barbeiro) {
    List<String> horariosDisponiveis = barbeiro.getHorarioDisponivelList();
    
    if (horariosDisponiveis.isEmpty()) {
      System.out.println("Este barbeiro não possui horários disponíveis cadastrados.");
      return null;
    }
    
    System.out.println("\n=== HORÁRIOS DISPONÍVEIS ===");
    for (int i = 0; i < horariosDisponiveis.size(); i++) {
      System.out.println((i + 1) + " - " + horariosDisponiveis.get(i));
    }
    
    System.out.print("Escolha um horário disponível: ");
    
    if (!scanner.hasNextInt()) {
      System.out.println("Por favor, digite apenas números!");
      scanner.nextLine();
      return null;
    }
    
    int escolha = scanner.nextInt();
    scanner.nextLine();
    
    if (escolha < 1 || escolha > horariosDisponiveis.size()) {
      System.out.println("Opção inválida!");
      return null;
    }
    
    String horarioSelecionado = horariosDisponiveis.get(escolha - 1);
  
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    LocalDateTime dataHora = LocalDateTime.parse(horarioSelecionado, formatter);

    barbeiro.removerDiaDisponivel(horarioSelecionado);
    
    return dataHora;
  }
}