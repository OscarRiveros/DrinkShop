
package Jcombox;


public class procedencia {
    private Integer idprocedencia;
    private String descripcion;
    public procedencia(){
        super();
    }
    public procedencia(Integer codigo , String descripcion){
        this.idprocedencia = codigo;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCodigo() {
        return idprocedencia;
    }

    public void setCodigo(Integer idprocedecnia) {
        this.idprocedencia = idprocedecnia;
    }
    
     @Override
    public String toString() {
        return idprocedencia+"-"+descripcion;
    }
    
    
    @Override
    public boolean equals(Object obj) {
         Integer cod1=this.getCodigo();
        //hacemos un cast - por que entrando un objeto
        Integer cod2 =((procedencia) obj).getCodigo();
        if(cod1.equals(cod2)){
            return true;
        }else{
            return false;
        }
    }
}
