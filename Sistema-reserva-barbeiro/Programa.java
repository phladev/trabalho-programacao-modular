import visao.UsaMenus;

public class Programa {
    public static void main(String[] args) {
        System.out.println("=== Sistema de Reserva de Barbeiro ===");
        System.out.println("Bem-vindo ao sistema!");
        System.out.println();
        
        UsaMenus sistema = new UsaMenus();
        sistema.iniciarSistema();
    }
}