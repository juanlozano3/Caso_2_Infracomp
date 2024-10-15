public class RAM {
     
     private Pagina[] marcos;
     
     public RAM(int cantidadMarcos) {
          this.marcos = new Pagina[cantidadMarcos];
          for (int i = 0 ; i < cantidadMarcos ; i++) {
               int[] clave = {-1, 0}; 
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
     
}
