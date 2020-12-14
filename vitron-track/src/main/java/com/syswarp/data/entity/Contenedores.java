package com.syswarp.data.entity;

import javax.persistence.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.syswarp.data.AbstractEntity;

@Entity
public class Contenedores extends AbstractEntity {

    //private Integer idcontenedor;
    private String contenedor;
    private String tagid;
   
    private String usuarioalt;
    private String usuarioact;

    private Date fechaalt;
    private Date fechaact;

    
   // TODO: Las contraints 
   // private Set operacioneses = new HashSet(0);

    


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
