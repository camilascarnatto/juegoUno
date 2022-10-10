package modelo.juego;

/**
 * IMPLEMENTAR SINGLENTON:
 * creacion de una unica instancia en toda la ejecucion
 * 
 */

import java.util.ArrayList;
import java.util.Collections;

import modelo.baraja.BarajaUno;
import modelo.baraja.CartaUno;
import modelo.baraja.Metodos;
import modelo.entradaDatos.Leer;
import modelo.enumerados.ColoresBarajaUno;
import modelo.jugador.Jugador;
import modelo.CambiosEnElJuego;
import modelo.Observador;

public class Juego {

	private int ronda;
    private BarajaUno baraja;
    private BarajaUno mazoDescarte;
    private int turno;
    //private Jugador[] jugadores;
    private int puntosLimite;
    private static Juego instancia = new Juego();     // SINGLENTON

    private final static int SETEANDO = 0;
    private final static int JUGANDO = 1;
    private final static int FIN_JUEGO = 2;
    private int estado = SETEANDO;
    
    private ArrayList<Observador> observadores = new ArrayList<>();
    private ArrayList<CambiosEnElJuego> cambiosDeEstado = new ArrayList<>();
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    
    
    
    /*
    public Juego(Jugador[] jugadores, int puntosLimite) {
        this.jugadores = jugadores;
        this.puntosLimite = puntosLimite;
        this.ronda = 1;
        this.turnoInicial();
        this.repartirCartas();

        if (this.baraja.getUltimaCarta().isEspecial()) {
            this.aplicarEfectoCarta(true);
        }

    }
    */
    
    private static Juego getInstance(){
    	return instancia;
    }
    
    public void agregarJugador (String nombre) {
    	if (estado == SETEANDO) {
    		jugadores.add(new Jugador(nombre));
    		notificarObservadores(CambiosEnElJuego.NUEVO_JUGADOR);
    		notificarAhora();
    	}
    }
    
    /**
     * Implementacion de observer
     */
    private void notificarObservadores(CambiosEnElJuego cambioEstado) {
    	for(Observador observador : observadores) {
    			observador.actualizar(cambioEstado);
    	}
    }
    
    private void notificarAhora() {
    	for (Observador observador : observadores) {
    		for(CambiosEnElJuego cambio : cambiosDeEstado) {
    			observador.actualizar(cambio);
    		}
    		
    		cambiosDeEstado.clear();
    	}
    }
    
    public void turnoInicial() {
        this.turno = Metodos.generaNumeroEnteroAleatorio(0, this.jugadores.length - 1);
    }

    public void repartirCartas() {
        this.baraja = new BarajaUno();
        for (Jugador j : this.jugadores) {
            j.setCartas(this.baraja.darCartas(7, false));
        }
    }
    
    public void agregarObservador(Observador observador) {
    	observadores.add(observador);
    }
    
    public void iniciar() {
    	if(jugadores.size() >= 2) {
    		baraja.barajar();
    		
    		
    	}
    }

    public int getRonda() {
        return this.ronda;
    }

    public Jugador jugadorActual() {
        return this.jugadores<this.turno>;
    }

    public void mostrarTurnoActual() {
        System.out.println("Es el turno del jugador: " + this.jugadorActual().getNombre());
    }

    public void mostrarCartasJugadorActual() {
        this.jugadorActual().mostrarMano();
    }

    public int numCartasJugadorActual() {
        return this.jugadorActual().numCartas();
    }

    public void mostrarSentidoActual() {
        if (this.baraja.isSentido()) {
            System.out.println("El sentido actual es la de las agujas del reloj");
        } else {
            System.out.println("El sentido actual es el contrario a la de las agujas del reloj");
        }
    }

    public void cambioTurno() {

        if (this.baraja.isSentido() && turno == this.jugadores.length - 1) {
            turno = 0;
        } else if (!this.baraja.isSentido() && turno == 0) {
            this.turno = this.jugadores.length - 1;
        } else {
            if (this.baraja.isSentido()) {
                turno++;
            } else {
                turno--;
            }
        }

    }

    public Jugador ganadorPartida() {

        Jugador j = null;
        for (int i = 0; i < this.jugadores.length; i++) {
            if (this.jugadores[i].sinCartas()) {
                j = this.jugadores[i];
            }
        }

        return j;

    }

    public Jugador ganandorJuego() {
        Jugador j = null;
        int mayorPuntuacion = 0;
        for (int i = 0; i < this.jugadores.length; i++) {
            if (this.jugadores[i].getPuntos() >= mayorPuntuacion) {
                mayorPuntuacion = this.jugadores[i].getPuntos();
                j = this.jugadores[i];
            }
        }

        return j;

    }

    public boolean finJuego() {
        return this.ganandorJuego().getPuntos() >= this.puntosLimite;
    }

    public boolean finPartida() {
        return this.ganadorPartida() != null;
    }

    public int calcularPuntos() {

        int puntos = 0;
        for (int i = 0; i < this.jugadores.length; i++) {
            puntos += this.jugadores[i].puntosMano();
        }
        return puntos;

    }

    public void siguienteRonda() {
        this.ronda++;
        int puntos = this.calcularPuntos();

        Jugador ganador = this.ganadorPartida();
        ganador.aumentarPuntos(puntos);
        System.out.println("Ha ganado " + ganador.getNombre() + " " + puntos + " puntos");

        this.repartirCartas();
    }

    public void ranking() {
        ArrayList<Jugador> jugadoresClon = new ArrayList<>();

        for (Jugador j : jugadores) {
            jugadoresClon.add(j);
        }

        Collections.sort(jugadoresClon);

        for (Jugador j : jugadoresClon) {
            System.out.println(j);
        }

    }

    public boolean cartaCompatible(int posCarta) {

        CartaUno cartaJ = this.jugadorActual().getCartaAt(posCarta);
        CartaUno cartaM = this.baraja.getUltimaCarta();

        if (cartaJ.compatible(cartaM) || baraja.getColorActual() == cartaJ.getPalo()) {
            this.baraja.setUltimaCarta(cartaJ);
            this.baraja.agregarCartaMonton(cartaJ);
            this.jugadorActual().removeCartaAt(posCarta);

            if (cartaJ.isEspecial()) {
                this.aplicarEfectoCarta(false);
            } else {
                this.baraja.actualizarColor();
            }

            return true;
        } else {
            return false;
        }

    }

    public Jugador siguienteJugador() {
        if (this.baraja.isSentido()) {
            if (this.turno == this.jugadores.length - 1) {
                return this.jugadores[0];
            } else {
                return this.jugadores[this.turno + 1];
            }
        } else {
            if (this.turno == 0) {
                return this.jugadores[this.jugadores.length - 1];
            } else {
                return this.jugadores[this.turno - 1];
            }
        }
    }

    public void elegirColor() {

        ColoresBarajaUno[] colores = ColoresBarajaUno.values();

        Leer lectura = new Leer();

        for (int i = 0; i < colores.length - 1; i++) {
            System.out.println(i + ". " + colores[i].name());
        }

        int posColor = lectura.pedirIntRango(0, colores.length - 2, "Elige un color:");

        this.baraja.setColorActual(colores[posColor]);

    }

    public void pasarTurno() {
        this.cambioTurno();
    }

    public void cartaCentro() {
        System.out.println("Carta en el centro: ");
        System.out.println(this.baraja.getUltimaCarta() + " (" + this.baraja.getColorActual() + ")");
    }

    public void robarCarta() {
        CartaUno carta = this.baraja.siguienteCarta(false);
        if (carta != null) {
            this.jugadorActual().agregarCartas(carta);
        } else {
            this.baraja.barajar();
            carta = this.baraja.siguienteCarta(false);
            if (carta != null) {
                this.jugadorActual().agregarCartas(carta);
            } else {
                System.out.println("Ya no hay mas cartas que robar");
            }
        }
    }

    public void aplicarEfectoCarta(boolean inicio) {

        CartaUno cartaM = this.baraja.getUltimaCarta();
        Jugador siguienteJ = null;
        ArrayList<CartaUno> cartas;
        switch (cartaM.getEfecto()) {
            case MASDOS:
                if (inicio) {
                    siguienteJ = this.jugadorActual();
                } else {
                    siguienteJ = this.siguienteJugador();
                }
                cartas = this.baraja.darCartas(2, false);
                if (cartas == null) {
                    this.baraja.barajar();
                    cartas = this.baraja.darCartas(2, false);
                }
                siguienteJ.agregarCartas(cartas);
                System.out.println("2 cartas mas para el jugador " + siguienteJ.getNombre());
                this.pasarTurno();
                this.baraja.actualizarColor();
                break;
            case MASCUATRO:

                if (inicio) {
                    siguienteJ = this.jugadorActual();
                } else {
                    siguienteJ = this.siguienteJugador();
                }
                cartas = this.baraja.darCartas(4, false);
                if (cartas == null) {
                    this.baraja.barajar();
                    cartas = this.baraja.darCartas(4, false);
                }
                siguienteJ.agregarCartas(cartas);
                System.out.println("4 cartas mas para el jugador " + siguienteJ.getNombre());
                this.elegirColor();
                System.out.println("Color cambiado");
                this.pasarTurno();
                break;
            case SALTO:
                this.pasarTurno();
                System.out.println("Se salta al siguiente jugador");
                this.baraja.actualizarColor();
                break;
            case REVERSO:
                this.baraja.cambiarSentido();
                System.out.println("Se cambia el sentido");
                this.baraja.actualizarColor();
                break;
            case CAMBIOCOLOR:
                this.elegirColor();
                System.out.println("Color cambiado");
                break;
        }

    }

}
