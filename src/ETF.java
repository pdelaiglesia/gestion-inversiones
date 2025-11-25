import java.io.Serializable;

/**
 * Clase hija de Activo
 */
public class ETF extends Activo implements Serializable {
    private String categoria;

    /**
     * Constructor
     * @param simbolo
     * @param nombre
     * @param negociable
     * @param categoria
     */
    public ETF(String simbolo, String nombre, boolean negociable, String categoria) {
        super(simbolo, nombre, negociable);
        this.categoria = categoria;
    }

}
