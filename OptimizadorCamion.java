import java.util.ArrayList;
import java.util.List;

public class OptimizadorCamion {

    //recursividad
    public static int mochilaRecursiva(Paquete<?>[] paquetes, int capacidad, int n) {
        if (n == 0 || capacidad == 0) {
            return 0;
        }
        if (paquetes[n - 1] == null || paquetes[n - 1].getPeso() > capacidad) {
            return mochilaRecursiva(paquetes, capacidad, n - 1);
        } else {
            int incluir = paquetes[n - 1].getValorizacion() + mochilaRecursiva(paquetes, capacidad - paquetes[n - 1].getPeso(), n - 1);
            int noIncluir = mochilaRecursiva(paquetes, capacidad, n - 1);
            return Math.max(incluir, noIncluir);
        }
    }

    public static List<Paquete<?>> mochilaDP(Paquete<?>[] paquetes, int capacidad) {
        int n = paquetes.length;
        int[][] dp = new int[n + 1][capacidad + 1];

        //llenamos la tabla DP
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacidad; w++) {
                if (paquetes[i - 1] == null) {
                    dp[i][w] = dp[i - 1][w];
                    continue;
                }
                int pesoActual = paquetes[i - 1].getPeso();
                int valorActual = paquetes[i - 1].getValorizacion();

                if (pesoActual <= w) {
                    dp[i][w] = Math.max(valorActual + dp[i - 1][w - pesoActual], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        //rastreamos cuáles paquetes se seleccionaron
        List<Paquete<?>> seleccionados = new ArrayList<>();
        int w = capacidad;
        for (int i = n; i > 0 && dp[i][w] > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                seleccionados.add(paquetes[i - 1]);
                w -= paquetes[i - 1].getPeso();
            }
        }
        return seleccionados;
    }
}