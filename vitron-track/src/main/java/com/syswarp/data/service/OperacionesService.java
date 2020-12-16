package com.syswarp.data.service;

import com.syswarp.data.entity.Operaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import java.time.LocalDate;

@Service
public class OperacionesService extends CrudService<Operaciones, Integer> {

    private OperacionesRepository repository;
    private ContenedoresRepository contenedoresRepository;
    @Autowired 
    public OperacionesService(OperacionesRepository repository,ContenedoresRepository contenedoresRepository ) {
        this.repository = repository;
        this.contenedoresRepository = contenedoresRepository;
    }

    @Override
    protected OperacionesRepository getRepository() {
        return repository;
    }

}
