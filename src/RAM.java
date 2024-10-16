import java.util.HashMap;
import java.util.ArrayList;

public class RAM {

     private ArrayList<Pagina> marcos;
     private HashMap<String, ArrayList<String>> tabla_paginas = new HashMap<>();

     public RAM(int cantidadMarcos) {
          this.marcos = new ArrayList<>();
          for (int i = 0; i < cantidadMarcos; i++) {
               String clave = "-1 -1";
               Pagina pagina = new Pagina(clave);
               this.marcos.add(pagina);
               ArrayList<String> valor = new ArrayList<>();
               valor.add("0");
               valor.add("0");
               this.tabla_paginas.put(clave, valor);
          }
     }

     public ArrayList<Pagina> getMarcos() {
          return marcos;
     }

     public void setMarcos(ArrayList<Pagina> marcos) {
          this.marcos = marcos;
     }

     // Estor etorna el ID de la pagina que se va a sacar de RAM para meter la nueva
     public String LRU() {
          String idLRU = "-2";
          int puntajeLRU = Integer.MAX_VALUE;
          for (Pagina pagina : marcos) {
               int puntaje = pagina.getPuntaje(tabla_paginas);
               System.out.println("El puntaje de: " + pagina.getId() + " es: " + puntaje);
               if (puntaje < puntajeLRU) {
                    puntajeLRU = puntaje;
                    idLRU = pagina.getId();
               }
          }
          System.out.println("A REMOVER: " + idLRU + " Por Puntaje: " + puntajeLRU);
          return idLRU;
     }

     public void remplacePagina(Pagina antigua, Pagina nueva) {
          for (int i = 0; i < marcos.size(); i++) {
              if (marcos.get(i).equals(antigua)) {
                  marcos.set(i, nueva);  // Reemplaza la página antigua con la nueva
                  break;  // Opcional: Si solo quieres reemplazar la primera coincidencia
              }
          }
      }
      

     public void actualizarBit(String bit, String clave) {
          // Obtener el valor asociado a la clave
          ArrayList<String> valor = this.tabla_paginas.get(clave);
          //System.out.println("AQUI: " + valor);
          // Verificar que el valor no sea null
          if (valor != null) {
               //System.out.println("BIT: " + bit + " - " + bit.equals("R"));
               if (bit.equals("R")) {
                    valor.set(0, "1"); // Modificar el bit "R"
               } else {
                    valor.set(1, "1"); // Modificar el bit "M"
               }

               // Guardar de vuelta el array modificado (aunque es in-place, no está de más
               // asegurarse)
               this.tabla_paginas.put(clave, valor);
               //System.out.println("ALLA: " + tabla_paginas.get(clave));
          } else {
               System.err.println("Error: clave no encontrada en tabla_paginas");
          }
     }

     public void cargarClave(String clave) {
          ArrayList<String> valor = new ArrayList<>();
          valor.add("0");
          valor.add("0");
          this.tabla_paginas.put(clave, valor);
          System.out.println("CLAVE PUESTA:" + clave + " - " + tabla_paginas.get(clave));
     }

     public void reiniciarBits() {
          //System.out.println("reiniciando");
          for (Pagina pagina : marcos) {
              ArrayList<String> claves = pagina.getDirecciones();
              for (String clave : claves) {
                  ArrayList<String> valor = tabla_paginas.get(clave);
                  if (valor != null) {  // Asegúrate de que el valor no sea null
                      valor.set(0, "0");
                      valor.set(1, "0");
                  } else {
                      System.err.println("Error: valor es null para la clave " + clave);
                  }
              }
          }
      }
      
}
