package com.syswarp.data.service;

import com.syswarp.data.entity.Medios;
import com.syswarp.data.entity.Operarios;

import java.util.List;

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
    
    
	public List<Operarios> findAll() {
		return getRepository().findAll();
	}

	public List<Operarios> findAll(String stringFilter) {
		if (stringFilter == null || stringFilter.isEmpty()) {
			return getRepository().findAll();
		} else {
			return getRepository().search(stringFilter) ;
		}
	}


	public List<Operarios> findByUsuario(String userName, String clave) {
		return getRepository().findUser(userName, clave);
	}

	
}
