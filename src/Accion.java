import java.io.Serializable;

/**
 * Clase hija de activo
 */
public class Accion extends Activo implements Serializable {
    private String mercado;

    /**
     * Constructor
     * @param simbolo
     * @param nombre
     * @param negociable
     * @param mercado
     */
    public Accion(String simbolo, String nombre, boolean negociable, String mercado){
        super(simbolo,nombre,negociable);
        this.mercado=mercado;
    }

    public String getMercado() {
        return mercado;
    }
}
