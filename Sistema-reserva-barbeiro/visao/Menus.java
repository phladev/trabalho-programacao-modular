package visao;

public class Menus{
    public static void exibeMenuPrincipal() {
        System.out.println("Escolha uma das opções a seguir:\n");
        System.out.println("0 - Sair\n");
        System.out.println("1 - Menu clientes\n");
        System.out.println("2 - Menu serviços\n");
        System.out.println("3 - Menu agendamentos\n");
        System.out.println("4 - Menu barbeiro\n");
    }

    public static void exibeMenuClientes(){
        System.out.println("0 - voltar");
        System.out.println("1 - Consultar informações\n");
        System.out.println("2 - Consultar agendamentos\n");
    }

    public static void exibeMenuServicos(){
        System.out.println("0 - voltar");
        System.out.println("1 - Consultar serviços\n");
    }

    public static void exibeMenuAgendamentos(){
        System.out.println("0 - voltar");
        System.out.println("1 - Agendar serviço\n");
        System.out.println("2 - Desagendar serviço\n");
    }

    public static void exibeMenuBarbeiro(){
        System.out.println("0 - voltar");
        System.out.println("1 - Consultar informações\n");
        System.out.println("2 - Consultar agendamentos\n");
    }
}
