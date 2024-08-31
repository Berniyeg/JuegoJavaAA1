package miJuego;

import java.util.Random;

public class Game {

    private static final int TAMAÑO = 6;
    private static final String VACIO = "\u001B[47m     \u001B[0m";
    private static final String JUGADOR = "\u001B[42m  J  \u001B[0m";
    private static final String ENEMIGO = "\u001B[41m  E  \u001B[0m";
    private static final String SALIDA = "\u001B[43m  S  \u001B[0m";
    private static final String VIDA_EXTRA = "\u001B[44m  V  \u001B[0m";

    private final String[][] tablero;
    private int jugadorX;
    private int jugadorY;
    private int salidaX;
    private int salidaY;
    private int vidas;
    private boolean modoTrucos;

    public Game() {
        this.tablero = new String[TAMAÑO][TAMAÑO];
        this.vidas = 3; // Cada jugador empieza con 3 vidas
        this.modoTrucos = false; // Modo trucos desactivado por defecto
        inicializarTablero();
        colocarJugadorAleatorio();
        colocarEnemigosAleatorios();
        colocarSalidaAleatoria();
        colocarVidasExtrasAleatorias();
    }

    public void pintarTablero() {
        for (int i = 0; i < TAMAÑO; i++) {
            // Imprimir línea superior del cuadro de la fila i
            for (int k = 0; k < 5 * TAMAÑO + 1; k++) {
                System.out.print("-");
            }
            System.out.println("------");

            // Imprimir contenido de cada celda con su borde
            for (int j = 0; j < TAMAÑO; j++) {
                if (tablero[i][j].equals(ENEMIGO)) {
                    System.out.print("|" + (modoTrucos ? ENEMIGO : VACIO));
                } else {
                    System.out.print("|" + tablero[i][j]);
                }
            }
            System.out.println("|");
        }

        // Imprimir línea inferior del cuadro
        for (int k = 0; k < 5 * TAMAÑO + 1; k++) {
            System.out.print("-");
        }
        System.out.println("------");
    }

    public int getVidas() {
        return vidas;
    }

    public void activarModoTrucos() {
        modoTrucos = !modoTrucos;
        System.out.println("Modo trucos " + (modoTrucos ? "activado" : "desactivado"));
    }

    public boolean moverJugador(String movimiento) {
        if (movimiento.length() < 2) return false; // Movimiento no válido
        int numCasillas = Character.getNumericValue(movimiento.charAt(0));
        if (numCasillas < 1 || numCasillas > 3) {
            System.out.println("Número de casillas no válido. Debe estar entre 1 y 3.");
            return false;
        }
        char direccion = Character.toUpperCase(movimiento.charAt(1));

        int nuevaX = jugadorX;
        int nuevaY = jugadorY;

        switch (direccion) {
            case 'W':
                nuevaX = (jugadorX - numCasillas + TAMAÑO) % TAMAÑO;
                break;
            case 'S':
                nuevaX = (jugadorX + numCasillas) % TAMAÑO;
                break;
            case 'A':
                nuevaY = (jugadorY - numCasillas + TAMAÑO) % TAMAÑO;
                break;
            case 'D':
                nuevaY = (jugadorY + numCasillas) % TAMAÑO;
                break;
            default:
                System.out.println("Dirección no válida. Usa 'W' para arriba, 'S' para abajo, 'A' para izquierda, y 'D' para derecha.");
                return false;
        }

        // Actualizar la posición del jugador en el tablero
        if (tablero[nuevaX][nuevaY].equals(ENEMIGO)) {
            vidas--;
            System.out.println("¡Has caído en una casilla con un enemigo! Te quedan " + vidas + " vidas.");
            if (vidas == 0) {
                System.out.println("¡Has perdido todas tus vidas!");
                return true; // Indicar que el jugador ha perdido
            }
        } else if (tablero[nuevaX][nuevaY].equals(VIDA_EXTRA)) {
            vidas++;
            System.out.println("¡Has encontrado una vida extra! Ahora tienes " + vidas + " vidas.");
            tablero[nuevaX][nuevaY] = VACIO; // La casilla de vida extra se convierte en vacía después de ser usada
        }

        tablero[jugadorX][jugadorY] = VACIO;
        jugadorX = nuevaX;
        jugadorY = nuevaY;
        tablero[jugadorX][jugadorY] = JUGADOR;

        // Verificar si el jugador ha alcanzado la casilla de salida
        return (jugadorX == salidaX && jugadorY == salidaY);
    }

    private void inicializarTablero() {
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                tablero[i][j] = VACIO;
            }
        }
    }

    private void colocarJugadorAleatorio() {
        Random random = new Random();
        do {
            jugadorX = random.nextInt(TAMAÑO);
            jugadorY = random.nextInt(TAMAÑO);
        } while (!tablero[jugadorX][jugadorY].equals(VACIO)); // Asegurarse de que la posición esté vacía

        tablero[jugadorX][jugadorY] = JUGADOR; // Colocar al jugador en la posición aleatoria
    }

    private void colocarEnemigosAleatorios() {
        Random random = new Random();
        int enemigosColocados = 0;
        while (enemigosColocados < 8) {
            int x = random.nextInt(TAMAÑO);
            int y = random.nextInt(TAMAÑO);
            if (tablero[x][y].equals(VACIO)) {
                tablero[x][y] = ENEMIGO;
                enemigosColocados++;
            }
        }
    }

    private void colocarSalidaAleatoria() {
        Random random = new Random();
        do {
            salidaX = random.nextInt(TAMAÑO);
            salidaY = random.nextInt(TAMAÑO);
        } while (!tablero[salidaX][salidaY].equals(VACIO)); // Asegurarse de que la posición esté vacía

        tablero[salidaX][salidaY] = SALIDA; // Colocar la salida en la posición aleatoria
    }

    private void colocarVidasExtrasAleatorias() {
        Random random = new Random();
        int vidasColocadas = 0;
        while (vidasColocadas < 2) {
            int x = random.nextInt(TAMAÑO);
            int y = random.nextInt(TAMAÑO);
            if (tablero[x][y].equals(VACIO)) {
                tablero[x][y] = VIDA_EXTRA;
                vidasColocadas++;
            }
        }
    }
}
