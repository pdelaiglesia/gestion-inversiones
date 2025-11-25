import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase principal, con la informacion del usuario y metodos para las distintas funciones
 */
public class GestionInversiones {
    private Cartera cartera;
    private ArrayList<Activo> activos;

    /**
     * Constructor
     * @param nombre
     * @param entidad
     * @param IBAN
     * @param saldo
     */
    public GestionInversiones(String nombre,String entidad,String IBAN, Double saldo) {
        cartera=new Cartera(nombre,new CuentaBancaria(entidad,IBAN,saldo));
        activos=new ArrayList<>();
    }

    public GestionInversiones(Cartera cartera,ArrayList<Activo> activos) {
        this.cartera = cartera;
        this.activos=new ArrayList<>(activos);
    }

    /**
     * Enseña el menu y devuelve el entero con la eleccion
     * @return
     */
    public int menu(){
        Scanner teclado=new Scanner(System.in);
        System.out.println("MENU");
        System.out.println("1.Registrar un Activo");
        System.out.println("2.Ingresar dinero en cuenta");
        System.out.println("3.Retirar dinero de la cuenta");
        System.out.println("4.Obtener informaciormacion de la cuenta y su saldo");
        System.out.println("5.Realizar la compra de un Activo");
        System.out.println("6.Realizar una venta");
        System.out.println("7.Cobrar dividendos");
        System.out.println("8.Informe de las operaciones realizadas");
        System.out.println("9.Informe de operaciones entre fechas");
        System.out.println("10.Calcular rentabilidad");
        System.out.println("11.Calcular rentabilidad hasta una fecha");
        System.out.println("12.Finalizar");
        System.out.println("13.Informe de operaciones entre fechas");
        System.out.println("14.Informe de rentabilidad");

        return teclado.nextInt();
    }

    /**
     * Funcion para ingresar saldo en la cuenta
     * @param cantidad
     */
    public void ingresar(Double cantidad){
        cartera.getCuentaAsociada().setSaldo(cartera.getCuentaAsociada().getSaldo()+cantidad);
    }

    /**
     * Funcion para retirar saldo de la cuenta
     * @param cantidad
     */
    public void retirar(Double cantidad){
        if(cantidad<=cartera.getCuentaAsociada().getSaldo()){
            cartera.getCuentaAsociada().setSaldo(cartera.getCuentaAsociada().getSaldo()-cantidad);
            System.out.println("Saldo retirado con exito");
        }
        else{
            System.out.println("Saldo insuficiente");
        }
    }

    /**
     * Funcion que dado un simbolo devuelve el activo de dicho simbolo
     * @param simbolo
     * @return
     */
    public Activo buscarActivo(String simbolo){
        for(Activo a:activos){
            if(a.getSimbolo().equals(simbolo)){
                return a;
            }
        }
        return null;
    }

    /**
     * Devuelve Cartera
     * @return
     */
    public Cartera getCartera() {
        return cartera;
    }

    /**
     * Informe de operaciones entre fechas
     * @param fecha1
     * @param fecha2
     */
    public void informeFechas(LocalDate fecha1,LocalDate fecha2){
        for(int i=0;i<cartera.getOperaciones().size();i++){
            if(cartera.getOperaciones().get(i).getFecha().isAfter(fecha1)&&cartera.getOperaciones().get(i).getFecha().isBefore(fecha2)){
                cartera.getOperaciones().get(i).toString();
            }
        }
    }

    /**
     * Funcion que genera un informe de las operaciones de la cartera entre fechas y las guarda en un txt
     * @param cartera
     * @param fInicio
     * @param fFin
     * @param nombreArchivo
     */
    public void generarInformeOperaciones(Cartera cartera,LocalDate fInicio,LocalDate fFin,String nombreArchivo){
        boolean hayOperaciones=false;
        try(FileWriter escribir= new FileWriter(nombreArchivo)){
            String guiones="----------------------------------------------------------------";
            if(cartera.getOperaciones().size()>0){
                escribir.write("Informe de operaciones de la cartera principal: \n");
                escribir.write("Fecha inicial: "+fInicio+"\nFecha final: "+fFin+"\n");
                escribir.write(guiones+"\n");
            }
            else {
                System.out.println("Ninguna operacion");
            }
            for(Activo a: activos){
                Double totalC=0.00,totalV=0.00,totalD=0.00,totalCo=0.00;
                ArrayList<Operacion> oa=new ArrayList<>();
                for(Operacion o: cartera.getOperaciones()){
                    if(o.getActivo().getSimbolo().equals(a.getSimbolo())&&o.getFecha().isBefore(fFin)&&o.getFecha().isAfter(fInicio)){
                        System.out.println(o.toString());
                        oa.add(o);
                    }
                }
                if(oa.size()>0){
                    escribir.write("|  "+a.getSimbolo()+" - "+a.getNombre()+"    |\n");
                    escribir.write("| Fecha     | Op | Cantidad     |  Precio | Comisión |    Total |\n");
                    for(Operacion o: oa){
                        String op;
                        if(o.getTipoOperacion()==tipoOperacion.COMPRA){
                            op="C";
                            totalC=totalC+o.getPrecio()*o.getNumero();

                        }
                        else if (o.getTipoOperacion()==tipoOperacion.VENTA){
                            op="V";
                            totalV=totalV+o.getPrecio()*o.getNumero();
                        }
                        else {
                            op="D";
                            totalD+=o.getPrecio();
                        }
                        totalCo+=o.getComision();
                        escribir.write("| "+o.getFecha()+"|  "+op+" |  "+o.getNumero()+" |   "+o.getPrecio()+" |  "+o.getComision()+" |  "+o.getComision()+" |  "+o.getNumero()*o.getPrecio()+" |\n");
                    }
                    escribir.write(guiones+"\n");
                    escribir.write("| Total Compras: "+totalC+"    |\n");
                    escribir.write("| Total Ventas: "+totalV+"     |\n");
                    escribir.write("| Total Dividendos: "+totalD+"    |\n");
                    escribir.write("| Comisiones Pagadas: "+totalCo+"   |\n");
                    escribir.write(guiones+"\n");
                    hayOperaciones=true;
                }
            }
            if(hayOperaciones==false){
                escribir.write("Ninguna Operacion entre estas fechas");
            }
            escribir.close();
        } catch (IOException e) {
            System.out.println("Error abriendo el archivo");
        }
    }

    /**
     * Funcion que genera un informe de la rentabilidad de los diferentes activos y los guarda en un archivo txt
     * @param cartera
     * @param fFin
     * @param nombreArchivo
     */
    public void generarInformeRentabilidad(Cartera cartera,LocalDate fFin,String nombreArchivo) {
        try (FileWriter escribir = new FileWriter(nombreArchivo)){
            escribir.write("Informe de rentabilidad de la cartera principal:\n");
            escribir.write("Fecha Final: "+fFin+"\n");
            for(Activo a:activos){
                Double totalC=0.00,totalV=0.00,totalD=0.00,totalCo=0.00,totalF=Precio.buscaPrecio(a.getSimbolo(),fFin)*cartera.getActivosCartera().get(a);;
                ArrayList<Operacion> oa=new ArrayList<>();
                for(Operacion o:cartera.getOperaciones()){
                    if(o.getActivo().getSimbolo().equals(a.getSimbolo())){
                        oa.add(o);
                        if(o.getTipoOperacion()==tipoOperacion.COMPRA){
                            totalC=totalC+o.getPrecio()*o.getNumero();
                        } else if (o.getTipoOperacion()==tipoOperacion.VENTA) {
                            totalV=totalV+o.getPrecio()*o.getNumero();
                        }
                        else {
                            totalD+=o.getPrecio();
                        }
                        totalCo+=o.getComision();
                    }
                }
                if(oa.size()>0){
                    escribir.write("\nActivo: "+a.getSimbolo()+" - "+a.getNombre()+"\n");
                    escribir.write("Valor de compras en el periodo: "+totalC+"€\n");
                    escribir.write("Valor de ventas en el periodo: "+totalV+"€\n");
                    escribir.write("Valor de dividendos en el periodo: "+totalD+"€\n");
                    escribir.write("Valor en cartera el "+fFin+": "+cartera.getActivosCartera().get(a)*Precio.buscaPrecio(a.getSimbolo(), fFin)+"€\n");
                    escribir.write("Rentabilidad: "+(((totalF+totalV+totalD)/totalC)-1)*100);
                }
            }
        }catch(IOException e) {
        throw new RuntimeException(e);
        }
    }
    public void nuevoActivo(Activo activo){
        activos.add(activo);
    }


}
