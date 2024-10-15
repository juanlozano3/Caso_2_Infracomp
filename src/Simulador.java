import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Simulador {

    private HashMap<int[], int[]> tabla_paginas = new HashMap<>();
    private int[][][] ram;
    private ArrayList<String[]> referencias = new ArrayList<>();

    public Simulador(String rutaReferencias, int marcosPagina) {
        tabla_paginas = new HashMap<>();
        int P = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rutaReferencias));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("P")) {
                    String[] partes = line.split("=");
                    P = Integer.parseInt(partes[1].trim());
                }
                if (line.startsWith("Imagen") || line.startsWith("Mensaje")) {
                    // Procesar la línea
                    String[] partes = line.split(",");
                    referencias.add(partes);
                    // Obtener la página y el desplazamiento de la imagen
                    int pagina = Integer.parseInt(partes[1].trim());
                    int desplazamiento = Integer.parseInt(partes[2].trim());
                    int[] clave = new int[] { pagina, desplazamiento }; // Clave del HashMap

                    int[] valor = new int[] { 0, 0 }; // Clave del HashMap

                    // Insertar en el HashMap
                    tabla_paginas.put(clave, valor);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // imprimirPrimeras17Claves();

        // estructura de marcos de página
        // lista de listas de arreglos de tamaño 2
        ram = new int[marcosPagina][P][2];
    }

    // Usar Threads
    // S: misses, hits y tiempos (normal, todo RAM, todo SWAP)
    // 1 seg = 100 misses o 40,000,000 hits
    // 1 ms = 1,000,000 ns
    // 1 hit = 25 ns, 1 miss = 10,000,000 ns
    // 1 miss = 400,000 hits
    public void simular() {
        // Crear dos threads para realizar las operaciones concurrentes

        // Thread 1 - Simula los accesos a las referencias y actualiza el estado de la
        // RAM
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (String[] referencia : referencias) {
                    // Obtener la página y el desplazamiento de la imagen
                    int pagina = Integer.parseInt(referencia[1].trim());
                    int desplazamiento = Integer.parseInt(referencia[2].trim());
                    int[] clave = new int[] { pagina, desplazamiento }; // Clave del HashMap
                    
                    //actualizar el bit (R) en la tabla pagina
                    // R--M
                    //esto es sincronizado
                    tabla_paginas.get(clave)[0] = 1;


                    // Esto no es sincornizado
    


                }

            }
        });

        // Thread 2 - Realiza algún tipo de monitoreo o actualización adicional (p. ej.,
        // envejecimiento de las páginas)
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                // Simulación de alguna operación periódica sobre la RAM o referencias
                while (true) {
                    synchronized (ram) {
                        // Realizar alguna operación periódica o de mantenimiento
                        System.out.println("Thread 2 realizando operación de mantenimiento.");
                    }
                    try {
                        // Simulación del tiempo de procesamiento
                        Thread.sleep(2); // Simular un tiempo de procesamiento (ejemplo)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Iniciar los threads
        thread1.start();
        thread2.start();
    }

    public void imprimirPrimeras17Claves() {
        int contador = 0;
        for (Map.Entry<int[], int[]> entry : tabla_paginas.entrySet()) {
            System.out.println("Clave: Página " + entry.getKey()[0] + ", Desplazamiento " + entry.getKey()[1]);
            System.out.println("Valor: [" + entry.getValue()[0] + ", " + entry.getValue()[1] + "]");
            contador++;
            if (contador >= 17) {
                break;
            }
        }
        System.out.println(tabla_paginas.size());
    }
}
