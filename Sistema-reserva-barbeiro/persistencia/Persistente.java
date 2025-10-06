package persistencia;

import modelo.Entidade;
import java.util.ArrayList;
import java.util.List;

public class Persistente<T extends Entidade> {
  private List<T> entidadesList;
  
  public Persistente() {
    this.entidadesList = new ArrayList<>();
  }
  
  public void inserir(T entidade) {
    if (entidade != null) {
      entidadesList.add(entidade);
    }
  }
  
  public boolean alterar(T entidadeAtualizada) {
    if (entidadeAtualizada == null || entidadeAtualizada.getId() == null) {
      return false;
    }
    
    for (int i = 0; i < entidadesList.size(); i++) {
      if (entidadesList.get(i).getId().equals(entidadeAtualizada.getId())) {
        entidadesList.set(i, entidadeAtualizada);
        return true;
      }
    }
    return false;
  }
  
  public boolean excluir(Integer id) {
    if (id == null || buscarPorId(id) == null) {
      return false;
    }
    
    return entidadesList.remove(buscarPorId(id));
  }
  
  public T buscarPorId(Integer id) {
    if (id == null) {
      return null;
    }
    
    for (T entidade : entidadesList) {
      if (entidade.getId().equals(id)) {
        return entidade;
      }
    }
    return null;
  }
  
  public List<T> buscarTodos() {
    return entidadesList;
  }
  
  public String toString() {
    if (entidadesList.isEmpty()) {
      return "Lista vazia";
    }
    
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < entidadesList.size(); i++) {
      sb.append(entidadesList.get(i).toString());
      if (i < entidadesList.size() - 1) {
        sb.append("\n").append("------------------------------").append("\n");
      }
    }
    return sb.toString();
  }
  
  public boolean isEmpty() {
    return entidadesList.isEmpty();
  }
  
  public int size() {
    return entidadesList.size();
  }
}