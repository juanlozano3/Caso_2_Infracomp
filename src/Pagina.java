import java.util.ArrayList;
import java.util.HashMap;

public class Pagina {
     private int id;
     private ArrayList<int[]> direcciones;

     public Pagina(int[] clave) {
          this.id = clave[0];
          this.direcciones.add(clave);
     }

     public int getPuntaje(HashMap<int[], int[]> tabla_paginas){
          int puntaje = 0;
          for (int[] direccion : direcciones) {
               int[] valor_R_M = tabla_paginas.get(direccion);
               if (valor_R_M[0] == 0 && valor_R_M[1] == 1) puntaje++;
               else if (valor_R_M[0] == 1 && valor_R_M[1] == 0) puntaje+=2;
               else if (valor_R_M[0] == 1 && valor_R_M[1] == 1) puntaje+=3;
          }
          return puntaje;
     }

     public void addDireccion(int[] clave) {
          direcciones.add(clave);
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public ArrayList<int[]> getDirecciones() {
          return direcciones;
     }

     public void setDirecciones(ArrayList<int[]> direcciones) {
          this.direcciones = direcciones;
     }

     
}
