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
		
		Juego juego = new Juego(jugadores, 100);
		
		while(!juego.finJuego()) {
			
			System.out.println("Ronda nro: " + juego.getRonda());
			
			while(!juego.finPartida()) {
				
				juego.mostrarTurnoActual();
				juego.mostrarSentidoActual();
				
				juego.cartaCentro();
				juego.mostrarCartasJugadorActual();
				
				int indiceCarta = lectura.pedirIntRango(-1, juego.numCartasJugadorActual(), 
						"Elige una carta de la mano, -1 para robar", 
						"Solo numeros entre -1 y " + juego.numCartasJugadorActual());
				
				if(indiceCarta == -1) {
					System.out.println("Se ha elegido agarrar una carta");
					juego.robarCarta();
					juego.cambioTurno();
					
				}else
					if(juego.cartaCompatible(indiceCarta)) {
						System.out.println("No es una carta valida");
					}else
						juego.cambioTurno();
			
			}
			
			juego.siguienteRonda();
			
			System.out.println("RANKING");
			juego.ranking();
		}
		
		Jugador ganador = juego.ganadorJuego();
		
		System.out.println("El ganador del juego es " + ganador.getNombre());
	}
}
