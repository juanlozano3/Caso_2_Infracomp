import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Referencias {

     int P, NF, NC, NR, NP;
     Imagen imagen;
     public Referencias(Imagen imagen, int P) {
          /*
           * P: Tamaño de página(en bytes)
           * NF y NC: Número de filas y columnas de la imagen
           * NR: Número de referencias (en el archivo)
           * NP: Número de páginas virtuales (las páginas necesarias para almacenar la
           * matriz imagen y el vector resultante)
           */
          /*
           * P: parametro
           * NF y NC: sacados de imagen
           * NR: 16 + 17*lenMensaje
           * NP: ceiling( (anch*alto*3 + lenMensaje) / P )
           */
          int lenMensaje = imagen.leerLongitud();
          this.P = P;
          this.NF = imagen.alto;
          this.NC = imagen.ancho;
          this.NR = 16 + (17 * lenMensaje);
          this.NP = (int) Math.ceil(((NF * NC * 3) + lenMensaje) / P);
          this.imagen = imagen;
     }

     public void generarReferencias() throws IOException {
          String rutaSalida = "../referencias/referencias.txt";
          BufferedWriter writer = new BufferedWriter(new FileWriter(rutaSalida));
          writer.write("P=" + P + "\n");
          writer.write("NF=" + NF + "\n");
          writer.write("NC=" + NC + "\n");
          writer.write("NR=" + NR + "\n");
          writer.write("NP=" + NP + "\n");
          int pagina_imagen = 0;
          int desplazamiento_imagen = 0;
          // Escribir referencias
          int pagina_vector = 0;
          int desplazamiento_vector = 0;
          for (int i = 0; i < imagen.leerLongitud(); i++) {
               
               writer.write("\n");
          }

          writer.close();
          System.out.println("Archivo de lista de referencias generado en: " + rutaSalida);
     }
}
