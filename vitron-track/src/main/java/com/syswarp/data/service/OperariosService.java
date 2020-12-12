package com.syswarp.data.service;

import com.syswarp.data.entity.Operarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class OperariosService extends CrudService<Operarios, Integer> {

    private OperariosRepository repository;

    public OperariosService(@Autowired OperariosRepository repository) {
        this.repository = repository;
    }

    @Override
    protected OperariosRepository getRepository() {
        return repository;
    }

}
