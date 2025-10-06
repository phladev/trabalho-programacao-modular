package visao;

public class Menus {
    public static void exibeMenuPrincipal() {
        System.out.println("SISTEMA DE RESERVA DE BARBEIRO");
        System.out.println("Escolha uma das opções a seguir:");
        System.out.println("─────────────────────────────────");
        System.out.println(" 0 - Sair                       ");
        System.out.println(" 1 - Menu clientes              ");
        System.out.println(" 2 - Menu serviços              ");
        System.out.println(" 3 - Menu agendamentos          ");
        System.out.println(" 4 - Menu barbeiro              ");
        System.out.println("─────────────────────────────────");
    }

    public static void exibeMenuClientes(){
        System.out.println("MENU CLIENTES");
        System.out.println("─────────────────────────────────");
        System.out.println(" 0 - Voltar                     ");
        System.out.println(" 1 - Consultar informações      ");
        System.out.println(" 2 - Consultar agendamentos     ");
        System.out.println("─────────────────────────────────");
    }

    public static void exibeMenuServicos(){
        System.out.println("MENU SERVIÇOS");
        System.out.println("─────────────────────────────────");
        System.out.println(" 0 - Voltar                     ");
        System.out.println(" 1 - Consultar serviços         ");
        System.out.println("─────────────────────────────────");
    }

    public static void exibeMenuAgendamentos(){
        System.out.println("MENU AGENDAMENTOS");
        System.out.println("─────────────────────────────────");
        System.out.println(" 0 - Voltar                     ");
        System.out.println(" 1 - Agendar serviço            ");
        System.out.println(" 2 - Cancelar agendamento       ");
        System.out.println("─────────────────────────────────");
    }

    public static void exibeMenuBarbeiro(){
        System.out.println("MENU BARBEIRO");
        System.out.println("─────────────────────────────────");
        System.out.println(" 0 - Voltar                     ");
        System.out.println(" 1 - Consultar informações      ");
        System.out.println(" 2 - Consultar agendamentos     ");
        System.out.println("─────────────────────────────────");
    }
}
