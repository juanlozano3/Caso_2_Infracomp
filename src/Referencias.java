import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Referencias {

     int P, NF, NC, NR, NP;

     public Referencias(Imagen imagen, int P) {
          int lenMensaje = imagen.leerLongitud();
          this.P = P;
          this.NF = imagen.alto;
          this.NC = imagen.ancho;
          this.NR = 16 + (17 * lenMensaje);
          this.NP = (int) Math.ceil(((NF * NC * 3) + lenMensaje) / P);
     }

     public void generarReferencias() throws IOException {
          String rutaSalida = "../referencias/referencias.txt";
          BufferedWriter writer = new BufferedWriter(new FileWriter(rutaSalida));
          writer.write("P=" + P + "\n");
          writer.write("NF=" + NF + "\n");
          writer.write("NC=" + NC + "\n");
          writer.write("NR=" + NR + "\n");
          writer.write("NP=" + NP + "\n");

          // Escribir referencias

          writer.close();
          System.out.println("Archivo de lista de referencias generado en: " + rutaSalida);
     }
}
