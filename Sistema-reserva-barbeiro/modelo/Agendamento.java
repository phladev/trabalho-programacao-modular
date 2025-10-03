import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Agendamento extends Entidade {
    public Cliente cliente;
    public Barbeiro barbeiro;
    public Date data;
    public List<Integer> agendamentos;
    public String status;

    public Agendamento(){
        super();
        cliente = null;
        data = null;
        agendamentos = new ArrayList<>();
    }

    public Agendamento(Integer id, Cliente cliente, Date data){
        super(id);
        this.cliente = cliente;
        this.data = data;
        agendamentos = new ArrayList<>();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Barbeiro getBarbeiro() {
        return barbeiro;
    }

    public Date getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public List<Integer> getAgendamentos() {
        return agendamentos;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setBarbeiro(Barbeiro barbeiro) {
        this.barbeiro = barbeiro;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setAgendamentos(List<Integer> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public void adicionarAgendamento(Integer agendamento) {
        agendamentos.add(agendamento);
    }

    public void setStatus(String status) {
      this.status = status;
    }

    @Override
    public String toString() {
        return "Id do agendamento: " + getId()+
            "\n Nome Cliente: " + getCliente().getNome()+
            "\n Nome do Barbeiro: " + getBarbeiro().getNome()+
            "\n Data dos serviços: " + getData();
            
    }
}