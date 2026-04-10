
public class Paquete<T extends Comparable<T>> implements Comparable<Paquete<T>> {
    private String codigo;
    private int peso;
    private T prioridad;
    private int valorizacion;
    private int zona;

    public Paquete(String codigo, int peso, T prioridad, int valorizacion, int zona) {
        this.codigo = codigo;
        this.peso = peso;
        this.prioridad = prioridad;
        this.valorizacion = valorizacion;
        this.zona = zona;
    }

    public int getPeso() { return peso; }
    public int getValorizacion() { return valorizacion; }
    public String getCodigo() { return codigo; }
    public int getZona() { return zona; }
    public T getPrioridad() { return prioridad; }

    @Override
    public int compareTo(Paquete<T> otro) {
        return otro.prioridad.compareTo(this.prioridad);
    }

    @Override
    public String toString() {
        return String.format("[%s | Peso: %d | Val: %d | Prio: %s]", codigo, peso, valorizacion, prioridad);
    }
}