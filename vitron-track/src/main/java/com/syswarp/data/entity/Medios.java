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
@Table(name = "medios")

public class Medios extends AbstractEntity {

   // private Integer idmedio;
    private String medio;
    
	private String usuarioalt;
    private String usuarioact;
    private Date fechaalt;
    private Date fechaact;
    
  //  TODO: Generar las contraints
    //private Set produccioneses = new HashSet(0);
    
    
    @OneToMany(mappedBy = "medios", cascade = CascadeType.ALL)
    private Set<Medios> medios = new HashSet<>(); 
 

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

    public Set<Medios> getMedios() {
		return medios;
	}
	public void setMedios(Set<Medios> medios) {
		this.medios = medios;
	}
    
    
}
