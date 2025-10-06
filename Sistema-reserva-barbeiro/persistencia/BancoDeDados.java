package persistencia;

import modelo.Agendamento;
import modelo.Barbeiro;
import modelo.Cliente;
import modelo.Servico;

public class BancoDeDados {
  private Persistente<Cliente> clientes;
  private Persistente<Barbeiro> barbeiros;
  private Persistente<Servico> servicos;
  private Persistente<Agendamento> agendamentos;
  
  public BancoDeDados() {
    this.clientes = new Persistente<>();
    this.barbeiros = new Persistente<>();
    this.servicos = new Persistente<>();
    this.agendamentos = new Persistente<>();
    inicializarDados();
  }
  
  private void inicializarDados() {
    Barbeiro barbeiro1 = new Barbeiro(1, "João Silva", "12345678901", "11987654321");
    Barbeiro barbeiro2 = new Barbeiro(2, "Pedro Santos", "98765432109", "11876543210");
    barbeiros.inserir(barbeiro1);
    barbeiros.inserir(barbeiro2);
    
    Servico servico1 = new Servico(3, "Corte masculino", 25.0, 30.0);
    Servico servico2 = new Servico(4, "Barba", 15.0, 20.0);
    Servico servico3 = new Servico(5, "Corte + Barba", 35.0, 45.0);
    servicos.inserir(servico1);
    servicos.inserir(servico2);
    servicos.inserir(servico3);
    
    Cliente cliente1 = new Cliente(6, "Carlos Oliveira", "11122233344", "11999888777");
    Cliente cliente2 = new Cliente(7, "Roberto Lima", "55566677788", "11888777666");
    clientes.inserir(cliente1);
    clientes.inserir(cliente2);
  }
  
  public Persistente<Cliente> getClientes() {
    return clientes;
  }
  
  public void setClientes(Persistente<Cliente> clientes) {
    this.clientes = clientes;
  }
  
  public Persistente<Barbeiro> getBarbeiros() {
    return barbeiros;
  }
  
  public void setBarbeiros(Persistente<Barbeiro> barbeiros) {
    this.barbeiros = barbeiros;
  }
  
  public Persistente<Servico> getServicos() {
    return servicos;
  }
  
  public void setServicos(Persistente<Servico> servicos) {
    this.servicos = servicos;
  }
  
  public Persistente<Agendamento> getAgendamentos() {
    return agendamentos;
  }
  
  public void setAgendamentos(Persistente<Agendamento> agendamentos) {
    this.agendamentos = agendamentos;
  }
  
  public int getProximoId() {
    int maxId = 0;
    
    for (Cliente cliente : clientes.buscarTodos()) {
      if (cliente.getId() > maxId) {
        maxId = cliente.getId();
      }
    }
    
    for (Barbeiro barbeiro : barbeiros.buscarTodos()) {
      if (barbeiro.getId() > maxId) {
        maxId = barbeiro.getId();
      }
    }
    
    for (Servico servico : servicos.buscarTodos()) {
      if (servico.getId() > maxId) {
        maxId = servico.getId();
      }
    }
    
    for (Agendamento agendamento : agendamentos.buscarTodos()) {
      if (agendamento.getId() > maxId) {
        maxId = agendamento.getId();
      }
    }
    
    return maxId + 1;
  }
}