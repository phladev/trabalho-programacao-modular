import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Agendamento extends Entidade {
    public Cliente cliente;
    public Date data;
    public List<Integer> agendamentos;

    public Agendamento(){
        cliente = null;
        data = null;
        agendamentos = new ArrayList<>();
    }

    public Agendamento(Cliente cliente, Date d){
        
    }


}
