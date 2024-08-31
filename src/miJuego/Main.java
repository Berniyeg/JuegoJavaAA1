package miJuego;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Game juego1 = new Game();
        Game juego2 = new Game();
        boolean turnoJugador1 = true;

        while (true) {
            if (turnoJugador1) {
                System.out.println("Turno del Jugador 1");
                juego1.pintarTablero();
                System.out.print("JUGADOR 1:" + "elige movimiento (número de casillas (max. 3) + dirección: Ej. '1W', '2S'), pulsa 'T' para activar/desactivar trucos o 'X' para salir: ");
            } else {
                System.out.println("Turno del JUGADOR 2");
                juego2.pintarTablero();
                System.out.print("JUGADOR 2, elige movimiento (número de casillas (max. 3) + dirección: EJ. '1W' '2S'), pulsa 'T' para activar/desactivar trucos o 'X' para salir: ");
            }

            String opcion = teclado.nextLine().toUpperCase();
            if (opcion.equals("X")) {
                System.out.println("Fin del juego");
                break;
            }

            if (opcion.equals("T")) {
                if (turnoJugador1) {
                    juego1.activarModoTrucos();
                } else {
                    juego2.activarModoTrucos();
                }
                continue;
            }

            boolean jugadorGanoOPerdio;
            if (turnoJugador1) {
                jugadorGanoOPerdio = juego1.moverJugador(opcion);
                System.out.println("Tablero del JUGADOR 1 después del movimiento:");
                juego1.pintarTablero();
                System.out.println("Vidas restantes del JUGADOR 1: " + juego1.getVidas());
                if (jugadorGanoOPerdio) {
                    if (juego1.getVidas() == 0) {
                        System.out.println("¡JUGADOR 1 ha perdido todas sus vidas y ha perdido el juego!");
                    } else {
                        System.out.println("¡JUGADOR 1 ha alcanzado la salida y ha ganado el juego!");
                    }
                    break;
                }
                turnoJugador1 = false; // Cambiar turno al jugador 2
            } else {
                jugadorGanoOPerdio = juego2.moverJugador(opcion);
                System.out.println("Tablero del JUGADOR 2 después del movimiento:");
                juego2.pintarTablero();
                System.out.println("Vidas restantes del JUGADOR 2: " + juego2.getVidas());
                if (jugadorGanoOPerdio) {
                    if (juego2.getVidas() == 0) {
                        System.out.println("¡JUGADOR 2 ha perdido todas sus vidas y ha perdido el juego!");
                    } else {
                        System.out.println("¡JUGADOR 2 ha alcanzado la salida y ha ganado el juego!");
                    }
                    break;
                }
                turnoJugador1 = true; // Cambiar turno al jugador 1
            }
        }

        teclado.close();
    }
}
