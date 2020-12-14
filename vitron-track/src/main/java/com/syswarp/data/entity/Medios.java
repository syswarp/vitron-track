package com.syswarp.data.entity;

import javax.persistence.Entity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import com.syswarp.data.AbstractEntity;

@Entity
public class Medios extends AbstractEntity {

   // private Integer idmedio;
    private String medio;
    
	private String usuarioalt;
    private String usuarioact;
    private Date fechaalt;
    private Date fechaact;
    
  //  TODO: Generar las contraints
    //private Set produccioneses = new HashSet(0);
    
    


    public String getMedio() {
        return medio;
    }
    public void setMedio(String medio) {
        this.medio = medio;
    }

    public String getUsuarioalt() {
		return usuarioalt;
	}
	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}
	public String getUsuarioact() {
		return usuarioact;
	}
	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}
	public Date getFechaalt() {
		return fechaalt;
	}
	public void setFechaalt(Date fechaalt) {
		this.fechaalt = fechaalt;
	}
	public Date getFechaact() {
		return fechaact;
	}
	public void setFechaact(Date fechaact) {
		this.fechaact = fechaact;
	}

    
    
}
