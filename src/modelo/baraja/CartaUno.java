package modelo.baraja;

import modelo.enumerados.ColoresBarajaUno;
import modelo.enumerados.EfectosBarajaUno;


public class CartaUno extends Carta<ColoresBarajaUno> {
	
    private EfectosBarajaUno efecto;

    public CartaUno(int numero, ColoresBarajaUno color) {
        super(numero, color);
    }

    public CartaUno() {
    }

    public CartaUno(ColoresBarajaUno color, EfectosBarajaUno efecto) {
        super(-1, color); // -1 para las que ya tienen un efecto
        this.efecto = efecto;
    }

    public EfectosBarajaUno getEfecto() {
        return efecto;
    }

    public boolean isEspecial() {
        return this.efecto != null;
    }

    @Override
    public String toString() {

        String estado = "";

        if (this.isEspecial()) {
            switch (this.efecto) {
                case MASDOS:
                    estado = "+2 " + palo;
                    break;
                case MASCUATRO:
                    estado = "+4"; // no tiene color porque es negra
                    break;
                case SALTO:
                    estado = "SALTO TURNO " + palo;
                    break;
                case REVERSO:
                    estado = "REVERSO " + palo;
                    break;
                case CAMBIOCOLOR:
                    estado = "CAMBIO COLOR";
                    break;
            }
        } else {
            estado = numero + " " + palo;
        }

        return estado;
    }

}