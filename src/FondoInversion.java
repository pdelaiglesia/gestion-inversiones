import java.io.Serializable;

/**
 * CLase hija de activo
 */
public class FondoInversion extends Activo implements Serializable {
    private String categoria;

    /**
     * Constructor
     * @param simbolo
     * @param nombre
     * @param negociable
     * @param categoria
     */
    public FondoInversion(String simbolo, String nombre, boolean negociable, String categoria) {
        super(simbolo, nombre, negociable);
        this.categoria = categoria;
    }
}
