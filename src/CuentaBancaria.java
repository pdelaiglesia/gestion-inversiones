import java.io.Serializable;

/**
 * Clase que guarda la informacion de la cuenta bancaria
 */
public class CuentaBancaria implements Serializable {
    private String entidad;
    private String IBAN;
    private Double saldo;

    /**
     * Constructor
     * @param entidad
     * @param IBAN
     * @param saldo
     */
    public CuentaBancaria(String entidad, String IBAN, Double saldo) {
        this.entidad = entidad;
        this.IBAN = IBAN;
        this.saldo = saldo;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "entidad='" + entidad + '\'' +
                ", IBAN='" + IBAN + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
