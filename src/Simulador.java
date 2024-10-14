import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Simulador {


     private HashMap<int[], int[]> referencias = new HashMap<>();
     private int[][][] ram;
     public Simulador(String rutaReferencias, int marcosPagina) {
        referencias = new HashMap<>();
          int P = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rutaReferencias));
            String line;

            while ((line = reader.readLine()) != null) {
               if (line.startsWith("P")){ 
                    String[] partes = line.split("=");
                    P = Integer.parseInt(partes[1].trim());
                } 
               if (line.startsWith("Imagen") || line.startsWith("Mensaje")) {
                    // Procesar la línea
                    String[] partes = line.split(",");

                    // Obtener la página y el desplazamiento de la imagen
                    int pagina = Integer.parseInt(partes[1].trim());
                    int desplazamiento = Integer.parseInt(partes[2].trim());
                    int[] clave = new int[]{pagina, desplazamiento};  // Clave del HashMap
                    
                    int[] valor = new int[]{0, 0};  // Clave del HashMap

                    
                    // Insertar en el HashMap
                    referencias.put(clave, valor);
                }
            }

            
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //imprimirPrimeras17Claves();

        //estructura de marcos de página
        // lista de listas de arreglos de tamaño 2
        ram = new int[marcosPagina][P][2];
    }

     public void simular() {
          // Usar Threads
          // S: misses, hits y tiempos (normal, todo RAM, todo SWAP)
          // 1 seg = 100 misses o 40,000,000 hits
          // 1 ms = 1,000,000 ns
          // 1 hit = 25 ns, 1 miss = 10,000,000 ns
          // 1 miss = 400,000 hits

          
          

          
     }


     public void imprimirPrimeras17Claves() {
        int contador = 0;
        for (Map.Entry<int[], int[]> entry : referencias.entrySet()) {
            System.out.println("Clave: Página " + entry.getKey()[0] + ", Desplazamiento " + entry.getKey()[1]);
            System.out.println("Valor: [" + entry.getValue()[0] + ", " + entry.getValue()[1] + "]");
            contador++;
            if (contador >= 17) {
                break;
            }
        }
        System.out.println(referencias.size());
    }
}
