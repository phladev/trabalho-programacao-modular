package visao;

import modelo.Servico;
import persistencia.BancoDeDados;
import persistencia.Persistente;
import java.util.List;
import java.util.Scanner;

public class MenuServico {
  private Persistente<Servico> servicos;
  private Scanner scanner;
  
  public MenuServico(BancoDeDados bancoDeDados, Scanner scanner) {
    this.servicos = bancoDeDados.getServicos();
    this.scanner = scanner;
  }
  
  public void consultarServicos() {
    if (servicos.isEmpty()) {
      System.out.println("Nenhum serviço cadastrado.");
      return;
    }
    
    System.out.println("\n=== SERVIÇOS DISPONÍVEIS ===");
    System.out.println(servicos.toString());
  }
  
  public Servico selecionarServico() {
    if (servicos.isEmpty()) {
      System.out.println("Nenhum serviço cadastrado.");
      return null;
    }
    
    System.out.println("\nServiços disponíveis:");
    List<Servico> listaServicos = servicos.buscarTodos();
    for (int i = 0; i < listaServicos.size(); i++) {
      Servico s = listaServicos.get(i);
      System.out.println((i + 1) + " - " + s.getNome() + " (R$ " + s.getPreco() + ")");
    }
    System.out.print("Escolha o serviço: ");
    
    if (!scanner.hasNextInt()) {
      System.out.println("Por favor, digite apenas números!");
      scanner.nextLine();
      return null;
    }
    
    int escolhaServico = scanner.nextInt();
    scanner.nextLine();
    
    if (escolhaServico < 1 || escolhaServico > listaServicos.size()) {
      System.out.println("Serviço inválido!");
      return null;
    }
    
    return listaServicos.get(escolhaServico - 1);
  }
}