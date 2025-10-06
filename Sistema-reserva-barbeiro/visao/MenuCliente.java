package visao;

import modelo.Cliente;
import persistencia.BancoDeDados;
import persistencia.Persistente;
import java.util.Scanner;

public class MenuCliente {
  private Persistente<Cliente> clientes;
  private BancoDeDados bancoDeDados;
  private Scanner scanner;
  
  public MenuCliente(BancoDeDados bancoDeDados, Scanner scanner) {
    this.clientes = bancoDeDados.getClientes();
    this.bancoDeDados = bancoDeDados;
    this.scanner = scanner;
  }
  
  public void cadastrarCliente() {
    System.out.print("Digite o nome do cliente: ");
    String nome = scanner.nextLine();
    System.out.print("Digite o telefone do cliente [(XX) XXXXX-XXXX]: ");
    String telefone = scanner.nextLine();
    System.out.print("Digite o CPF do cliente [XXX.XXX.XXX-XX]: ");
    String cpf = scanner.nextLine();

    Integer id = bancoDeDados.getProximoId();

    Cliente cliente = new Cliente(id, nome, cpf, telefone);
    clientes.inserir(cliente);
    System.out.println("Cliente cadastrado com sucesso! ID: " + cliente.getId());
  }

  public void atualizarCliente() {
    System.out.print("Digite o ID do cliente que deseja atualizar: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    Cliente cliente = clientes.buscarPorId(id);
    if (cliente == null) {
      System.out.println("Cliente com ID " + id + " não encontrado.");
      return;
    }

    System.out.print("Digite o novo nome do cliente (atual: " + cliente.getNome() + "): ");
    String nome = scanner.nextLine();
    System.out.print("Digite o novo telefone do cliente (atual: " + cliente.getTelefone() + "): ");
    String telefone = scanner.nextLine();
    System.out.print("Digite o novo CPF do cliente (atual: " + cliente.getCpf() + "): ");
    String cpf = scanner.nextLine();

    cliente.setNome(nome);
    cliente.setTelefone(telefone);
    cliente.setCpf(cpf);

    boolean sucesso = clientes.alterar(cliente);
    if (!sucesso) {
      System.out.println("Falha ao atualizar o cliente.");
      return;
    }
    System.out.println("Cliente atualizado com sucesso!");
  }

  public void removerCliente() {
    System.out.print("Digite o ID do cliente que deseja remover: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    boolean sucesso = clientes.excluir(id);
    if (!sucesso) {
      System.out.println("Cliente com ID " + id + " não encontrado.");
      return;
    }
    System.out.println("Cliente removido com sucesso!");
  }

  public void buscarCLientePorId() {
    System.out.print("Digite o ID do cliente que deseja consultar: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    Cliente cliente = clientes.buscarPorId(id);
    if (cliente == null) {
      System.out.println("Cliente com ID " + id + " não encontrado.");
      return;
    }
    System.out.println("Informações do Cliente:");
    System.out.println(cliente);
  }

  public void listarTodosClientes() {
    System.out.println("Lista de todos os clientes:");
    for (Cliente cliente : clientes.buscarTodos()) {
      System.out.println(cliente);
    }
  }

}