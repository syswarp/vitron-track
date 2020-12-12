package com.syswarp.data.entity;

import javax.persistence.Entity;

import com.syswarp.data.AbstractEntity;

@Entity
public class Operarios extends AbstractEntity {

    private Integer idoperario;
    private String operario;
    private String usuario;
    private String clave;
    private String esadmin;

    public Integer getIdoperario() {
        return idoperario;
    }
    public void setIdoperario(Integer idoperario) {
        this.idoperario = idoperario;
    }
    public String getOperario() {
        return operario;
    }
    public void setOperario(String operario) {
        this.operario = operario;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getEsadmin() {
        return esadmin;
    }
    public void setEsadmin(String esadmin) {
        this.esadmin = esadmin;
    }

}
