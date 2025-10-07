package modelo;

public enum StatusAgendamento {
AGENDADO("AGENDADO"),
  CONCLUIDO("CONCLUIDO"),
  CANCELADO("CANCELADO");

  private final String descricao;

  StatusAgendamento(String descricao) {
    this.descricao = descricao;
  }
  
  public String getDescricao() {
    return descricao;
  }
  
  @Override
  public String toString() {
    return descricao;
  }
}