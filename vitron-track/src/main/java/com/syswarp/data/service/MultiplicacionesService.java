package com.syswarp.data.service;

import com.syswarp.data.entity.Medios;
import com.syswarp.data.entity.Multiplicaciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class MultiplicacionesService extends CrudService<Multiplicaciones, Integer> {

    private MultiplicacionesRepository repository;

    public MultiplicacionesService(@Autowired MultiplicacionesRepository repository) {
        this.repository = repository;
    }

    @Override
    protected MultiplicacionesRepository getRepository() {
        return repository;
    }
	public List<Multiplicaciones> findAll() {
		return getRepository().findAll();
	}

	public List<Multiplicaciones> findAll(String stringFilter) {
		if (stringFilter == null || stringFilter.isEmpty()) {
			return getRepository().findAll();
		} else {
			return getRepository().search(stringFilter) ;
		}
	}

}
