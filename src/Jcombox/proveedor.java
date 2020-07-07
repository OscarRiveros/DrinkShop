
package Jcombox;


public class proveedor {
    private Integer idproveedor;
    private String nombre;
    public proveedor(){
        super();
    }
    public proveedor(Integer codigo , String nombre){
        this.idproveedor = codigo;
        this.nombre = nombre;
    }

    public Integer getCodigo() {
        return idproveedor;
    }

    public void setCodigo(Integer codigo) {
        this.idproveedor = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
       @Override
    public String toString() {
        return idproveedor+"-"+nombre;
    }

    @Override
    public boolean equals(Object obj) {
         Integer cod1=this.getCodigo();
        //hacemos un cast - por que entrando un objeto
        Integer cod2 =((proveedor) obj).getCodigo();
        if(cod1.equals(cod2)){
            return true;
        }else{
            return false;
        }
    } 
    
    
    
}
