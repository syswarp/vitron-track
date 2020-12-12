package com.syswarp.data.entity;

import javax.persistence.Entity;

import com.syswarp.data.AbstractEntity;

@Entity
public class Contenedores extends AbstractEntity {

    private Integer idcontenedor;
    private String contenedor;
    private String tagid;

    public Integer getIdcontenedor() {
        return idcontenedor;
    }
    public void setIdcontenedor(Integer idcontenedor) {
        this.idcontenedor = idcontenedor;
    }
    public String getContenedor() {
        return contenedor;
    }
    public void setContenedor(String contenedor) {
        this.contenedor = contenedor;
    }
    public String getTagid() {
        return tagid;
    }
    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

}
