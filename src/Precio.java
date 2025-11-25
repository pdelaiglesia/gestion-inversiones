import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Precio {
    public static double buscaPrecio(String simbolo, LocalDate fecha) throws IOException, NumberFormatException {
        double precio = 0;
        String url = "https://finance.yahoo.com/quote/" + simbolo + "/history?p=" + simbolo + "&guccounter=2";

        URL yahooURL = new URL(url);
        Scanner con = new Scanner(yahooURL.openStream());
        String sFecha = DateTimeFormatter.ofPattern("MMM dd, yyyy").withLocale(Locale.US).format(fecha);

        String entrada;
        while (con.hasNext()) {
            entrada = con.nextLine();
            if(entrada.contains(sFecha)) {
                precio = analizaRespuesta(entrada, sFecha);
                break;
            }
        }
        con.close();
        return precio;
    }

    private static double analizaRespuesta(String entrada, String sFecha) throws NumberFormatException{
        double precio;

        // Eliminamos las primeras columnas y nos quedamos con la de precio ajustado de cierre.
        String linea = entrada.substring(entrada.indexOf(sFecha) + sFecha.length());
        for (int i = 0; i < 5; i++) {
            linea = linea.substring(linea.indexOf("<span>")+6);
        }

        // Si encontramos un double, lo convertimos y devolvemos
        precio = Double.parseDouble(linea.substring(0, linea.indexOf("<")));
        return precio;
    }
}
