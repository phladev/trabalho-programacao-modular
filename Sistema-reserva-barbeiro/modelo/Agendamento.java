package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Agendamento extends Entidade {
    private Cliente cliente;
    private Barbeiro barbeiro;
    private LocalDateTime dataHora;
    private List<ServicoAgendamento> servicos;
    private StatusAgendamento status;

    public Agendamento(){
        super();
        cliente = null;
        barbeiro = null;
        dataHora = null;
        servicos = new ArrayList<>();
        status = StatusAgendamento.AGENDADO;
    }

    public Agendamento(Integer id, Cliente cliente, Barbeiro barbeiro, LocalDateTime dataHora){
        super(id);
        this.cliente = cliente;
        this.barbeiro = barbeiro;
        this.dataHora = dataHora;
        servicos = new ArrayList<>();
        status = StatusAgendamento.AGENDADO;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Barbeiro getBarbeiro() {
        return barbeiro;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public List<ServicoAgendamento> getServicos() {
        return servicos;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setBarbeiro(Barbeiro barbeiro) {
        this.barbeiro = barbeiro;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setServicos(List<ServicoAgendamento> servicos) {
        this.servicos = servicos;
    }

    public void adicionarServico(ServicoAgendamento servico) {
        servicos.add(servico);
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public double calcularValorTotal() {
        return servicos.stream()
                      .mapToDouble(ServicoAgendamento::getPreco)
                      .sum();
    }

    @Override
    public String toString() {
        String clienteNome = (cliente != null) ? cliente.getNome() : "N/A";
        String barbeiroNome = (barbeiro != null) ? barbeiro.getNome() : "N/A";
        
        return "Id do agendamento: " + getId() +
            "\n Nome Cliente: " + clienteNome +
            "\n Nome do Barbeiro: " + barbeiroNome +
            "\n Data e hora: " + dataHora +
            "\n Status: " + status +
            "\n Valor total: R$ " + String.format("%.2f", calcularValorTotal());
    }
}