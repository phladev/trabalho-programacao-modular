package visao;

import modelo.Barbeiro;
import persistencia.BancoDeDados;
import persistencia.Persistente;
import java.util.List;
import java.util.Scanner;

public class MenuBarbeiro {
  private Persistente<Barbeiro> barbeiros;
  private Scanner scanner;
  
  public MenuBarbeiro(BancoDeDados bancoDeDados, Scanner scanner) {
    this.barbeiros = bancoDeDados.getBarbeiros();
    this.scanner = scanner;
  }
  
  public void consultarInformacoesBarbeiros() {
    if (barbeiros.isEmpty()) {
      System.out.println("Nenhum barbeiro cadastrado.");
      return;
    }
    
    System.out.println("\n=== BARBEIROS CADASTRADOS ===");
    System.out.println(barbeiros.toString());
  }
  
  public void consultarAgendamentosBarbeiro(Persistente<modelo.Agendamento> agendamentos) {
    if (barbeiros.isEmpty()) {
      System.out.println("Nenhum barbeiro cadastrado.");
      return;
    }
    
    System.out.println("Barbeiros disponíveis:");
    List<Barbeiro> listaBarbeiros = barbeiros.buscarTodos();
    for (int i = 0; i < listaBarbeiros.size(); i++) {
      System.out.println((i + 1) + " - " + listaBarbeiros.get(i).getNome());
    }
    
    System.out.print("Escolha o barbeiro (número): ");
    if (scanner.hasNextInt()) {
      int escolha = scanner.nextInt();
      scanner.nextLine();
      
      if (escolha > 0 && escolha <= listaBarbeiros.size()) {
        Barbeiro barbeiro = listaBarbeiros.get(escolha - 1);
        System.out.println("\n=== AGENDAMENTOS DE " + barbeiro.getNome().toUpperCase() + " ===");
        
        boolean temAgendamentos = false;
        for (modelo.Agendamento agendamento : agendamentos.buscarTodos()) {
          if (agendamento.getBarbeiro().getId().equals(barbeiro.getId())) {
            System.out.println(agendamento.toString());
            System.out.println("-".repeat(30));
            temAgendamentos = true;
          }
        }
        
        if (!temAgendamentos) {
          System.out.println("Este barbeiro não possui agendamentos.");
        }
      } else {
        System.out.println("Opção inválida!");
      }
    } else {
        System.out.println("Por favor, digite apenas números!");
        scanner.nextLine();
    }
  }
  
  public Barbeiro selecionarBarbeiro() {
    if (barbeiros.isEmpty()) {
      System.out.println("Nenhum barbeiro cadastrado.");
      return null;
    }
    
    System.out.println("\nBarbeiros disponíveis:");
    List<Barbeiro> listaBarbeiros = barbeiros.buscarTodos();
    for (int i = 0; i < listaBarbeiros.size(); i++) {
      System.out.println((i + 1) + " - " + listaBarbeiros.get(i).getNome());
    }
    System.out.print("Escolha o barbeiro: ");
    
    if (!scanner.hasNextInt()) {
      System.out.println("Por favor, digite apenas números!");
      scanner.nextLine();
      return null;
    }
    
    int escolhaBarbeiro = scanner.nextInt();
    scanner.nextLine();
    
    if (escolhaBarbeiro < 1 || escolhaBarbeiro > listaBarbeiros.size()) {
      System.out.println("Barbeiro inválido!");
      return null;
    }
    
    return listaBarbeiros.get(escolhaBarbeiro - 1);
  }
}