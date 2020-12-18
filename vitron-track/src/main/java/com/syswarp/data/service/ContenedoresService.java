package com.syswarp.data.service;

import com.syswarp.data.entity.Contenedores;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;


//import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class ContenedoresService extends CrudService<Contenedores, Integer> {

    private ContenedoresRepository repository;
    private OperacionesRepository  operacionesRepository;
    protected EntityManager em;
    @Autowired
    public ContenedoresService( ContenedoresRepository repository,OperacionesRepository  operacionesRepository ) {
        this.repository = repository;
        this.operacionesRepository = operacionesRepository;
    }

    @Override
    protected ContenedoresRepository getRepository() {
        return repository;
    }
    
   
   

}
