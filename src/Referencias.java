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
          System.out.println("Largo del mensaje: ");
          System.out.println(lenMensaje);
     }

     public void generarReferencias() throws IOException {
          String rutaSalida = "referencias/referencias.txt";
          BufferedWriter writer = new BufferedWriter(new FileWriter(rutaSalida));
          writer.write("P=" + P + "\n");
          writer.write("NF=" + NF + "\n");
          writer.write("NC=" + NC + "\n");
          writer.write("NR=" + NR + "\n");
          writer.write("NP=" + NP + "\n");
          int pagina_imagen = 0;
          int desplazamiento_imagen = 0;
          // Escribir referencias
          int bytes_imagen = NF * NC * 3;
          int pagina_vector = bytes_imagen / P;
          int desplazamiento_vector = bytes_imagen % P;
          int fila = 0;
          int columna = 0;
          int rgb = 0;
          String color;
          for (int i = 0; i < 16; i++) {
               String linea = "";
               if (rgb == 0)
                    color = "R";
               else if (rgb == 1)
                    color = "G";
               else
                    color = "B";
               linea += "Imagen[" + fila + "][" + columna + "]." + color + "," + pagina_imagen + ","
                         + desplazamiento_imagen + ",R\n";
               writer.write(linea);
               rgb++;
               if (rgb > 2) {
                    columna++;
                    rgb = 0;
               }
               if (columna >= imagen.ancho) {
                    columna = 0;
                    fila++;
               }
               desplazamiento_imagen++;
               if (desplazamiento_imagen >= P) {
                    desplazamiento_imagen = 0;
                    pagina_imagen++;
               }
          }
          int posicion_vector = 0;
          for (int i = 0; i < imagen.leerLongitud(); i++) {
               // acceso inicial
               String linea = "Mensaje[" + posicion_vector + "]," + pagina_vector + "," + desplazamiento_vector
                         + ",W\n";
               writer.write(linea);
               for (int j = 0; j < 8; j++) {
                    linea = "";
                    // acceso a imagen (Lectura)
                    if (rgb == 0)
                         color = "R";
                    else if (rgb == 1)
                         color = "G";
                    else
                         color = "B";
                    linea = "Imagen[" + fila + "][" + columna + "]." + color + "," + pagina_imagen + ","
                              + desplazamiento_imagen + ",R\n";
                    writer.write(linea);
                    linea = "";
                    // acceso a vector (escritura)
                    linea = "Mensaje[" + posicion_vector + "]," + pagina_vector + "," + desplazamiento_vector + ",W\n";
                    writer.write(linea);

                    rgb++;
                    if (rgb > 2) {
                         columna++;
                         rgb = 0;
                    }

                    if (columna >= imagen.ancho) {
                         columna = 0;
                         fila++;
                    }
                    desplazamiento_imagen++;
                    if (desplazamiento_imagen >= P) {
                         desplazamiento_imagen = 0;
                         pagina_imagen++;
                    }
                    desplazamiento_vector++;
                    if (desplazamiento_vector >= P) {
                         desplazamiento_vector = 0;
                         pagina_vector++;
                    }

               }

               posicion_vector++;
          }

          writer.close();
          System.out.println("Archivo de lista de referencias generado en: " + rutaSalida);
     }
}
