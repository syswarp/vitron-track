package com.syswarp.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.syswarp.data.AbstractEntity;

@Entity
@Table(name = "contenedores")
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

    @OneToMany(mappedBy = "contenedores", cascade = CascadeType.ALL)
    private Set<Contenedores> contenedores = new HashSet<>(); 
    
    


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

	
    public Set<Contenedores> getContenedores() {
		return contenedores;
	}
	public void setContenedores(Set<Contenedores> contenedores) {
		this.contenedores = contenedores;
	}

	
}
