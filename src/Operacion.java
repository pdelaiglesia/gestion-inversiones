import java.io.Serializable;
import java.time.LocalDate;

/**
 * Clase que guarda la informacion de cada operacion
 */
public class Operacion implements Serializable {
    private Activo activo;
    private LocalDate fecha;
    private tipoOperacion TipoOperacion;
    private int numero;
    private Double precio;
    private Double comision;

    /**
     * Constructor
     * @param activo
     * @param fecha
     * @param tipoOperacion
     * @param numero
     * @param precio
     * @param comision
     */
    public Operacion(Activo activo, LocalDate fecha, tipoOperacion tipoOperacion, int numero, Double precio, Double comision) {
        this.activo = activo;
        this.fecha = fecha;
        TipoOperacion = tipoOperacion;
        this.numero = numero;
        this.precio = precio;
        this.comision = comision;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public tipoOperacion getTipoOperacion() {
        return TipoOperacion;
    }

    public Double getPrecio() {
        return precio;
    }

    public Double getComision() {
        return comision;
    }

    @Override
    public String toString() {
        return "Operacion{" +
                "activo=" + activo +
                ", fecha=" + fecha +
                ", TipoOperacion=" + TipoOperacion +
                ", numero=" + numero +
                ", precio=" + precio +
                ", comision=" + comision +
                '}';
    }

    public Activo getActivo() {
        return activo;
    }

    public int getNumero() {
        return numero;
    }
}
