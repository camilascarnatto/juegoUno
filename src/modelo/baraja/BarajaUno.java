package modelo.baraja;

import modelo.enumerados.ColoresBarajaUno;
import modelo.enumerados.EfectosBarajaUno;

public class BarajaUno extends Baraja<CartaUno> {

    public BarajaUno() {
        this.numCartas = 108;
        this.cartasPorPalo = 13;

        this.crearBaraja();
        super.barajar();
    }

    @Override
    public void crearBaraja() {

        ColoresBarajaUno[] colores = ColoresBarajaUno.values();

        for (ColoresBarajaUno color : colores) {

            if (color != ColoresBarajaUno.NEGRO) {

                for (int i = 0; i < this.cartasPorPalo; i++) {
                    if (i > 9) {
                    	//Creacion de cartas especiales
                        switch (i) {
                            case 10:
                                this.cartas.push(new CartaUno(color, EfectosBarajaUno.MASDOS));
                                break;
                            case 11:
                                this.cartas.push(new CartaUno(color, EfectosBarajaUno.SALTO));
                                break;
                            case 12:
                                this.cartas.push(new CartaUno(color, EfectosBarajaUno.REVERSO));
                                break;
                        }
                    } else {
                        this.cartas.push(new CartaUno(i, color));
                    }
                }

                for (int i = 1; i < this.cartasPorPalo; i++) {
                    if (i > 9) {
                        switch (i) {
                            case 10:
                                this.cartas.push(new CartaUno(color, EfectosBarajaUno.MASDOS));
                                break;
                            case 11:
                                this.cartas.push(new CartaUno(color, EfectosBarajaUno.SALTO));
                                break;
                            case 12:
                                this.cartas.push(new CartaUno(color, EfectosBarajaUno.REVERSO));
                                break;
                        }
                    } else {
                        this.cartas.push(new CartaUno(i, color));
                    }
                }

            } else {
            	//8 cartas negras especiales (4 cartas +4 y 4 cartas de cambioColor
                for (int i = 0; i < 4; i++) {
                    this.cartas.push(new CartaUno(color, EfectosBarajaUno.MASCUATRO));
                    this.cartas.push(new CartaUno(color, EfectosBarajaUno.CAMBIOCOLOR));
                }

            }

        }

    }

}
