package com.syswarp.data.entity;

import javax.persistence.Entity;

import com.syswarp.data.AbstractEntity;

@Entity
public class Multiplicaciones extends AbstractEntity {

    private Integer idmultiplicacion;
    private Integer idmultiplicacion_padre;

    public Integer getIdmultiplicacion() {
        return idmultiplicacion;
    }
    public void setIdmultiplicacion(Integer idmultiplicacion) {
        this.idmultiplicacion = idmultiplicacion;
    }
    public Integer getIdmultiplicacion_padre() {
        return idmultiplicacion_padre;
    }
    public void setIdmultiplicacion_padre(Integer idmultiplicacion_padre) {
        this.idmultiplicacion_padre = idmultiplicacion_padre;
    }

}
