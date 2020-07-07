
package Jcombox;


public class categoria {
    private Integer idcategoria;
    private String descripcion;
    public categoria(){
        super();
    }

     public categoria(Integer codigo, String descripcion){
            this.idcategoria = codigo;
            this.descripcion = descripcion;
        }
      public Integer getCodigo() {
        return idcategoria;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCodigo(Integer codigo) {
        this.idcategoria = codigo;
    }
    
    @Override
    public String toString() {
        return idcategoria+"-"+descripcion;
    }

    @Override
    public boolean equals(Object obj) {
         Integer cod1=this.getCodigo();
        //hacemos un cast - por que entrando un objeto
        Integer cod2 =((categoria) obj).getCodigo();
        if(cod1.equals(cod2)){
            return true;
        }else{
            return false;
        }
    } 
}
