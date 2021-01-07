package com.syswarp.data.service;

import com.syswarp.data.entity.Medios;
import com.syswarp.data.entity.Variedades;

import java.util.List;

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

    
    
	public List<Variedades> findAll() {
		return getRepository().findAll();
	}

	public List<Variedades> findAll(String stringFilter) {
		if (stringFilter == null || stringFilter.isEmpty()) {
			return getRepository().findAll();
		} else {
			return getRepository().search(stringFilter) ;
		}
	}

    
    
}
