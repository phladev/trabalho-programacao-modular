package modelo;

public class Servico extends Entidade{
    private String nome;
    private double preco;
    private double tempo;
    public Servico () {
        super();
        nome = null;
        preco = 0;
        tempo = 0;
    }

    public Servico (Integer id, String nome, double preco, double tempo) {
        super(id);
        this.nome = nome;
        this.preco = preco;
        this.tempo = tempo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return this.nome;
    }

    public double getTempo() {
        return this.tempo;
    }

    public double getPreco() {
        return this.preco;
    }

    public String toString(){
        return ("id do servico: "+ getId() + "\n" +
        "nome: " + nome + "\n" + 
        "preco: " + preco + "\n" + 
        "tempo estimado: " + tempo + "\n");
    }
}
