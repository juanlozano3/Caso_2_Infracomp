import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

public class Simulador {

    private RAM ram;
    private ArrayList<ArrayList<String>> referencias = new ArrayList<>();
    private int hits, misses;

    public Simulador(String rutaReferencias, int marcosPagina) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rutaReferencias));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Imagen") || line.startsWith("Mensaje")) {
                    // Procesar la línea
                    // System.out.println(line);
                    String[] partesArray = line.split(","); // split devuelve un array de String
                    ArrayList<String> partes = new ArrayList<>(Arrays.asList(partesArray));
                    referencias.add(partes);
                    // System.out.println(partes.toString());
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.ram = new RAM(marcosPagina);
    }

    public void simular() {
        // Crear dos threads para realizar las operaciones concurrentes

        // Thread 1 - Simula los accesos a las referencias y actualiza el estado de la
        // RAM
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                int porcentajeImpreso = 0;
                System.out.println("Progreso: 0%");
                for (ArrayList<String> referencia : referencias) {
                    int porcentajeActual = (int) ((count * 100.0) / referencias.size());
                    if (porcentajeActual >= porcentajeImpreso + 5) {
                        System.out.println("Progreso: " + porcentajeActual + "%");
                        porcentajeImpreso += 5; // Actualizar el porcentaje impreso
                    }
                    // Obtener la página y el desplazamiento de la imagen
                    String pagina = referencia.get(1).trim();
                    String desplazamiento = referencia.get(2).trim();
                    String clave = "" + pagina + " " + desplazamiento; // Clave del HashMap
                    // System.out.println("GENERADAAAA: " + clave);
                    synchronized (ram) { // Sincronizar sobre la instancia de RAM
                        // Actualizar el bit R en la tabla de páginas
                        ram.cargarClave(clave); // Cargar clave en RAM
                        ram.actualizarBit("R", clave); // Actualizar bit R
                        calcularHitMiss(clave);

                        // Si es un mensaje, también actualizamos el bit M
                        if (referencia.get(0).trim().contains("Mensaje")) {
                            ram.actualizarBit("M", clave);
                        }
                    }

                    count++;
                    try {
                        Thread.sleep(1); // Simulación de tiempo de procesamiento
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Thread 2 - Reinicia los bits de todas las páginas en la tabla, y corre
        // indefinidamente
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (thread1.isAlive()) { // Mantener corriendo mientras Thread 1 esté vivo
                    synchronized (ram) { // Sincronizar sobre la instancia de RAM
                        ram.reiniciarBits(); // Reiniciar bits en RAM
                    }
                    try {
                        Thread.sleep(2); // Simulación de tiempo de procesamiento
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Iniciar los threads
        thread1.start();
        thread2.start();

        // Esperar a que ambos threads terminen
        try {
            thread1.join(); // Espera a que Thread 1 termine
            thread2.join(); // Espera a que Thread 2 termine (finaliza automáticamente tras Thread 1)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Llamar a calcularTiempos() después de que ambos hilos han terminado
        System.out.println("Progreso: 100%");
        calcularTiempos();
    }

    // S: misses, hits y tiempos (normal, todo RAM, todo SWAP)
    // 1 seg = 100 misses o 40,000,000 hits
    // 1 ms = 1,000,000 ns
    // 1 hit = 25 ns, 1 miss = 10,000,000 ns
    // 1 miss = 400,000 hits
    public void calcularTiempos() {
        int totalRefs = referencias.size();
        System.out.println("Total de direcciones procesadas: " + totalRefs);
        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
        double percentage = ((double) hits / (hits + misses)) * 100;
        System.out.println("Porcentaje de hits: " + Math.round(percentage * 100.0) / 100.0 + "%");
        double tiempoPorHitNs = 25; // 25 ns por hit
        double tiempoPorMissNs = 10000000; // 10,000,000 ns por miss
        double totalTiempoNs = (hits * tiempoPorHitNs) + (misses * tiempoPorMissNs);
        double totalTiempoMs = totalTiempoNs / 1000000.0;
        System.out.println("Tiempo de la simulación en milisegundos: " + Math.round(totalTiempoMs * 100.0) / 100.0);
        double tiempoTotalHitsNs = totalRefs * tiempoPorHitNs;
        double tiempoTotalHitsMs = Math.round((tiempoTotalHitsNs / 1000000.0) * 100.0) / 100.0;
        double tiempoTotalMissesNs = totalRefs * tiempoPorMissNs;
        double tiempoTotalMissesMs = Math.round((tiempoTotalMissesNs / 1000000.0) * 100.0) / 100.0;
        System.out.println("Tiempo en milisegundos si todas las referencias fueran hits: " + tiempoTotalHitsMs);
        System.out.println("Tiempo en milisegundos si todas las referencias fueran misses: " + tiempoTotalMissesMs);
    }

    public void calcularHitMiss(String clave) {
        //System.out.println("BUSCADA: " + clave);
        String[] partes = clave.split(" ");
        boolean hit = false;
        for (Pagina pagina : ram.getMarcos()) {
            //System.out.println("ID: " + pagina.getId() + " vs " + partes[0]);
            if (pagina.getId().equals(partes[0])) {
                hit = true;
                hits++;
                pagina.addDireccion(clave);
            }
        }
        if (!hit) {
            misses++;
            String idPaginaEliminada = ram.LRU();
            for (Pagina pagina : ram.getMarcos()) {
                if (pagina.getId() == idPaginaEliminada) {
                    // System.out.println("COINCIDENCIAAAAAAAAAAAAAAAAAA " + clave);
                    Pagina nuevaPagina = new Pagina(clave);
                    // System.out.println("El de la nueva: " + nuevaPagina.getId());
                    ram.remplacePagina(pagina, nuevaPagina);
                }
            }
        }
    }

}
