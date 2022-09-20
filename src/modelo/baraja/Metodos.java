package modelo.baraja;

/**
 * Clase Metodos, contiene metodos estaticos
 * @author camila
 *
 */
public class Metodos {

    public static int generaNumeroEnteroAleatorio(int minimo, int maximo) {
        int num = (int) (Math.random() * (minimo - (maximo + 1)) + (maximo + 1));
        return num;
    }

}