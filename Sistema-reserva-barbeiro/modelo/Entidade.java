package modelo;

public abstract class Entidade {
    private Integer id;

    public Entidade() {
        id = null;
    }

    public Entidade(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toString() {
        return "ID: " + id;
    }
}