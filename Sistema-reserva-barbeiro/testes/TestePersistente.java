package testes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistencia.Persistente;
import modelo.Entidade;

public class TestePersistente {
    class StubEntidade extends Entidade{
        private Integer id;
        private String nome;

        public StubEntidade(Integer id, String nome){
            this.id = id;
            this.nome = nome;
        }

        @Override
        public Integer getId(){
            return this.id;
        }
        public String getNome(){
            return this.nome;
        }
    }

    private Persistente<StubEntidade> banco;
        @BeforeEach
        public void preparaCenario(){
            banco = new Persistente<>();
        }

    @Test
    public void testeListaInicial(){
        Assertions.assertTrue(banco.isEmpty(), "banco deveria estar vazio");
        Assertions.assertEquals(0, banco.size(), "tamanho inicial deveria ser 0");
    }

    @Test
    public void testeInserir(){
        StubEntidade boneco = new StubEntidade(1, "Pedro");
        banco.inserir(boneco);
        StubEntidade resultado = banco.buscarPorId(1);
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1, resultado.getId());
        Assertions.assertEquals("Pedro", resultado.getNome());
    }

    @Test
    public void testeBuscarInexistente(){
        StubEntidade resultado = banco.buscarPorId(999);
        Assertions.assertNull(resultado);
    }

    @Test
    public void testeExcluir(){
        StubEntidade boneco = new StubEntidade(2, "Para Deletar");
        banco.inserir(boneco);
        boolean removido = banco.excluir(2);

        Assertions.assertTrue(removido);
        Assertions.assertNull(banco.buscarPorId(2));
    }

    @Test
    public void testeAlterar(){
        StubEntidade original = new StubEntidade(3, "Antigo");
        banco.inserir(original);

        StubEntidade atualizado = new StubEntidade(3, "Novo");
        boolean alterado = banco.alterar(atualizado);

        Assertions.assertTrue(alterado);

        StubEntidade resultado = banco.buscarPorId(3); 

        Assertions.assertEquals("Novo", resultado.getNome(), "O nome nao foi atualizado");
    }

    @Test
    public void testeExcluirInexistente(){
        boolean removido = banco.excluir(999);
        Assertions.assertFalse(removido, "Não deveria conseguir excluir ID inexistente");
    }

    @Test
    public void testeAlterarInexistente(){
        StubEntidade inexistente = new StubEntidade(999, "Não existe");
        boolean alterado = banco.alterar(inexistente);
        Assertions.assertFalse(alterado, "Não deveria conseguir alterar entidade inexistente");
    }

    @Test
    public void testeInserirNull(){
        int tamanhoInicial = banco.size();
        banco.inserir(null);
        Assertions.assertEquals(tamanhoInicial, banco.size(), "Não deveria inserir null");
    }

    @Test
    public void testeAlterarNull(){
        boolean resultado = banco.alterar(null);
        Assertions.assertFalse(resultado, "Não deveria alterar com entidade null");
    }

    @Test
    public void testeBuscarComIdNull(){
        StubEntidade resultado = banco.buscarPorId(null);
        Assertions.assertNull(resultado, "Buscar com ID null deveria retornar null");
    }

    @Test
    public void testeExcluirComIdNull(){
        boolean resultado = banco.excluir(null);
        Assertions.assertFalse(resultado, "Excluir com ID null deveria retornar false");
    }

    @Test
    public void testeBuscarTodos(){
        StubEntidade entidade1 = new StubEntidade(1, "Primeiro");
        StubEntidade entidade2 = new StubEntidade(2, "Segundo");
        StubEntidade entidade3 = new StubEntidade(3, "Terceiro");
        
        banco.inserir(entidade1);
        banco.inserir(entidade2);
        banco.inserir(entidade3);
        
        Assertions.assertEquals(3, banco.buscarTodos().size(), "Deveria ter 3 entidades");
        Assertions.assertTrue(banco.buscarTodos().contains(entidade1));
        Assertions.assertTrue(banco.buscarTodos().contains(entidade2));
        Assertions.assertTrue(banco.buscarTodos().contains(entidade3));
    }

    @Test
    public void testeToStringVazio(){
        String resultado = banco.toString();
        Assertions.assertEquals("Lista vazia", resultado);
    }

    @Test
    public void testeToStringComEntidades(){
        StubEntidade entidade1 = new StubEntidade(1, "Teste");
        banco.inserir(entidade1);
        String resultado = banco.toString();
        Assertions.assertFalse(resultado.contains("Lista vazia"));
    }

    @Test
    public void testeMultiplasInsercoes(){
        for (int i = 1; i <= 10; i++) {
            banco.inserir(new StubEntidade(i, "Entidade" + i));
        }
        Assertions.assertEquals(10, banco.size());
        Assertions.assertFalse(banco.isEmpty());
    }

    @Test
    public void testeExcluirTodos(){
        banco.inserir(new StubEntidade(1, "Um"));
        banco.inserir(new StubEntidade(2, "Dois"));
        banco.inserir(new StubEntidade(3, "Três"));
        
        banco.excluir(1);
        banco.excluir(2);
        banco.excluir(3);
        
        Assertions.assertTrue(banco.isEmpty());
        Assertions.assertEquals(0, banco.size());
    }
} 