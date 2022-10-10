package controlador;

import modelo.baraja.CartaUno;

public interface Visualizable {
	void mostrarMenuSeteando();
	void mostrarMenuJugando();
	void mostrarCartasJugador();
	void mostrarCartaCentro(CartaUno carta);
}
