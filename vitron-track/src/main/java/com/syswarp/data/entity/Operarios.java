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
@Table(name = "operarios")
public class Operarios extends AbstractEntity {

 //   private Integer idoperario;
    private String operario;
    private String usuario;
    private String clave;
    private String esadmin;
	private String usuarioalt;
    private String usuarioact;
    private Date fechaalt;
    private Date fechaact;
    
    @OneToMany(mappedBy = "operarios", cascade = CascadeType.ALL)
    private Set<Operarios> operarios = new HashSet<>(); 
    
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

    
	public Set<Operarios> getOperarios() {
		return operarios;
	}
	public void setOperarios(Set<Operarios> operarios) {
		this.operarios = operarios;
	}


    
}
