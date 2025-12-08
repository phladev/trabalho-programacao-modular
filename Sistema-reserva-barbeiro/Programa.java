import visao.JanelaPrincipal;
import visao.UsaMenus;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Programa {
    public static void main(String[] args) {
        iniciarComGUI();
    }
    
    private static void iniciarComGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new JanelaPrincipal();
        });
    }
    
    @SuppressWarnings("unused")
    private static void iniciarComConsole() {
        System.out.println("=== Sistema de Reserva de Barbeiro ===");
        System.out.println("Bem-vindo ao sistema!");
        System.out.println();
        
        UsaMenus sistema = new UsaMenus();
        sistema.iniciarSistema();
    }
}