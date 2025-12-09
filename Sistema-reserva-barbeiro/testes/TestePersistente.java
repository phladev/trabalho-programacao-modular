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
} 