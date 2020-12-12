package com.syswarp.data.entity;

import javax.persistence.Entity;

import com.syswarp.data.AbstractEntity;
import java.time.LocalDate;

@Entity
public class Operaciones extends AbstractEntity {

    private Integer idoperacion;
    private LocalDate fecha;
    private Integer idcontenedor;
    private Integer idvariedad;
    private Integer idoperario;
    private Integer idmultiplicacion;
    private Integer idmedio;
    private String observaciones;

    public Integer getIdoperacion() {
        return idoperacion;
    }
    public void setIdoperacion(Integer idoperacion) {
        this.idoperacion = idoperacion;
    }
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

}
