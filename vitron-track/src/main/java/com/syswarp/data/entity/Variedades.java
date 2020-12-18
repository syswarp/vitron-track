package com.syswarp.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.syswarp.data.AbstractEntity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "variedades")
public class Variedades extends AbstractEntity {

    //private Integer idvariedad;
    private String variedad;


	private String usuarioalt;
    private String usuarioact;
    private Date fechaalt;
    private Date fechaact;

    @OneToMany(mappedBy = "variedades", cascade = CascadeType.ALL)
    private Set<Variedades> variedades = new HashSet<>(); 
    

    
	public String getVariedad() {
        return variedad;
    }
    public void setVariedad(String variedad) {
        this.variedad = variedad;
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
    
    public Set<Variedades> getVariedades() {
		return variedades;
	}
	public void setVariedades(Set<Variedades> variedades) {
		this.variedades = variedades;
	}



}
