import java.io.FileInputStream;
import java.io.IOException;

public class Simulador {

     public Simulador(String rutaReferencias) {
          try {
               FileInputStream fis = new FileInputStream(rutaReferencias);
               // leer y guardar referencias
               // fis.read() y fis.skip()
               fis.close();
          } catch (IOException e) {
               e.printStackTrace();
          }
     }

     public void simular(int M) {
          // Usar Threads
          // 1 seg = 100 misses o 40,000,000 hits
          // 1 ms = 1,000,000 ns
          // 1 hit = 25 ns, 1 miss = 10,000,000 ns
          // 1 miss = 400,000 hits
     }
}
