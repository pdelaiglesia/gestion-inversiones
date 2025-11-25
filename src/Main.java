/**
 * Clase Main, principal
 */

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Pablo de la Iglesia
 * @author Victor Hernandez
 */
public class Main {
    /**
     * Funcion que carga desde un archiivo inv la clase cartera
     * @param nombreArchivo
     * @return
     */
    public static Cartera cargarCartera(String nombreArchivo) {
        Cartera cartera=null;
        try {
            FileInputStream archivo = new FileInputStream(nombreArchivo);
            ObjectInputStream entrada = new ObjectInputStream(archivo);
            cartera = (Cartera) entrada.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no se pudo abrir");
        } catch (IOException e) {

            System.out.println("Error abriendo archivo");
        } catch (ClassNotFoundException e) {

            System.out.println("Clase no encontrada");
        }
        return cartera;
    }

    /**
     * Funcion que guarda la Clase cartera como un objeto en un archivo inv
     * @param cartera
     * @param nombreArchivo
     */
    public static void guardarCartera(Cartera cartera, String nombreArchivo)  {
        try {
            FileOutputStream archivo1 = new FileOutputStream(nombreArchivo);
            ObjectOutputStream salida = new ObjectOutputStream(archivo1);
            salida.writeObject(cartera);
            salida.close();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no se pudo abrir");
        } catch (IOException e) {
            System.out.println("Error abriendo archivo");
        }
    }

    /**
     * Funcion cargar Activos, carga los diferentes activos desde un txt y los guarda segun sus respectivos tipos
     * @param nombreArchivo
     * @return
     */
    public static ArrayList<Activo> cargarActivos(String nombreArchivo){
        ArrayList<Activo> activos=new ArrayList<>();
        try(BufferedReader leer=new BufferedReader(new FileReader(nombreArchivo))){
            String l=leer.readLine();
            while(l!=null){
                String palabras[]=l.split(",");
                if(palabras[0].equals("ACCION")){
                    activos.add(new Accion(palabras[1],palabras[2], Boolean.parseBoolean(palabras[4]),palabras[3]));
                }
                else if (palabras[0].equals("ETF")) {
                    activos.add(new ETF(palabras[1],palabras[2],Boolean.parseBoolean(palabras[4]),palabras[3]));
                }
                else{
                    activos.add(new FondoInversion(palabras[1],palabras[2],Boolean.parseBoolean(palabras[3]),palabras[4]));
                }
                l=leer.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return activos;
    }

    /**
     * Funcion main
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        boolean exit=false;
        Scanner teclado=new Scanner(System.in);
        System.out.println("BIENVENIDO");
        GestionInversiones gestionInversiones=new GestionInversiones(cargarCartera("cartera.inv"),cargarActivos("activos.csv"));
        //While para el menu
        while(!exit){
            switch (gestionInversiones.menu()){
                case 1:
                    //Registrar un activo
                    //Pedimos info del activo
                    System.out.println("Introduce el simbolo del activo: ");
                    String algo= teclado.next();
                    System.out.println("Introduce el nombre del activo: ");
                    String nombre3= teclado.next();
                    System.out.println("Introduce true si es negociable y false si no lo es: ");
                    Boolean negociable=teclado.nextBoolean();
                    System.out.println("Introduce el tipo de Activo:\n1.Accion\n2.ETF\n3.Fondo de Inversion");
                    int eleccion= teclado.nextInt();
                    //Segun el tipo pedimos el ultimo dato y lo creamos
                    if(eleccion==1){
                        System.out.println("Introduce el mercado: ");
                        gestionInversiones.nuevoActivo(new Accion(algo,nombre3,negociable, teclado.next()));
                    }
                    else if(eleccion==3){
                        System.out.println("Introduce la categoria: ");
                        gestionInversiones.nuevoActivo(new ETF(algo,nombre3,negociable, teclado.next()));
                    }
                    else{
                        System.out.println("Introduce la categoria: ");
                        gestionInversiones.nuevoActivo(new FondoInversion(algo,nombre3,negociable, teclado.next()));
                    }
                    break;
                case 2:
                    //Ingresar dinero
                    System.out.println("Introduce cantidad a ingresar: ");
                    gestionInversiones.ingresar(teclado.nextDouble());
                    System.out.println("Saldo ingresado con exito");
                    break;
                case 3:
                    //Retirar dinero
                    System.out.println("Introduce cantidad a retirar: ");
                    gestionInversiones.retirar(teclado.nextDouble());
                    break;
                case 4:
                    //Obtener info de cuenta y saldo
                    System.out.println(gestionInversiones.getCartera().getCuentaAsociada().toString());
                    break;
                case 5:
                    //Realizar compra de activo
                    //Se pide informacion de la compra
                    System.out.println("Introduce el simbolo: ");
                    String simbolo= teclado.next();
                    System.out.println("Introduce el numero de acciones: ");
                    int numero= teclado.nextInt();
                    System.out.println("Introduce el valor de las comisiones pagadas: ");
                    Double comisiones= teclado.nextDouble();
                    //Se busca el activo a comprar
                    Activo activo=gestionInversiones.buscarActivo(simbolo);
                    //Si no se encuentra se muestra un error
                    if(activo==null){
                        System.out.println("Activo no encontrado");
                    }
                    //Si se encuentra, se intenta comprar si hay suficiente saldo en la cuenta
                    else{
                        if(gestionInversiones.getCartera().comprar(activo,LocalDate.now(),numero,Precio.buscaPrecio(simbolo,LocalDate.now()),comisiones)==true){
                            System.out.println("Compra hecha");
                        }
                        else{
                            System.out.println("Error en la compra");
                        }
                    }
                    break;
                case 6:
                    //Realizar una venta
                    //Se pide informacion del activo y la cnatidad a vender
                    System.out.println("Introduce el simbolo: ");
                    String simbolo1= teclado.next();
                    System.out.println("Introduce el numero de acciones: ");
                    int numero1= teclado.nextInt();
                    System.out.println("Introduce el valor de las comisiones pagadas: ");
                    Double comisiones1= teclado.nextDouble();
                    //Se busca el activo a vender y si tiene suficiente numero de activos se vende
                    Activo activo1=gestionInversiones.buscarActivo(simbolo1);
                    if(activo1==null){
                        System.out.println("Activo no encontrado");
                    }
                    else{
                        if(gestionInversiones.getCartera().vender(activo1,LocalDate.now(),numero1,Precio.buscaPrecio(simbolo1,LocalDate.now()),comisiones1)==true){
                            System.out.println("Venta hecha");
                        }
                        else{
                            System.out.println("No tienes suficientes activos para vender");
                        }
                    }
                    break;
                case 7:
                    //Cobrar dividendos
                    //Se pide la info y el valor de los dividendos y se añade a la cuenta
                    System.out.println("Introduce el simbolo: ");
                    String simbolo2= teclado.next();
                    System.out.println("Introduce el valor de los dividendos: ");
                    Double valor= teclado.nextDouble();
                    System.out.println("Introduce el valor de las comisiones pagadas: ");
                    Double comisiones2= teclado.nextDouble();
                    gestionInversiones.getCartera().cobrarDividendos(gestionInversiones.buscarActivo(simbolo2),LocalDate.now(),valor,comisiones2);
                    break;
                case 8:
                    //Informe de las operaciones realizadas
                    System.out.println(gestionInversiones.getCartera().toString());
                    break;
                case 9:
                    //Informe de operaciones entre fechas
                    System.out.println("Introduce las dos fechas de forma cronologica de la siguiente forma 2022-12-13");
                    gestionInversiones.informeFechas(LocalDate.parse(teclado.next()),LocalDate.parse(teclado.next()));
                    break;
                case 10:
                    //Calcular rentabilidad
                    System.out.println("La rentabilidad hasta el momento es: ");
                    System.out.println(gestionInversiones.getCartera().rentablidad(LocalDate.now()));
                    break;
                case 11:
                    //Calcular rentabilidad hasta una fecha
                    System.out.println("Introduce la fecha de la siguiente forma 2022-12-13: ");
                    LocalDate fecha=LocalDate.parse(teclado.next());
                    System.out.println("La rentabilidad hasta "+fecha+" es:");
                    System.out.println(gestionInversiones.getCartera().rentablidad(fecha));
                    break;
                case 12:
                    //Salir
                    exit=true;
                    break;
                case 13:
                    //Generar informe de operaciones
                    System.out.println("");
                    LocalDate fInicio =LocalDate.parse("2002-02-02");
                    LocalDate fFinal =LocalDate.parse("2024-02-02");
                    gestionInversiones.generarInformeOperaciones(gestionInversiones.getCartera(),fInicio,fFinal,"Informe_rentabilidad_"+gestionInversiones.getCartera().getNombre()+"_"+fInicio+"_"+fFinal+".txt");
                    break;
                case 14:
                    //Generar informe de rentabilidad
                    System.out.println("");
                    LocalDate fFin=LocalDate.now();
                    gestionInversiones.generarInformeRentabilidad(gestionInversiones.getCartera(),fFin,"Informe_rentabilidad_"+gestionInversiones.getCartera().getNombre()+"_"+fFin+".txt");
                    break;
            }
        }
        guardarCartera(gestionInversiones.getCartera(),"cartera.inv");
    }
}