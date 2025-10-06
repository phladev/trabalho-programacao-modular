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
        System.out.println(" 1 - Cadastrar cliente           ");
        System.out.println(" 2 - Atualizar cliente           ");
        System.out.println(" 3 - Remover cliente             ");
        System.out.println(" 4 - Consultar cliente por ID   ");
        System.out.println(" 5 - Listar todos os clientes    ");
        System.out.println("─────────────────────────────────");
    }

    public static void exibeMenuServicos(){
        System.out.println("MENU SERVIÇOS");
        System.out.println("─────────────────────────────────");
        System.out.println(" 0 - Voltar                     ");
        System.out.println(" 1 - Cadastrar serviço           ");
        System.out.println(" 2 - Atualizar serviço           ");
        System.out.println(" 3 - Remover serviço             ");
        System.out.println(" 4 - Consultar serviço por ID    ");
        System.out.println(" 5 - Listar todos os serviços    ");
        System.out.println("─────────────────────────────────");
    }

    public static void exibeMenuAgendamentos(){
        System.out.println("MENU AGENDAMENTOS");
        System.out.println("─────────────────────────────────");
        System.out.println(" 0 - Voltar                     ");
        System.out.println(" 1 - Agendar serviço            ");
        System.out.println(" 2 - Atualizar agendamento       ");
        System.out.println(" 3 - Cancelar agendamento        ");
        System.out.println(" 4 - Consultar agendamento por ID");
        System.out.println(" 5 - Listar todos os agendamentos");
        System.out.println("─────────────────────────────────");
    }

    public static void exibeMenuBarbeiro(){
        System.out.println("MENU BARBEIRO");
        System.out.println("─────────────────────────────────");
        System.out.println(" 0 - Voltar                     ");
        System.out.println(" 1 - Cadastrar Barbeiro          ");
        System.out.println(" 2 - Atualizar Barbeiro          ");
        System.out.println(" 3 - Remover Barbeiro            ");
        System.out.println(" 4 - Consultar Barbeiro por ID   ");
        System.out.println(" 5 - Listar todos os Barbeiros   ");
        System.out.println("─────────────────────────────────");
    }
}
