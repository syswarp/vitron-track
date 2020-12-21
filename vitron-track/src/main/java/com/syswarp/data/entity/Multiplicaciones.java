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
@Table(name = "multiplicaciones")
public class Multiplicaciones extends AbstractEntity {

    //private Integer idmultiplicacion;
	// TODO: Ver somo se hace la constraint para llamarse a si misma
	
    private Integer idmultiplicacion_padre;
	private String usuarioalt;
    private String usuarioact;
    private String descripcion;
	private Date fechaalt;
    private Date fechaact;
    
    @OneToMany(mappedBy = "multiplicaciones", cascade = CascadeType.ALL)
    private Set<Multiplicaciones> multiplicaciones = new HashSet<>(); 
    
  
 	public Integer getIdmultiplicacion_padre() {
        return idmultiplicacion_padre;
    }
    public void setIdmultiplicacion_padre(Integer idmultiplicacion_padre) {
        this.idmultiplicacion_padre = idmultiplicacion_padre;
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

	   public Set<Multiplicaciones> getMultiplicaciones() {
			return multiplicaciones;
		}
		public void setMultiplicaciones(Set<Multiplicaciones> multiplicaciones) {
			this.multiplicaciones = multiplicaciones;
		}
	    public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

}
