package modelo;

import java.util.ArrayList;
import java.util.List;

public class Barbeiro extends Entidade {
  private String nome;
  private String cpf;
  private String telefone;
  private List<Agendamento> agendamentos;
  private List<String> horarioDisponivelList;

  public Barbeiro() {
    super();
    nome = null;
    cpf = null;
    telefone = null;
    agendamentos = new ArrayList<>();
    horarioDisponivelList = new ArrayList<>();
  }

  public Barbeiro(Integer id, String nome, String cpf, String telefone) {
    super(id);
    this.nome = nome;
    this.cpf = cpf;
    this.telefone = telefone;
    agendamentos = new ArrayList<>();
    horarioDisponivelList = new ArrayList<>();
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public List<Agendamento> getAgendamentos() {
    return agendamentos;
  }

  public void setAgendamentos(List<Agendamento> agendamentos) {
    this.agendamentos = agendamentos;
  }

  public void adicionarAgendamento(Agendamento agendamento) {
    agendamentos.add(agendamento);
  }

  public List<String> getHorarioDisponivelList() {
    return horarioDisponivelList;
  }

  public void setHorarioDisponivelList(List<String> horarioDisponivelList) {
    this.horarioDisponivelList = horarioDisponivelList;
  }

  public void adicionarDiaDisponivel(String dia) {
    if (!horarioDisponivelList.contains(dia)) {
      horarioDisponivelList.add(dia);
    }
  }

  public void removerDiaDisponivel(String dia) {
    horarioDisponivelList.remove(dia);
  }

  public String toString() {
    StringBuilder agendamentosInfo = new StringBuilder();

    for (Agendamento agendamento : agendamentos) {
      agendamentosInfo.append("\n  - ID: ").append(agendamento.getId())
                      .append(", Cliente: ").append(agendamento.getCliente().getNome())
                      .append(", Data e Hora: ").append(agendamento.getDataHora())
                      .append(", Status: ").append(agendamento.getStatus());
    }

    return "Id do Barbeiro: " + getId() + 
            "\nNome: " + getNome() +
            "\nCPF: " + getCpf() +
            "\nTelefone: " + getTelefone() +
            "\nDias disponíveis: " + (horarioDisponivelList.isEmpty() ? "Nenhum" : String.join(", ", horarioDisponivelList)) +
            "\nNúmero de agendamentos: " + agendamentos.size() + 
            "\nAgendamentos: " + (agendamentos.isEmpty() ? "Nenhum" : agendamentosInfo.toString());


  }
}
