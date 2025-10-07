package visao;

import modelo.Barbeiro;
import persistencia.BancoDeDados;
import persistencia.Persistente;
import java.util.List;
import java.util.Scanner;

public class MenuBarbeiro {
  private Persistente<Barbeiro> barbeiros;
  private Scanner scanner;
  private BancoDeDados bancoDeDados;
  
  public MenuBarbeiro(BancoDeDados bancoDeDados, Scanner scanner) {
    this.barbeiros = bancoDeDados.getBarbeiros();
    this.bancoDeDados = bancoDeDados;
    this.scanner = scanner;
  }
  
  public void cadastrarBarbeiro() {
    System.out.print("Digite o nome do barbeiro: ");
    String nome = scanner.nextLine();
    System.out.print("Digite o telefone do barbeiro [(XX) XXXXX-XXXX]: ");
    String telefone = scanner.nextLine();
    System.out.print("Digite o CPF do barbeiro [XXX.XXX.XXX-XX]: ");
    String cpf = scanner.nextLine();

    Integer id = bancoDeDados.getProximoId();

    Barbeiro barbeiro = new Barbeiro(id, nome, cpf, telefone);
    
    System.out.println("\nAdicione os horários disponíveis do barbeiro:");
    System.out.println("Formato: DD-MM-AAAA HH:MM (ex: 07-10-2025 09:00)");
    System.out.println("Digite 'fim' para finalizar");
    
    while (true) {
      System.out.print("Horário disponível: ");
      String horario = scanner.nextLine();
      
      if (horario.equalsIgnoreCase("fim")) {
        break;
      }
      
      if (horario.matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}")) {
        barbeiro.adicionarDiaDisponivel(horario);
        System.out.println("Horário adicionado com sucesso!");
      } else {
        System.out.println("Formato inválido! Use: DD-MM-AAAA HH:MM");
      }
    }
    
    barbeiros.inserir(barbeiro);
    System.out.println("Barbeiro cadastrado com sucesso! ID: " + barbeiro.getId());
  }

    public void buscarBarbeiroPorId() {
    System.out.print("Digite o ID do barbeiro que deseja consultar: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    Barbeiro barbeiro = barbeiros.buscarPorId(id);
    if (barbeiro == null) {
      System.out.println("Barbeiro com ID " + id + " não encontrado.");
      return;
    }
    System.out.println("Informações do Barbeiro:");
    System.out.println(barbeiro);
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

  public void atualizarBarbeiro() {
    System.out.print("Digite o ID do barbeiro que deseja atualizar: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    Barbeiro barbeiro = barbeiros.buscarPorId(id);
    if (barbeiro == null) {
      System.out.println("barbeiro com ID " + id + " não encontrado.");
      return;
    }

    System.out.print("Digite o novo nome do barbeiro (atual: " + barbeiro.getNome() + "): ");
    String nome = scanner.nextLine();
    System.out.print("Digite o novo telefone do barbeiro (atual: " + barbeiro.getTelefone() + "): ");
    String telefone = scanner.nextLine();
    System.out.print("Digite o novo CPF do barbeiro (atual: " + barbeiro.getCpf() + "): ");
    String cpf = scanner.nextLine();

    barbeiro.setNome(nome);
    barbeiro.setTelefone(telefone);
    barbeiro.setCpf(cpf);

    boolean sucesso = barbeiros.alterar(barbeiro);
    if (!sucesso) {
      System.out.println("Falha ao atualizar o barbeiro.");
      return;
    }
    System.out.println("Barbeiro atualizado com sucesso!");
  }

  public void removerBarbeiro() {
    System.out.print("Digite o ID do barbeiro que deseja remover: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    boolean sucesso = barbeiros.excluir(id);
    if (!sucesso) {
      System.out.println("Barbeiro com ID " + id + " não encontrado.");
      return;
    }
    System.out.println("Barbeiro removido com sucesso!");
  }

  public void listarTodosBarbeiros() {
    List<Barbeiro> listaBarbeiros = barbeiros.buscarTodos();
    if (listaBarbeiros.isEmpty()) {
      System.out.println("Nenhum barbeiro cadastrado.");
      return;
    }

    System.out.println("Lista de todos os barbeiros:");
    for (Barbeiro barbeiro : listaBarbeiros) {
      System.out.println("\n" + barbeiro);
    }
  }
}