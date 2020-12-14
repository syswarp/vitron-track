package com.syswarp.data.entity;

import javax.persistence.Entity;

import com.syswarp.data.AbstractEntity;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Operaciones extends AbstractEntity {

   // private Integer idoperacion;
    private LocalDate fecha;
    private Integer idcontenedor;
    private Integer idvariedad;
    private Integer idoperario;
    private Integer idmultiplicacion;
    private Integer idmedio;
    private String observaciones;
// todo aca no van aparentemente metodos de constraint    
    private String usuarioalt;
	private String usuarioact;
    private Date fechaalt;
    private Date fechaact;


    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public Integer getIdcontenedor() {
        return idcontenedor;
    }
    public void setIdcontenedor(Integer idcontenedor) {
        this.idcontenedor = idcontenedor;
    }
    public Integer getIdvariedad() {
        return idvariedad;
    }
    public void setIdvariedad(Integer idvariedad) {
        this.idvariedad = idvariedad;
    }
    public Integer getIdoperario() {
        return idoperario;
    }
    public void setIdoperario(Integer idoperario) {
        this.idoperario = idoperario;
    }
    public Integer getIdmultiplicacion() {
        return idmultiplicacion;
    }
    public void setIdmultiplicacion(Integer idmultiplicacion) {
        this.idmultiplicacion = idmultiplicacion;
    }
    public Integer getIdmedio() {
        return idmedio;
    }
    public void setIdmedio(Integer idmedio) {
        this.idmedio = idmedio;
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

    
}
