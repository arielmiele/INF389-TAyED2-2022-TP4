
/**
 * INF389 - Taller de Algoritmos y Estructura de Datos II
 * Trabajo Práctico N° 4
 * Nombre: Ariel Gerardo Miele
 * Legajo: VINF011482
 * DNI: 34.434.704
 */

import java.util.*;

public class Archivos {

    /**
     * Método que permite borrar la consola para una mejor utilización del sistema
     */
    public static void ClearConsole() {
        try {
            // Verifica el sistema donde se está ejecutando el programa
            String operatingSystem = System.getProperty("os.name");

            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Genera la cadena de n posiciones con números aleatorios entre 0 y 1.
     * 
     * @param n es la cantidad de posiciones de la cadena requerida.
     * @return la cadena generada.
     */
    public static int[] generarCadena(int n) {
        Random random = new Random(); // Inicializa el random
        int[] cadena = new int[n]; // Inicializa la cadena a generar
        for (int i = 0; i < n; i++) {
            // Crea la cadena con números aleatorios entre 0 y 1 para cada una de sus
            // posiciones
            cadena[i] = random.nextInt(2);
        }
        return cadena; // Devuelve la cadena generada
    }

    /**
     * Calcula la calidad de la cadena de acuerdo con la fórmula
     * H(x) = SUMATORIA(x * 2)
     * 
     * @param cadena para la cual quiere calcularse la calidad
     * @param n      el número de posiciones de la cadena
     * @return el resultado del cálculo para la calidad de la cadena
     */
    public static int calidadCadena(int[] cadena, int n) {
        int resultado = 0;
        for (int i = 0; i < n; i++) {
            resultado += (cadena[i] * 2);
        }
        return resultado;
    }

    /**
     * Imprime la cadena por consola
     * 
     * @param cadena a imprimir
     * @param n      el número de posiciones de la cadena
     */
    public static void imprimirCadena(int[] cadena, int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(cadena[i]);
        }
        System.out.print(" - Calidad de la cadena: " + calidadCadena(cadena, n) + "\n");
    }

    /**
     * Crea una nueva cadenaGenética de acuerdo con la función genética a partir de
     * dos cadenas
     * Si las posiciones i de ambas cadenas son iguales, la posición i de la cadena
     * genética es 1, si son diferentes es 0
     * 
     * @param cadenaOriginal desde la que se calculará la función genética
     * @param cadenaNueva    generada en el paso anterior que se compara con la
     *                       original
     * @param n              es la cantidad de posiciones de la cadena
     * @return la nueva cadena genética
     */
    public static int[] funcionGenetica(int[] cadenaOriginal, int[] cadenaNueva, int n) {
        int[] cadenaGenetica = new int[n];
        for (int i = 0; i < n; i++) {
            if (cadenaOriginal[i] == cadenaNueva[i]) {
                cadenaGenetica[i] = 1;
            } else {
                cadenaGenetica[i] = 0;
            }
        }
        return cadenaGenetica;
    }

    /**
     * Main que ejecuta el código para obtener la cadena óptima
     * Donde H(x) = 40
     * equivalente a una cadena con todas sus posiciones con valor 1
     * 
     * @param args
     */
    public static void main(String[] args) {

        // Almacena el tiempo inicial de ejecución para calcular el tiempo total
        final long tiempoInicio = System.currentTimeMillis();

        // Limpia la consola para poder iniciar el programa de forma más ordenada
        ClearConsole();

        System.out.println("\nBienvenido al generador de Cadenas Optimas con Funcion Genetica!\n");

        // Inicializa la cantidad de posiciones de las cadenas en 20
        final int cantidadPosiciones = 20;
        // Inicializa el valor de la cadena óptima, en este caso será 40
        final int cadenaIdeal = cantidadPosiciones * 2;

        // Inicializa las tres cadenas necesarias para la ejecución del programa
        int[] cadenaOriginal = new int[cantidadPosiciones];
        int[] cadenaNueva = new int[cantidadPosiciones];
        int[] cadenaGenetica = new int[cantidadPosiciones];

        // Inicializa los contadores de iteraciones (nuevas cadenas generadas) y cruces
        // (cadenas más cercanas a la óptima que la original)
        int iteraciones = 0;
        int cruces = 0;

        do {
            if (iteraciones == 0) {
                // Si es la primer iteración, genera la cadena original
                cadenaOriginal = generarCadena(cantidadPosiciones);
                System.out.print("Cadena Original: ");
                // Muestra la cadena original por pantalla
                imprimirCadena(cadenaOriginal, cantidadPosiciones);
                System.out.print("\n");
            }
            // Genera una nueva cadena
            cadenaNueva = generarCadena(cantidadPosiciones);
            // Genera una nueva cadena genética comparando la cadena original con la nueva
            // cadena
            cadenaGenetica = funcionGenetica(cadenaOriginal, cadenaNueva,
                    cantidadPosiciones);
            if (calidadCadena(cadenaGenetica, cantidadPosiciones) > calidadCadena(cadenaOriginal, cantidadPosiciones)) {
                // Si la cadena genética tiene una mayor calidad que la cadena original -> H(x)
                // mayor
                // La nueva cadena original será la cadena genética generada
                cadenaOriginal = cadenaGenetica;
                System.out.print("Cadena Genetica es la nueva Cadena Original: ");
                // Se muestra la nueva cadena original por pantalla
                imprimirCadena(cadenaOriginal, cantidadPosiciones);
                // Se incrementa la variable de cruces para analizar la cantidad de nuevas
                // cadenas genéticas mejores que cadenas originales generadas
                cruces++;
                System.out.println("Cruces necesarios hasta ultima Cadena: " + cruces);
                System.out.println("Iteraciones necesarias hasta ultima Cadena: " + (iteraciones + 1) + "\n");
            }
            // Se incrementa la variable iteraciones para analizar la cantidad de nuevas
            // cadenas que se generaron para obtener la cadena optima
            iteraciones++;
        } while (calidadCadena(cadenaOriginal, cantidadPosiciones) != cadenaIdeal);
        // Cuando la cadena óptima se encuentra, el while finaliza su ejecución

        // Almacena el tiempo final de ejecución
        final long tiempoFin = System.currentTimeMillis();

        System.out.println("Resultados de la ejecucion:");
        System.out.println("***************************");
        System.out.print("Cadena Optima: ");
        // Imprime la cadena óptima
        imprimirCadena(cadenaOriginal, cantidadPosiciones);
        // Imprime la cantidad de iteraciones necesarias para obtener la cadena óptima
        System.out.println("Cantidad de iteraciones necesarias para obtener la Cadena Optima: " + iteraciones);
        // Imprime la cantidad de cruces entre cadenas genéticas y cadenas óriginales
        // para obtener la cadena óptima
        System.out.println("Cantidad de cruces necesarios para obtener la Cadena Optima: " + cruces);
        // Calcula e imprime por pantalla el tiempo de ejecución del programa en
        // milisegundos
        System.out.println("Tiempo total de ejecucion: " + (tiempoFin - tiempoInicio) + " milisegundos.");
        System.out.println("\nMuchas gracias por utilizar el generador de Cadenas Optimas con Funcion Genetica!\n");
    }
}