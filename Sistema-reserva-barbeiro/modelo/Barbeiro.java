import java.util.ArrayList;
import java.util.List;

public class Barbeiro extends Entidade {
  private String nome;
  private Integer cpf;
  private Integer telefone;
  private List<Integer> agendamentos;

  public Barbeiro() {
    super();
    nome = null;
    cpf = null;
    telefone = null;
    agendamentos = new ArrayList<>();
  }

  public Barbeiro(Integer id, String nome, Integer cpf, Integer telefone) {
    super(id);
    this.nome = nome;
    this.cpf = cpf;
    this.telefone = telefone;
    agendamentos = new ArrayList<>();
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Integer getCpf() {
    return cpf;
  }

  public void setCpf(Integer cpf) {
    this.cpf = cpf;
  }

  public Integer getTelefone() {
    return telefone;
  }

  public void setTelefone(Integer telefone) {
    this.telefone = telefone;
  }

  public List<Integer> getAgendamentos() {
    return agendamentos;
  }

  public void setAgendamentos(List<Integer> agendamentos) {
    this.agendamentos = agendamentos;
  }

  public void adicionarAgendamento(Integer agendamento) {
    agendamentos.add(agendamento);
  }

  public String toString() {
    return "Id do Barbeiro: " + getId() + 
            "\nNome: " + getNome() +
            "\nCPF: " + getCpf() +
            "\nTelefone: " + getTelefone();
  }
}
