package com.syswarp.data.service;

import com.syswarp.data.entity.Variedades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class VariedadesService extends CrudService<Variedades, Integer> {

    private VariedadesRepository repository;

    public VariedadesService(@Autowired VariedadesRepository repository) {
        this.repository = repository;
    }

    @Override
    protected VariedadesRepository getRepository() {
        return repository;
    }

}
