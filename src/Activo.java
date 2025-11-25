import java.io.Serializable;

/**
 * Clase General de activo
 */
public class Activo implements Serializable {
    private String simbolo;
    private String nombre;
    private boolean negociable;

    /**
     * Constructor
     * @param simbolo
     * @param nombre
     * @param negociable
     */
    public Activo(String simbolo, String nombre, boolean negociable) {
        this.simbolo = simbolo;
        this.nombre = nombre;
        this.negociable = negociable;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isNegociable() {
        return negociable;
    }

    public void setNegociable(boolean negociable) {
        this.negociable = negociable;
    }

    @Override
    public String toString() {
        return "Activo{" +
                "simbolo='" + simbolo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", negociable=" + negociable +
                '}';
    }
}
