package visao;
import modelo.Servicos;
//teste
public class UsaMenus{
    public Servicos s;
    public UsaMenus() {
        this.s = new Servicos();
    }
    public void teste(){
        System.out.println("nome: "+ s.getNome());
    }
}