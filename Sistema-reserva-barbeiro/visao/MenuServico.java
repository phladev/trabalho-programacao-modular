package visao;

import modelo.Servico;
import persistencia.BancoDeDados;
import persistencia.Persistente;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuServico {
  private Persistente<Servico> servicos;
  private BancoDeDados bancoDeDados;
  private Scanner scanner;
  
  public MenuServico(BancoDeDados bancoDeDados, Scanner scanner) {
    this.servicos = bancoDeDados.getServicos();
    this.bancoDeDados = bancoDeDados;
    this.scanner = scanner;
  }
  
  public List<Servico> selecionarServico() {
    List<Servico> servicosSelecionadosList = new ArrayList<>();

    if (servicos.isEmpty()) {
      System.out.println("Nenhum serviço cadastrado.");
      return null;
    }
    
    System.out.println("\nServiços disponíveis:");
    List<Servico> listaServicos = servicos.buscarTodos();

    int escolhaServico = -1;

    boolean selecionouPeloMenosUm = false;
    
    do {
      for (int i = 0; i < listaServicos.size(); i++) {
      Servico s = listaServicos.get(i);
      System.out.println((i + 1) + " - " + s.getNome() + " (R$ " + s.getPreco() + ")");
      }
      System.out.print("Escolha o serviço para adicionar (0 para finalizar seleção): ");

      if (!scanner.hasNextInt()) {
      System.out.println("Por favor, digite apenas números!");
      scanner.nextLine();
      return null;
      }

      escolhaServico = scanner.nextInt();
      scanner.nextLine();

      if (escolhaServico == 0) {
        if (!selecionouPeloMenosUm) {
          System.out.println("Selecione pelo menos um serviço antes de finalizar.");
          continue;
        } else {
          break;
        }
      }

      if (escolhaServico < 1 || escolhaServico > listaServicos.size()) {
      System.out.println("Serviço inválido!");
      continue;
      }

      Servico servicoEscolhido = listaServicos.get(escolhaServico - 1);
      servicosSelecionadosList.add(servicoEscolhido);
      selecionouPeloMenosUm = true;
      System.out.println("Serviço " + servicoEscolhido.getNome() + " adicionado.");

    } while (true);
    
    return servicosSelecionadosList;
  }

  public void cadastrarServico() {
    System.out.print("Nome do serviço: ");
    String nome = scanner.nextLine();
    
    System.out.print("Tempo do serviço: ");
    double tempo = scanner.nextDouble();
    
    System.out.print("Preço do serviço: ");
    if (!scanner.hasNextDouble()) {
      System.out.println("Por favor, digite um valor numérico para o preço!");
      scanner.nextLine();
      return;
    }
    double preco = scanner.nextDouble();
    scanner.nextLine();

    Integer id = bancoDeDados.getProximoId();
    
    Servico novoServico = new Servico(id, nome, tempo, preco);
    servicos.inserir(novoServico);
    System.out.println("Serviço cadastrado com sucesso!");
  }

  public void atualizarServico() {
    System.out.print("ID do serviço a ser atualizado: ");
    if (!scanner.hasNextInt()) {
      System.out.println("Por favor, digite apenas números!");
      scanner.nextLine();
      return;
    }
    Integer id = scanner.nextInt();
    scanner.nextLine();
    
    Servico servico = servicos.buscarPorId(id);
    if (servico == null) {
      System.out.println("Serviço não encontrado!");
      return;
    }
    
    System.out.print("Novo nome do serviço (atual: " + servico.getNome() + "): ");
    String nome = scanner.nextLine();
    if (!nome.isEmpty()) {
      servico.setNome(nome);
    }
    
    System.out.print("Novo tempo do serviço (atual: " + servico.getTempo() + "): ");
    String tempoInput = scanner.nextLine();
    if (!tempoInput.isEmpty()) {
      double tempo = Double.parseDouble(tempoInput);
      servico.setTempo(tempo);
    }
    
    System.out.print("Novo preço do serviço (atual: R$ " + servico.getPreco() + "): ");
    String precoInput = scanner.nextLine();
    if (!precoInput.isEmpty()) {
      double preco = Double.parseDouble(precoInput);
      servico.setPreco(preco);
    }
    
    servicos.alterar(servico);
    System.out.println("Serviço atualizado com sucesso!");
  }

  public void removerServico() {
    System.out.print("ID do serviço a ser removido: ");
    if (!scanner.hasNextInt()) {
      System.out.println("Por favor, digite apenas números!");
      scanner.nextLine();
      return;
    }
    Integer id = scanner.nextInt();
    scanner.nextLine();
    
    Servico servico = servicos.buscarPorId(id);
    if (servico == null) {
      System.out.println("Serviço não encontrado!");
      return;
    }
    
    boolean successo = servicos.excluir(id);
    if (!successo) {
      System.out.println("Falha ao remover o serviço.");
      return;
    }
    System.out.println("Serviço removido com sucesso!");
  }

  public void buscarServicoPorId() {
    System.out.print("ID do serviço a ser consultado: ");
    if (!scanner.hasNextInt()) {
      System.out.println("Por favor, digite apenas números!");
      scanner.nextLine();
      return;
    }
    Integer id = scanner.nextInt();
    scanner.nextLine();
    
    Servico servico = servicos.buscarPorId(id);
    if (servico == null) {
      System.out.println("Serviço não encontrado!");
      return;
    }
    
    System.out.println("Serviço encontrado:");
    System.out.println(servico);
  }

  public void listarTodosServicos() {
    List<Servico> listaServicos = servicos.buscarTodos();
    if (listaServicos.isEmpty()) {
      System.out.println("Nenhum serviço cadastrado.");
      return;
    }
    
    System.out.println("\nLista de todos os serviços:");
    for (Servico servico : listaServicos) {
      System.out.println("\n" + servico);
    }
  }

}