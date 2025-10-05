package visao;
import modelo.Servico;
//teste
public class UsaMenus{
    public Servico s;
    public UsaMenus() {
        this.s = new Servico();
    }
    public void teste(){
        System.out.println("nome: "+ s.getNome());
    }
}