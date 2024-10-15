import java.util.HashMap;

public class RAM {

     private Pagina[] marcos;
     private HashMap<int[], int[]> tabla_paginas = new HashMap<>();

     public RAM(int cantidadMarcos) {
          this.marcos = new Pagina[cantidadMarcos];
          for (int i = 0; i < cantidadMarcos; i++) {
               int[] clave = { -1, 0 };
               Pagina pagina = new Pagina(clave);
               this.marcos[i] = pagina;
          }
     }

     public Pagina[] getMarcos() {
          return marcos;
     }

     public void setMarcos(Pagina[] marcos) {
          this.marcos = marcos;
     }

     // Estor etorna el ID de la pagina que se va a sacar de RAM para meter la nueva
     public int LRU() {
          int idLRU = -1;
          int puntajeLRU = Integer.MAX_VALUE;
          for (Pagina pagina : marcos) {
               int puntaje = pagina.getPuntaje(tabla_paginas);
               if (puntaje < puntajeLRU) {
                    puntajeLRU = puntaje;
                    idLRU = pagina.getId();
               }
          }
          return idLRU;
     }

     public void actualizarBit(String bit, int[] clave) {
          if (bit == "R") this.tabla_paginas.get(clave)[0] = 1;
          else this.tabla_paginas.get(clave)[1] = 1;
     }

     public void cargarClave(int[] clave) {
          this.tabla_paginas.put(clave, new int[] { 0, 0 });
     }

}
