import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // Crear un objeto Scanner para leer desde la consola
        Scanner scanner = new Scanner(System.in);

        // Leer la ruta de archivo
        System.out.print("Digite la opción que desea: ");
        System.out.print("1) Generación de las referencias. ");
        System.out.print("2) Calcular datos buscados. ");
        System.out.print("3) Esconder mensaje. ");
        System.out.print("4) Recuperar mensaje. ");

        // Leer un número
        System.out.print("Ingrese un número: ");
        int opcion = scanner.nextInt();
        scanner.close();
        // Cerrar el scanner
        if (opcion == 1) {
            //Generar referencias
        }
        else if (opcion == 2) {
            //Calcular datos
        }
        else if (opcion == 3) {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            try {
                System.out.println("Nombre del archivo con la imagen a procesar: ");
                String ruta = br.readLine();
                Imagen imagen = new Imagen(ruta);
                System.out.println("Nombre del archivo con el mensaje a esconder: ");
                String ruta2 = br.readLine();
                char[] mensaje = leerArchivoTexto(ruta2);
                int longitud = mensaje.length;
                imagen.esconder(mensaje, longitud);
                imagen.escribirImagen("salida" + ruta);
                // Ud debería poder abrir el bitmap de salida en un editor de imágenes y no debe
                // percibir
                // ningún cambio en la imagen, pese a tener modificaciones por el mensaje que
                // esconde
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (opcion == 4) {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            try {
                System.out.println("Nombre de la imagen con el mensaje escondido: ");
                String ruta = br.readLine();
                System.out.println("Nombre del archivo para almacenar el mensaje recuperado: ");
                String salida = br.readLine();
                Imagen imagen = new Imagen(ruta);
                int longitud = imagen.leerLongitud();
                char[] mensaje = new char[longitud];
                imagen.recuperar(mensaje, longitud);
                FileWriter writer = new FileWriter(salida);
                writer.write(mensaje);
                writer.close();
                System.out.println("El mensaje ha sido recuperado y guardado en: " + salida);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Ingrese una opción válida");
        }

    }

    public static char[] leerArchivoTexto(String input) {
        StringBuilder contenido = new StringBuilder();

        try {
            FileInputStream fis = new FileInputStream(input);
            int byteLeido;
            while ((byteLeido = fis.read()) != -1) {
                contenido.append((char) byteLeido);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenido.toString().toCharArray();
    }

}
