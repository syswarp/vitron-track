package com.syswarp.data.service;

import com.syswarp.data.entity.Medios;
import com.syswarp.data.entity.Operaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;
import java.time.LocalDate;
import java.util.List;

@Service
public class OperacionesService extends CrudService<Operaciones, Integer> {

    private OperacionesRepository repository;
    private ContenedoresRepository contenedoresRepository;
    private MediosRepository mediosRepository;
    @Autowired 
    public OperacionesService(OperacionesRepository repository,ContenedoresRepository contenedoresRepository, MediosRepository  mediosRepository) {
        this.repository = repository;
        this.contenedoresRepository = contenedoresRepository;
        this.mediosRepository = mediosRepository;
    }

    @Override
    protected OperacionesRepository getRepository() {
        return repository;
    }
    
	public List<Operaciones> findAll() {
		return getRepository().findAll();
	}

	public List<Operaciones> findAll(String stringFilter) {
		if (stringFilter == null || stringFilter.isEmpty()) {
			return getRepository().findAll();
		} else {
			return getRepository().search(stringFilter) ;
		}
	}

}
