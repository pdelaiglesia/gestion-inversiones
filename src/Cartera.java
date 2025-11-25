import javax.swing.text.ViewFactory;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase cartera con informacion del usuario y funciones de compra, venta, dividendos y rentabilidad
 */
public class  Cartera implements Serializable {
    private String nombre;
    private ArrayList<Operacion> operaciones;
    private CuentaBancaria cuentaAsociada;
    private HashMap<Activo,Integer> activosCartera;

    /**
     * Constructor
     * @param nombre
     * @param cuentaAsociada
     */
    public Cartera(String nombre, CuentaBancaria cuentaAsociada) {
        this.nombre = nombre;
        this.cuentaAsociada = cuentaAsociada;
        operaciones=new ArrayList<>();
        activosCartera=new HashMap<>();
    }

    public Cartera(Object readObject) {
    }

    /**
     * Funcion para comprar Activo
     * @param activo
     * @param fecha
     * @param numero
     * @param precio
     * @param comision
     * @return
     */
    public boolean comprar(Activo activo, LocalDate fecha, int numero, Double precio, Double comision){
        //Si el activo es negociable y el usuario tiene suficiente dinero en la cuenta
        if(precio<= cuentaAsociada.getSaldo() && activo.isNegociable()==true){
            boolean yaPropietario=false;
            //Restamos el dinero de la cuenta
            cuentaAsociada.setSaldo(cuentaAsociada.getSaldo()-precio-comision);
            //Comprobamos si ya tenemos este activo
            for(int i=0;i<activosCartera.size();i++){
                if(activosCartera.get(activo)==null){
                    yaPropietario=true;
                }
            }
            //Si no lo tenemos lo guardamos en los del usuario
            if(yaPropietario==false){
                activosCartera.put(activo,numero);
            }
            //Si ya lo tenemos le añadimos el numero
            else{
                int nAntes=activosCartera.get(activo);
                activosCartera.put(activo,nAntes+numero);
            }
            //Creamos la operacion de compra
            operaciones.add(new Operacion(activo,fecha, tipoOperacion.COMPRA, numero, precio, comision));
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Funcion para la venta de activos
     * @param activo
     * @param fecha
     * @param numero
     * @param precio
     * @param comision
     * @return
     */
    public boolean vender(Activo activo, LocalDate fecha, int numero, Double precio, Double comision){
        //Si tenemos el suficiente numero de activos
        if(numero>=activosCartera.get(activo)){
            //Restamos el saldo
            cuentaAsociada.setSaldo(cuentaAsociada.getSaldo()+(precio-comision));
            //Creamos una nueva operacion
            operaciones.add(new Operacion(activo,fecha, tipoOperacion.VENTA, numero, precio, comision));
            //Si el numero de activos es el mismo que vendemos, eliminamos el activo de la cartera
            if(numero==activosCartera.get(activo)){
                activosCartera.remove(activo);
            }
            //Sino restamos solo la cantidad que vendemos
            else{
                activosCartera.put(activo,activosCartera.get(activo)-numero);
            }
            return true;
        }
        else{
            return false;
        }
    }

    public HashMap<Activo, Integer> getActivosCartera() {
        return activosCartera;
    }

    public ArrayList<Operacion> getOperaciones() {
        return operaciones;
    }

    public CuentaBancaria getCuentaAsociada() {
        return cuentaAsociada;
    }


    @Override
    public String toString() {
        String a="Operaciones \n";
        for(int i=0;i<operaciones.size();i++){
            a=a+operaciones.get(i).toString();
        }
        return a;
    }

    /**
     * Funcion que cobra dividendos
     * @param activo
     * @param fecha
     * @param valor
     * @param comision
     */
    public void cobrarDividendos(Activo activo, LocalDate fecha, Double valor, Double comision){
        //Creamos operacion
        operaciones.add(new Operacion(activo,fecha,tipoOperacion.DIVIDENDOS,0,valor,comision));
        //Sumamos el saldo
        cuentaAsociada.setSaldo(cuentaAsociada.getSaldo()+valor-comision);
    }


    /**
     * Funcion para calcular la rentabilidad hasta una fecha
     * @param fecha
     * @return
     * @throws IOException
     */
    public Double rentablidad(LocalDate fecha) throws IOException {
        Double vFinal=0.00, vCompras=0.00, vVentas=0.00,vDividendos=0.00;
        //Para cada activo que tenemos calculamos el precio que vale y lo multiplicamos por cuantos tenemos
        for(Activo a : activosCartera.keySet()){
            vFinal= vFinal+Precio.buscaPrecio(a.getSimbolo(),fecha)*activosCartera.get(a);
        }
        //Para cada operacion comprobamos si es antes de la fecha y la clasificamos
        for(int i=0;i<operaciones.size();i++){
            if(operaciones.get(i).getFecha().isBefore(fecha)){
                if(operaciones.get(i).getTipoOperacion()==tipoOperacion.COMPRA){
                    vCompras= vCompras + operaciones.get(i).getPrecio();
                }
                else if(operaciones.get(i).getTipoOperacion()==tipoOperacion.VENTA){
                    vVentas=vVentas+operaciones.get(i).getPrecio();
                }
                else if(operaciones.get(i).getTipoOperacion()==tipoOperacion.DIVIDENDOS){
                    vDividendos=vDividendos+operaciones.get(i).getPrecio();
                }
            }
        }
        //Devolvemos el resultado
        return (((vFinal+vVentas+vDividendos)/vCompras)-1)*100;
    }

    public String getNombre() {
        return nombre;
    }
}
