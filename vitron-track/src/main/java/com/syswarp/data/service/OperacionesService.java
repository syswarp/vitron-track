package com.syswarp.data.service;

import com.syswarp.data.entity.Operaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import java.time.LocalDate;

@Service
public class OperacionesService extends CrudService<Operaciones, Integer> {

    private OperacionesRepository repository;

    public OperacionesService(@Autowired OperacionesRepository repository) {
        this.repository = repository;
    }

    @Override
    protected OperacionesRepository getRepository() {
        return repository;
    }

}
