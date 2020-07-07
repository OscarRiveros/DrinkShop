
package Jcombox;


public class marca {
    private Integer idmarca;
    private String descripcion;
    public marca(){
        super();
    }
        public marca(Integer codigo, String descripcion){
            this.idmarca = codigo;
            this.descripcion = descripcion;
        }

    public Integer getCodigo() {
        return idmarca;
    }

    public String getDescripccion() {
        return descripcion;
    }

    public void setCodigo(Integer codigo) {
        this.idmarca = codigo;
    }

    public void setDescripccion(String descripcion) {
        this.descripcion = descripcion;
    }
    @Override
    public String toString() {
        return idmarca+"-"+descripcion;
    }

    @Override
    public boolean equals(Object obj) {
         Integer cod1=this.getCodigo();
        //hacemos un cast - por que entrando un objeto
        Integer cod2 =((marca) obj).getCodigo();
        if(cod1.equals(cod2)){
            return true;
        }else{
            return false;
        }
    } 
}

