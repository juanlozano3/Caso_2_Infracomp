import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite el número de la opción que desea: ");
        System.out.print("1) Generación de las referencias. ");
        System.out.print("2) Calcular datos buscados. ");
        System.out.print("3) Esconder mensaje. ");
        System.out.print("4) Recuperar mensaje. ");
        System.out.print("Ingrese un número: ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea después de leer el número

        // len(mensaje) = chars + cantLineas
        // 1) Generación de las referencias.
        if (opcion == 1) { 
            // E: tamanio pagina y ruta imagen encriptada 
            // S: archivo de referencias
            try {
                System.out.println("Nombre del archivo en imagenes/procesadas/: ");
                String ruta = "imagenes/procesadas/" + scanner.nextLine();
                Imagen imagen = new Imagen(ruta);
                
                System.out.println("Ingrese el tamaño en bytes de las páginas: ");
                int P = Integer.parseInt(scanner.nextLine());

                Referencias referencias = new Referencias(imagen, P);
                referencias.generarReferencias();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // 2) Calcular datos buscados.
        else if (opcion == 2) { 
            try {
                System.out.println("Nombre del archivo en referencias/: ");
                String ruta = "referencias/" + scanner.nextLine();
                System.out.println("Ingrese el número de marcos página: ");
                int M = Integer.parseInt(scanner.nextLine());
                Simulador simulador = new Simulador(ruta, M);
                
                

                simulador.simular();
            } catch (Exception e) {
                e.printStackTrace();
            }

        // 3) Esconder mensaje.
        } else if (opcion == 3) {
            try {
                System.out.println("Nombre del archivo con la imagen en imagenes/originales/: ");
                String ruta = "imagenes/originales/" + scanner.nextLine();
                Imagen imagen = new Imagen(ruta);

                System.out.println("Nombre del archivo con el mensaje a esconder en mensajes/esconder/: ");
                String ruta2 = "mensajes/esconder/" + scanner.nextLine();
                char[] mensaje = leerArchivoTexto(ruta2);
                int longitud = mensaje.length;

                imagen.esconder(mensaje, longitud);
                String rutaSalida = "imagenes/procesadas/salida_" + ruta.substring(ruta.lastIndexOf("/") + 1);
                imagen.escribirImagen(rutaSalida);
                System.out.println("Imagen modificada guardada en: " + rutaSalida);
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        // 4) Recuperar mensaje.
        } else if (opcion == 4) {
            try {
                System.out.println("Nombre de la imagen con el mensaje escondido en imagenes/procesadas/: ");
                String ruta = "imagenes/procesadas/" + scanner.nextLine();
                System.out.println("Nombre del archivo para almacenar el mensaje recuperado en mensajes/recuperados/: ");
                String salida = "mensajes/recuperados/" + scanner.nextLine();

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

        // No cerramos `System.in` o `scanner` ya que se utiliza durante toda la ejecución.
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
