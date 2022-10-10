package modelo.test;

import modelo.juego.Juego;
import modelo.jugador.Jugador;
import modelo.entradaDatos.Leer;

public class TestJuego {
	public static void main(String[] args) {
		
		Leer lectura = new Leer();

        Jugador[] jugadores = {
            new Jugador("J1"),
            new Jugador("J2"),
            new Jugador("J3"),
            new Jugador("J4")
        };

    	Juego juego = new Juego(jugadores, 150);

        while (! juego.finJuego()) {

            System.out.println("Ronda: " + juego.getRonda());
            
            while (! juego.finPartida()) {
            	
                juego.mostrarTurnoActual();
                juego.mostrarSentidoActual();

                juego.cartaCentro();
               
                juego.mostrarCartasJugadorActual();
                int indiceCarta = lectura.pedirIntRango(-1, juego.numCartasJugadorActual(),
                        "Elige una carta de la mano, -1 para tomar una carta",
                        "Solo numeros entre -1 y " + juego.numCartasJugadorActual());

                if (indiceCarta == -1) {
                    System.out.println("Se eligió tomar una carta");
                    juego.robarCarta();
                    juego.cambioTurno();
                } else {
                    if (!juego.cartaCompatible(indiceCarta)) {
                        System.out.println("La carta no es valida");
                    } else {
                        juego.cambioTurno();
                    }
                }

            }
            
            juego.siguienteRonda();

            System.out.println("Ranking");
            juego.ranking();
            
        }
	        
        Jugador ganador = juego.ganandorJuego();
        System.out.println("El ganador del juego es " + ganador.getNombre());

	    
	}

}

