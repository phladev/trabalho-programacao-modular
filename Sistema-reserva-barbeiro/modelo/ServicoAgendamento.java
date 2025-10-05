package modelo;

public class ServicoAgendamento extends Entidade {
    private Servico servico;
    private String observacoes;

    public ServicoAgendamento() {
      super();
      this.servico = null;
      this.observacoes = "";
    }

    public ServicoAgendamento(Integer id, Servico servico) {
      super(id);
      this.servico = servico;
      this.observacoes = "";
    }

    public ServicoAgendamento(Integer id, Servico servico, String observacoes) {
      super(id);
      this.servico = servico;
      this.observacoes = observacoes;
    }

    public Servico getServico() {
      return servico;
    }

    public void setServico(Servico servico) {
      this.servico = servico;
    }

    public double getPreco() {
      return (servico != null) ? servico.getPreco() : 0.0;
    }

    public double getTempo() {
      return (servico != null) ? servico.getTempo() : 0.0;
    }

    public String getObservacoes() {
      return observacoes;
    }

    public void setObservacoes(String observacoes) {
      this.observacoes = observacoes;
    }

    @Override
    public String toString() {
      String servicoNome = (servico != null) ? servico.getNome() : "N/A";
      return "Serviço: " + servicoNome + 
              ", Preço: R$ " + String.format("%.2f", getPreco()) + 
              ", Tempo: " + getTempo() + " min" +
              (observacoes != null && !observacoes.isEmpty() ? ", Obs: " + observacoes : "");
    }
}
