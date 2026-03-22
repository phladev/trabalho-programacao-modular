# Sistema de Reserva para Barbearia

Projeto acadêmico da disciplina de Programação Modular para gerenciamento de clientes, barbeiros, serviços e agendamentos em uma barbearia.

O sistema foi desenvolvido em Java com foco em organização modular, separação de responsabilidades e operações CRUD completas sobre as entidades principais.

## Integrantes

- Gabriel Reis Pereira Nascimento
- Ian Moura
- Matheus Henrique Barbosa Assis
- Pedro Henrique Lima de Assis

## Sumário

- [Visão Geral](#visão-geral)
- [Funcionalidades](#funcionalidades)
- [Arquitetura do Projeto](#arquitetura-do-projeto)
- [Estrutura de Diretórios](#estrutura-de-diretórios)
- [Tecnologias e Requisitos](#tecnologias-e-requisitos)
- [Como Compilar e Executar](#como-compilar-e-executar)
- [Como Executar os Testes](#como-executar-os-testes)
- [Dados Iniciais de Exemplo](#dados-iniciais-de-exemplo)
- [Pontos de Atenção](#pontos-de-atenção)

## Visão Geral

O sistema permite:

- Cadastrar, atualizar, buscar, listar e remover clientes.
- Cadastrar, atualizar, buscar, listar e remover barbeiros.
- Cadastrar, atualizar, buscar, listar e remover serviços.
- Criar, atualizar, listar e cancelar agendamentos.
- Associar múltiplos serviços a um agendamento.
- Calcular valor total e tempo total do agendamento.

Além do fluxo por menus no console, o projeto possui interface gráfica (Swing) para uso principal da aplicação.

## Funcionalidades

### 1. Gestão de Clientes

- Cadastro com nome, CPF e telefone.
- Atualização de dados.
- Remoção por ID.
- Consulta por ID e listagem completa.

### 2. Gestão de Barbeiros

- Cadastro com nome, CPF e telefone.
- Definição de horários disponíveis.
- Atualização e remoção.
- Consulta por ID e listagem completa.

### 3. Gestão de Serviços

- Cadastro de serviços com nome, preço e tempo estimado.
- Atualização e remoção.
- Consulta por ID e listagem completa.

### 4. Gestão de Agendamentos

- Criação de agendamento vinculando cliente, barbeiro, horário e serviços.
- Alteração de data/hora e status (`AGENDADO`, `CONCLUIDO`, `CANCELADO`).
- Cancelamento de agendamento.
- Adição e remoção de serviços dentro de um agendamento existente.
- Cálculo automático do valor total e tempo total.

## Arquitetura do Projeto

O projeto segue uma organização modular em três camadas principais:

### Modelo (`modelo`)

Representa as entidades de negócio e regras centrais:

- `Entidade`: classe base com identificador.
- `Cliente`, `Barbeiro`, `Servico`.
- `Agendamento`: agrega cliente, barbeiro, data/hora, serviços e status.
- `ServicoAgendamento`: snapshot de preço e tempo do serviço no momento do agendamento.
- `StatusAgendamento`: enumeração dos estados do agendamento.

### Persistência (`persistencia`)

Camada em memória para operações de armazenamento:

- `Persistente<T>`: estrutura genérica com operações CRUD.
- `BancoDeDados`: centraliza coleções, IDs incrementais e dados iniciais.
- `ExcecaoIDInexistente`: exceção customizada para identificadores inexistentes.

### Visão (`visao`)

Responsável pela interação com usuário:

- GUI em Swing: `JanelaPrincipal`, `ClienteGUI`, `BarbeiroGUI`, `ServicoGUI`, `AgendamentoGUI`.
- Menus de console: `Menus`, `UsaMenus` e classes `Menu*`.

### Ponto de Entrada

- `Programa.java`: inicia a aplicação pela interface gráfica (modo padrão).

## Estrutura de Diretórios

```text
.
├── README.md
├── lib/
│   └── junit-platform-console-standalone-1.13.0-M3.jar
└── Sistema-reserva-barbeiro/
	├── Programa.java
	├── modelo/
	├── persistencia/
	├── testes/
	└── visao/
```

## Tecnologias e Requisitos

- Java JDK 17+ (recomendado)
- Swing (já incluído no JDK)
- JUnit 5 (jar já disponível em `lib`)
- Sistema operacional: Linux, macOS ou Windows com JDK configurado

Verifique a instalação:

```bash
java -version
javac -version
```

## Como Compilar e Executar

### 1. Compilar a aplicação

Na raiz do projeto:

```bash
mkdir -p out
javac -d out $(find Sistema-reserva-barbeiro -name "*.java" ! -path "*/testes/*")
```

### 2. Executar a aplicação (GUI)

```bash
java -cp out Programa
```

### 3. Executar no modo console (opcional)

O método para console existe em `Programa`, mas o fluxo padrão atual inicia a interface gráfica. Para usar somente console, ajuste temporariamente o `main` para chamar `iniciarComConsole()` e recompile.

```bash
java -jar lib/junit-platform-console-standalone-1.13.0-M3.jar -cp out --scan-classpath
```

## Dados Iniciais de Exemplo

Ao iniciar, o `BancoDeDados` cria alguns registros para facilitar testes manuais:

- 2 barbeiros com horários disponíveis.
- 3 serviços cadastrados.
- 2 clientes cadastrados.

Isso permite simular agendamentos imediatamente após abrir o sistema.

## Pontos de Atenção

- A persistência é em memória: os dados não são salvos após encerrar o programa.
- Há coexistência de interface gráfica e menus de console no mesmo projeto.
- O gerenciamento de IDs é manual/incremental pela camada `BancoDeDados`.

## Possíveis Evoluções

- Persistência em arquivo ou banco relacional.
- Validações mais robustas de CPF, telefone e conflitos de agenda.
- Relatórios (faturamento diário, serviços mais solicitados, taxa de cancelamento).
- Melhorias de UX na interface gráfica.
