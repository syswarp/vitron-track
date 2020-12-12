package com.syswarp.data.entity;

import javax.persistence.Entity;

import com.syswarp.data.AbstractEntity;

@Entity
public class Medios extends AbstractEntity {

    private Integer idmedio;
    private String medio;

    public Integer getIdmedio() {
        return idmedio;
    }
    public void setIdmedio(Integer idmedio) {
        this.idmedio = idmedio;
    }
    public String getMedio() {
        return medio;
    }
    public void setMedio(String medio) {
        this.medio = medio;
    }

}
