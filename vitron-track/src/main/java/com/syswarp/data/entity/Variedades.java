package com.syswarp.data.entity;

import javax.persistence.Entity;

import com.syswarp.data.AbstractEntity;

@Entity
public class Variedades extends AbstractEntity {

    private Integer idvariedad;
    private String variedad;

    public Integer getIdvariedad() {
        return idvariedad;
    }
    public void setIdvariedad(Integer idvariedad) {
        this.idvariedad = idvariedad;
    }
    public String getVariedad() {
        return variedad;
    }
    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

}
