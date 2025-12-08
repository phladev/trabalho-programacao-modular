package modelo;

public class ServicoAgendamento extends Entidade {
    private Servico servico;
    private String observacoes;
    private double precoCongelado;
    private double tempoCongelado;

    public ServicoAgendamento() {
      super();
      this.servico = null;
      this.observacoes = "";
      this.precoCongelado = 0.0;
      this.tempoCongelado = 0.0;
    }

    public ServicoAgendamento(Integer id, Servico servico) {
      super(id);
      this.servico = servico;
      this.observacoes = "";
      this.precoCongelado = (servico != null) ? servico.getPreco() : 0.0;
      this.tempoCongelado = (servico != null) ? servico.getTempo() : 0.0;
    }

    public ServicoAgendamento(Integer id, Servico servico, String observacoes) {
      super(id);
      this.servico = servico;
      this.observacoes = observacoes;
      this.precoCongelado = (servico != null) ? servico.getPreco() : 0.0;
      this.tempoCongelado = (servico != null) ? servico.getTempo() : 0.0;
    }

    public Servico getServico() {
      return servico;
    }

    public void setServico(Servico servico) {
      this.servico = servico;
      if (servico != null) {
        this.precoCongelado = servico.getPreco();
        this.tempoCongelado = servico.getTempo();
      }
    }

    public double getPreco() {
      return precoCongelado;
    }

    public double getTempo() {
      return tempoCongelado;
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
