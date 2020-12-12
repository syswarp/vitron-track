package com.syswarp.data.service;

import com.syswarp.data.entity.Medios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class MediosService extends CrudService<Medios, Integer> {

    private MediosRepository repository;

    public MediosService(@Autowired MediosRepository repository) {
        this.repository = repository;
    }

    @Override
    protected MediosRepository getRepository() {
        return repository;
    }

}
