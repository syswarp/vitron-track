package com.syswarp.data.service;

import com.syswarp.data.entity.Contenedores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class ContenedoresService extends CrudService<Contenedores, Integer> {

    private ContenedoresRepository repository;

    public ContenedoresService(@Autowired ContenedoresRepository repository) {
        this.repository = repository;
    }

    @Override
    protected ContenedoresRepository getRepository() {
        return repository;
    }

}
