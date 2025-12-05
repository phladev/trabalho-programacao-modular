package persistencia;

public class ExcecaoIDInexistente extends Exception {
  public ExcecaoIDInexistente(Integer id) {
    super("o id " + id + " nao foi encontrado.");
  }
}
