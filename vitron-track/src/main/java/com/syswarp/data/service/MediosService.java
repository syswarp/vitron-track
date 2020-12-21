package com.syswarp.data.service;

import com.syswarp.data.entity.Medios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class MediosService extends CrudService<Medios, Integer> {

    private MediosRepository repository;
    private OperacionesRepository  operacionesRepository;
   
    @Autowired
    public MediosService( MediosRepository repository, OperacionesRepository  operacionesRepository) {
        this.repository = repository;
        this.operacionesRepository = operacionesRepository;
    }

    @Override
    protected MediosRepository getRepository() {
        return repository;
    }

}
