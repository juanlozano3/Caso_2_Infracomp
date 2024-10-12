import java.io.FileInputStream;
import java.io.IOException;

public class Simulador {

     public Simulador(String rutaReferencias) {
          try {
               FileInputStream fis = new FileInputStream(rutaReferencias);
               //leer y guardar referencias
               //fis.read() y fis.skip()
               fis.close();
          } catch (IOException e) {
               e.printStackTrace();
          }
     }

     public void simular (int M) {
          // Usar Threads
     }
}
