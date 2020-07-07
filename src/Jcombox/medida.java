/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jcombox;

/**
 *
 * @author USER
 */
public class medida {
     private Integer idmedida;
     private String descripcion;
    public medida(){
        super();
    }
    public medida(Integer codigo, String descripcion){
            this.idmedida = codigo;
            this.descripcion = descripcion;
        }

    public Integer getCodigo() {
        return idmedida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setCodigo(Integer codigo) {
        this.idmedida = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
     public String toString() {
        return idmedida+"-"+descripcion;
    }

    @Override
    public boolean equals(Object obj) {
         Integer cod1=this.getCodigo();
        //hacemos un cast - por que entrando un objeto
        Integer cod2 =((medida) obj).getCodigo();
        if(cod1.equals(cod2)){
            return true;
        }else{
            return false;
        }
    }
    
}
