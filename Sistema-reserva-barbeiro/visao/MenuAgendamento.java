package visao;

import persistencia.BancoDeDados;
import persistencia.Persistente;
import java.time.LocalDateTime;
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
    
    Servico servico = menuServico.selecionarServico();
    if (servico == null) {
      return;
    }
    
    int novoId = bancoDeDados.getProximoId();
    Agendamento agendamento = new Agendamento(novoId, cliente, barbeiro, LocalDateTime.now().plusDays(1));
    
    int idServicoAgendamento = bancoDeDados.getProximoId() + 1;
    ServicoAgendamento servicoAgendamento = new ServicoAgendamento(idServicoAgendamento, servico);
    agendamento.adicionarServico(servicoAgendamento);
    
    agendamentos.inserir(agendamento);
    cliente.adicionarAgendamento(agendamento);
    barbeiro.adicionarAgendamento(agendamento);
    
    System.out.println("\nAgendamento realizado com sucesso!");
    System.out.println("Detalhes:");
    System.out.println(agendamento.toString());
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
                          " | Barbeiro: " + agendamento.getBarbeiro().getNome());
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
      
      agendamentos.alterar(agendamento);
      
      System.out.println("Agendamento cancelado com sucesso!");
    } else {
      System.out.println("Opção inválida!");
    }
  }
}