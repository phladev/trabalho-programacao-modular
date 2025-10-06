package visao;

import java.util.Scanner;
import persistencia.BancoDeDados;

public class UsaMenus {
    private Scanner scanner;
    private BancoDeDados bancoDeDados;
    private MenuCliente menuCliente;
    private MenuBarbeiro menuBarbeiro;
    private MenuServico menuServico;
    private MenuAgendamento menuAgendamento;
    
    public UsaMenus() {
        this.scanner = new Scanner(System.in);
        this.bancoDeDados = new BancoDeDados();
        this.menuCliente = new MenuCliente(bancoDeDados, scanner);
        this.menuBarbeiro = new MenuBarbeiro(bancoDeDados, scanner);
        this.menuServico = new MenuServico(bancoDeDados, scanner);
        this.menuAgendamento = new MenuAgendamento(bancoDeDados, scanner);
    }
    
    public void iniciarSistema() {
        int opcao = -1;
        
        do {
            System.out.println("\n======================================");
            Menus.exibeMenuPrincipal();
            System.out.print("Digite sua opção: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
                processarOpcaoMenuPrincipal(opcao);
            } else {
                System.out.println("Por favor, digite apenas números!");
                scanner.nextLine();
            }
        } while (opcao != 0);
        
        System.out.println("Obrigado por usar o sistema!");
        scanner.close();
    }
    
    private void processarOpcaoMenuPrincipal(int opcao) {
        switch (opcao) {
            case 0:
                System.out.println("Saindo do sistema...");
                break;
            case 1:
                menuClientes();
                break;
            case 2:
                menuServicos();
                break;
            case 3:
                menuAgendamentos();
                break;
            case 4:
                menuBarbeiro();
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }
    
    private void menuClientes() {
        int opcao = -1;
        do {
            System.out.println("\n-----------------------------");
            Menus.exibeMenuClientes();
            System.out.print("Digite sua opção: ");
            
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcao) {
                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    case 1:
                        menuCliente.cadastrarCliente();
                        break;
                    case 2:
                        menuCliente.atualizarCliente();
                        break;
                    case 3:
                        menuCliente.removerCliente();
                        break;
                    case 4:
                        menuCliente.buscarCLientePorId();
                        break;
                    case 5:
                        menuCliente.listarTodosClientes();
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } else {
                System.out.println("Por favor, digite apenas números!");
                scanner.nextLine();
            }
        } while (opcao != 0);
    }
    
    private void menuServicos() {
        int opcao = -1;
        do {
            System.out.println("\n-----------------------------");
            Menus.exibeMenuServicos();
            System.out.print("Digite sua opção: ");
            
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcao) {
                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    case 1:
                        menuServico.cadastrarServico();
                        break;
                    case 2:
                        menuServico.atualizarServico();
                        break;
                    case 3:
                        menuServico.removerServico();
                        break;
                    case 4:
                        menuServico.buscarServicoPorId();
                        break;
                    case 5:
                        menuServico.listarTodosServicos();
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } else {
                System.out.println("Por favor, digite apenas números!");
                scanner.nextLine();
            }
        } while (opcao != 0);
    }
    
    private void menuAgendamentos() {
        int opcao = -1;
        do {
            System.out.println("\n-----------------------------");
            Menus.exibeMenuAgendamentos();
            System.out.print("Digite sua opção: ");
            
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcao) {
                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    case 1:
                        menuAgendamento.agendarServico(menuCliente, menuBarbeiro, menuServico);
                        break;
                    case 2:
                        menuAgendamento.desagendarServico();
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } else {
                System.out.println("Por favor, digite apenas números!");
                scanner.nextLine();
            }
        } while (opcao != 0);
    }
    
    private void menuBarbeiro() {
        int opcao = -1;
        do {
            System.out.println("\n-----------------------------");
            Menus.exibeMenuBarbeiro();
            System.out.print("Digite sua opção: ");
            
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcao) {
                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    case 1:
                        menuBarbeiro.consultarInformacoesBarbeiros();
                        break;
                    case 2:
                        menuBarbeiro.consultarAgendamentosBarbeiro(bancoDeDados.getAgendamentos());
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } else {
                System.out.println("Por favor, digite apenas números!");
                scanner.nextLine();
            }
        } while (opcao != 0);
    }
}