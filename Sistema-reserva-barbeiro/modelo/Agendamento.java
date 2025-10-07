package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public String getDataHora() {
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
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

    public double calcularTempoTotal() {
        return servicos.stream()
                      .mapToDouble(ServicoAgendamento::getTempo)
                      .sum();
    }

    public double getValorTotal() {
        return calcularValorTotal();
    }

    public double getTempoTotal() {
        return calcularTempoTotal();
    }

    @Override
    public String toString() {
        String clienteNome = (cliente != null) ? cliente.getNome() : "N/A";
        String barbeiroNome = (barbeiro != null) ? barbeiro.getNome() : "N/A";
        
        StringBuilder servicesInfo = new StringBuilder();
        if (!servicos.isEmpty()) {
            servicesInfo.append("\n Serviços:");
            for (ServicoAgendamento sa : servicos) {
                servicesInfo.append("\n   - ").append(sa.toString());
            }
        }
        
        return "Id do agendamento: " + getId() +
            "\n Nome Cliente: " + clienteNome +
            "\n Nome do Barbeiro: " + barbeiroNome +
            "\n Data e hora: " + getDataHora() +
            "\n Status: " + getStatus() +
            servicesInfo.toString() +
            "\n Valor total: R$ " + String.format("%.2f", calcularValorTotal()) +
            "\n Tempo total: " + String.format("%.0f", calcularTempoTotal()) + " minutos";
    }
}