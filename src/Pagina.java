import java.util.ArrayList;
import java.util.HashMap;

public class Pagina {
     private String id;
     private ArrayList<String> direcciones;

     public Pagina(String clave) {
          this.direcciones = new ArrayList<>();
          String[] partes = clave.split(" ");
          this.id = partes[0];
          this.direcciones.add(clave);
     }

     public int getPuntaje(HashMap<String, ArrayList<String>> tabla_paginas) {
          int puntaje = 0;
          for (String direccion : direcciones) {
              ArrayList<String> valores_R_M = tabla_paginas.get(direccion);
              if (valores_R_M != null) {  // Asegúrate de que valores_R_M no sea null
                  if (valores_R_M.get(0).equals("0") && valores_R_M.get(1).equals("1")) {
                      puntaje++;
                  } else if (valores_R_M.get(0).equals("1") && valores_R_M.get(1).equals("0")) {
                      puntaje += 2;
                  } else if (valores_R_M.get(0).equals("1") && valores_R_M.get(1).equals("1")) {
                      puntaje += 3;
                  }
              } else {
                  System.err.println("Error: valores_R_M es null para la dirección " + direccion);
              }
          }
          return puntaje;
      }
      

     public void addDireccion(String clave) {
          direcciones.add(clave);
     }

     public String getId() {
          return id;
     }

     public ArrayList<String> getDirecciones() {
          return direcciones;
     }

     public void setDirecciones(ArrayList<String> direcciones) {
          this.direcciones = direcciones;
     }

}
