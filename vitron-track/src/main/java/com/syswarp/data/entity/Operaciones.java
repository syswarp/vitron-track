package com.syswarp.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syswarp.data.AbstractEntity;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "operaciones")
public class Operaciones extends AbstractEntity {

   // private Integer idoperacion;
    private LocalDate fecha;
   // private Integer idcontenedor;
   // private Integer idvariedad;
    //private Integer idoperario;
    //private Integer idmultiplicacion;
//    private Integer idmedio;
    private String observaciones;
// todo aca no van aparentemente metodos de constraint    
    private String usuarioalt;
	private String usuarioact;
    private Date fechaalt;
    private Date fechaact;
 
    
    // contenedores
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idcontenedor")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Contenedores contenedores;
   
    // medios
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idmedio")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Medios medios;
    
    
    // Operarios
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idoperario")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Operarios operarios;
    
    // Variedades
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idvariedad")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Variedades variedades;

    // Multiplicaciones
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idmultiplicacion")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Multiplicaciones multiplicaciones;

    
    
	public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public Operarios getOperarios() {
		return operarios;
	}
	public void setOperarios(Operarios operarios) {
		this.operarios = operarios;
	}
	public Medios getMedios() {
		return medios;
	}
	public void setMedios(Medios medios) {
		this.medios = medios;
	}
	public Contenedores getContenedores() {
		return contenedores;
	
	}
	public void setContenedores(Contenedores contenedores) {
		this.contenedores = contenedores;
	}
	public Variedades getVariedades() {
		return variedades;
	}
	public void setVariedades(Variedades variedades) {
		this.variedades = variedades;
	}
	public Multiplicaciones getMultiplicaciones() {
		return multiplicaciones;
	}
	public void setMultiplicaciones(Multiplicaciones multiplicaciones) {
		this.multiplicaciones = multiplicaciones;
	}

	
}
