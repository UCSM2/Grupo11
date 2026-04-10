import java.io.File;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int capacidadCamion = 15;
        int numZonas = 3;
        int maxPaquetesPorZona = 4;

        //matriz por zonas
        Paquete<Integer>[][] matrizZonas = new Paquete[numZonas][maxPaquetesPorZona];

        //simulando archivos
        String[] lineasArchivo = {
            "Z1P1,4,Alta,2,1", "Z1P2,2,Media,4,1", "Z1P3,6,Alta,5,1", "Z1P4,3,Baja,1,1",
            "Z2P1,5,Alta,3,2", "Z2P2,1,Baja,2,2", "Z2P3,4,Media,5,2", "Z2P4,7,Alta,7,2",
            "Z3P1,3,Media,4,3", "Z3P2,2,Baja,8,3", "Z3P3,5,Alta,4,3", "Z3P4,6,Alta,5,3"
        };

        //cargar matriz
        int[] contadores = new int[numZonas];
        for (String linea : lineasArchivo) {
            String[] partes = linea.split(",");
            String cod = partes[0].trim();
            int peso = Integer.parseInt(partes[1].trim());
            
            //mapeo de prioridad a Entero para usar Genericidad y que se ordene bien
            String prioStr = partes[2].trim();
            int prioridadNum = prioStr.equals("Alta") ? 3 : (prioStr.equals("Media") ? 2 : 1);
            
            int valor = Integer.parseInt(partes[3].trim());
            int zona = Integer.parseInt(partes[4].trim()) - 1; // Para índice 0

            matrizZonas[zona][contadores[zona]] = new Paquete<>(cod, peso, prioridadNum, valor, zona);
            contadores[zona]++;
        }

        System.out.println("=== 1. MATRIZ ORIGINAL POR ZONAS (ANTES DEL ORDENAMIENTO) ===");
        mostrarMatriz(matrizZonas);

        //ordenar paquetes (Usando Insertion en Zona 1 y QuickSort en Zona 2 y 3 para variar)
        System.out.println("\n=== 2. MATRIZ DESPUES DEL ORDENAMIENTO (MAYOR A MENOR PRIORIDAD) ===");
        Ordenador.insertionSort(matrizZonas[0]); // Zona 1 con Insertion
        Ordenador.quickSort(matrizZonas[1], 0, matrizZonas[1].length - 1); // Zona 2 con Quick
        Ordenador.quickSort(matrizZonas[2], 0, matrizZonas[2].length - 1); // Zona 3 con Quick
        
        mostrarMatriz(matrizZonas);

        //aplanar matriz para meter al camión
        Paquete<Integer>[] todosLosPaquetes = new Paquete[numZonas * maxPaquetesPorZona];
        int idx = 0;
        for (int i = 0; i < numZonas; i++) {
            for (int j = 0; j < maxPaquetesPorZona; j++) {
                if(matrizZonas[i][j] != null) {
                    todosLosPaquetes[idx++] = matrizZonas[i][j];
                }
            }
        }

        System.out.println("\n=== 3. SELECCION DE PAQUETES (CAPACIDAD CAMION: " + capacidadCamion + ") ===");
        
      
        int valorMaximo = OptimizadorCamion.mochilaRecursiva(todosLosPaquetes, capacidadCamion, todosLosPaquetes.length);
        System.out.println("Valorización máxima posible (Recursivo): " + valorMaximo);


        List<Paquete<?>> seleccionados = OptimizadorCamion.mochilaDP(todosLosPaquetes, capacidadCamion);
        System.out.println("\nPaquetes cargados en el camión (Programación Dinámica):");
        int pesoTotal = 0;
        int valorTotal = 0;
        for (Paquete<?> p : seleccionados) {
            System.out.println("- " + p.getCodigo() + " (Peso: " + p.getPeso() + ", Valor: " + p.getValorizacion() + ")");
            pesoTotal += p.getPeso();
            valorTotal += p.getValorizacion();
        }
        System.out.println("\nRESUMEN CAMIÓN -> Peso ocupado: " + pesoTotal + "/" + capacidadCamion + " | Valorización total: " + valorTotal);
    }

    private static void mostrarMatriz(Paquete<?>[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            System.out.print("Zona " + (i + 1) + ": ");
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] != null) {
                    System.out.print(matriz[i][j].getCodigo() + " ");
                }
            }
            System.out.println();
        }
    }
}
