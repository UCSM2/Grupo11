public class Ordenador {
    public static <T extends Comparable<T>> void insertionSort(Paquete<T>[] arreglo) {
        for (int i = 1; i < arreglo.length; i++) {
            if (arreglo[i] == null) continue;
            Paquete<T> actual = arreglo[i];
            int j = i - 1;
            while (j >= 0 && arreglo[j] != null && arreglo[j].compareTo(actual) > 0) {
                arreglo[j + 1] = arreglo[j];
                j--;
            }
            arreglo[j + 1] = actual;
        }
    }
    public static <T extends Comparable<T>> void quickSort(Paquete<T>[] arreglo, int inicio, int fin) {
        if (inicio < fin) {
            int indiceParticion = particion(arreglo, inicio, fin);
            quickSort(arreglo, inicio, indiceParticion - 1);
            quickSort(arreglo, indiceParticion + 1, fin);
        }
    }
    private static <T extends Comparable<T>> int particion(Paquete<T>[] arreglo, int inicio, int fin) {
        Paquete<T> pivote = arreglo[fin];
        int i = (inicio - 1);
        for (int j = inicio; j < fin; j++) {
            if (arreglo[j] != null && pivote != null && arreglo[j].compareTo(pivote) <= 0) {
                i++;
                Paquete<T> temp = arreglo[i];
                arreglo[i] = arreglo[j];
                arreglo[j] = temp;
            }
        }
        Paquete<T> temp = arreglo[i + 1];
        arreglo[i + 1] = arreglo[fin];
        arreglo[fin] = temp;
        return i + 1;
    }
}