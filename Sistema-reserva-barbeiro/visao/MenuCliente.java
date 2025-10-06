package visao;

import modelo.Cliente;
import persistencia.BancoDeDados;
import persistencia.Persistente;
import java.util.List;
import java.util.Scanner;

public class MenuCliente {
  private Persistente<Cliente> clientes;
  private Scanner scanner;
  
  public MenuCliente(BancoDeDados bancoDeDados, Scanner scanner) {
    this.clientes = bancoDeDados.getClientes();
    this.scanner = scanner;
  }
  
  public void consultarInformacoesClientes() {
    if (clientes.isEmpty()) {
      System.out.println("Nenhum cliente cadastrado.");
      return;
    }
      
    System.out.println("\n=== CLIENTES CADASTRADOS ===");
    System.out.println(clientes.toString());
  }
  
  public void consultarAgendamentosCliente(Persistente<modelo.Agendamento> agendamentos) {
    if (clientes.isEmpty()) {
      System.out.println("Nenhum cliente cadastrado.");
      return;
    }
    
    System.out.println("Clientes disponíveis:");
    List<Cliente> listaClientes = clientes.buscarTodos();
    for (int i = 0; i < listaClientes.size(); i++) {
      System.out.println((i + 1) + " - " + listaClientes.get(i).getNome());
    }
    
    System.out.print("Escolha o cliente (número): ");
    if (scanner.hasNextInt()) {
      int escolha = scanner.nextInt();
      scanner.nextLine();
      
      if (escolha > 0 && escolha <= listaClientes.size()) {
        Cliente cliente = listaClientes.get(escolha - 1);
        System.out.println("\n=== AGENDAMENTOS DE " + cliente.getNome().toUpperCase() + " ===");
        
        boolean temAgendamentos = false;
        for (modelo.Agendamento agendamento : agendamentos.buscarTodos()) {
          if (agendamento.getCliente().getId().equals(cliente.getId())) {
            System.out.println(agendamento.toString());
            System.out.println("-".repeat(30));
            temAgendamentos = true;
          }
        }
        
        if (!temAgendamentos) {
          System.out.println("Este cliente não possui agendamentos.");
        }
      } else {
        System.out.println("Opção inválida!");
      }
    } else {
      System.out.println("Por favor, digite apenas números!");
      scanner.nextLine();
    }
  }
  
  public Cliente selecionarCliente() {
    if (clientes.isEmpty()) {
      System.out.println("Nenhum cliente cadastrado.");
      return null;
    }
    
    System.out.println("Clientes disponíveis:");
    List<Cliente> listaClientes = clientes.buscarTodos();
    for (int i = 0; i < listaClientes.size(); i++) {
      System.out.println((i + 1) + " - " + listaClientes.get(i).getNome());
    }
    System.out.print("Escolha o cliente: ");
    
    if (!scanner.hasNextInt()) {
      System.out.println("Por favor, digite apenas números!");
      scanner.nextLine();
      return null;
    }
    
    int escolhaCliente = scanner.nextInt();
    scanner.nextLine();
    
    if (escolhaCliente < 1 || escolhaCliente > listaClientes.size()) {
      System.out.println("Cliente inválido!");
      return null;
    }
    
    return listaClientes.get(escolhaCliente - 1);
}
}