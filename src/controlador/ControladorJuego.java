package controlador;

import java.util.ArrayList;

import modelo.CambiosEnElJuego;
import modelo.baraja.Carta;
import modelo.baraja.CartaUno;
import modelo.juego.Juego;
import modelo.jugador.Jugador;
import modelo.Observador;


public class ControladorJuego implements Observador{
	private Juego juego = Juego.getInstance();
	private Visualizable vista;
	
	public ControladorJuego(Visualizable vista) {
		this.vista = vista;
		juego.agregarObservador(this);
		vista.mostrarMenuSeteando();
		vista.setControlador(this);
		vista.comenzar();
	}
	
	@Override
	public void actualizar(CambiosEnElJuego cambioEstado) {
		switch (cambioEstado) {
		
			case NUEVO_JUGADOR:
				vista.mostrarListaJugadores(juego.getJugadores());
				break;
			
			case JUEGO_INICIADO:
				vista.mostrarMenuJugando();
				System.out.println();
				vista.mostrarJugadorEnTurno(juego.jugadorActual());
				System.out.println();
				System.out.println("- - - - - Carta en mesa - - - - -");
				vista.mostrarCartaCentro(juego.cartaCentro());
				System.out.println();
				break;
				
			case CAMBIO_TURNO:
				vista.mostrarJugadorEnTurno(juego.jugadorActual());
				break;
				
			case CAMBIO_SENTIDO:
				vista.mostrarSentidoRonda(juego.mostrarSentidoActual());
				break;
				
			case UNO:
				vista.mostrarMenuUno(juego.jugadorActual());
				break;
				
			case JUEGO_TERMINADO:
				vista.mostrarMenuFin();
		}
	}
	
	public ArrayList<CartaUno> mostrarCartasJugador(){
		Jugador jugador = juego.jugadorActual();
		jugador.mostrarMano();
	}
	
	public void agregarJugador(String nombre){
		juego.agregarJugador(nombre);
		System.out.println("\n Agregaste un jugador al juego");
	}
	
	public void iniciarJuego() {
		juego.iniciar();
	}
}
