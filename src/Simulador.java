import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;

public class Simulador {

    private RAM ram;
    private ArrayList<String[]> referencias = new ArrayList<>();
    private int hits, misses;

    public Simulador(String rutaReferencias, int marcosPagina) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rutaReferencias));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Imagen") || line.startsWith("Mensaje")) {
                    // Procesar la línea
                    String[] partes = line.split(",");
                    referencias.add(partes);
                    // Obtener la página y el desplazamiento de la imagen
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.ram = new RAM(marcosPagina);
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

                    // actualizar el bit (R) en la tabla pagina
                    // R--M
                    // esto es sincronizado
                    cargarEnRAM(clave);

                    ram.actualizarBit("R", clave);
                    if (referencia[0].trim() == "Mensaje")
                        ram.actualizarBit("M", clave);

                    // Esto no es sincornizado

                    try {
                        // Simulación del tiempo de procesamiento
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                // Poner en 0,0 todos los valores de todas las referencias de la tabla_paginas
            }
        });

        // Iniciar los threads
        thread1.start();
        thread2.start();
        // Join para retornar total de hit y misses
    }

    public void cargarEnRAM(int[] clave) {
        ram.cargarClave(clave);
        boolean hit = false;
        for (Pagina pagina : ram.getMarcos()) {
            if (pagina.getId() == clave[0]) {
                hit = true;
                hits++;
                pagina.addDireccion(clave);
            }
        }
        if (!hit) {
            misses++;
            int idPaginaEliminada = ram.LRU();
            for (Pagina pagina : ram.getMarcos()) {
                if (pagina.getId() == idPaginaEliminada) {
                    Pagina nuevaPagina = new Pagina(clave);
                    pagina = nuevaPagina;
                }
            }
        }
    }

}
