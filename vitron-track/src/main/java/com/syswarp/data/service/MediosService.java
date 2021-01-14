package com.syswarp.data.service;

import com.syswarp.data.entity.Medios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class MediosService extends CrudService<Medios, Integer> {

	private MediosRepository repository;
	private OperacionesRepository operacionesRepository;

	@Autowired
	public MediosService(MediosRepository repository, OperacionesRepository operacionesRepository) {
		this.repository = repository;
		this.operacionesRepository = operacionesRepository;
	}

	@Override
	protected MediosRepository getRepository() {
		return repository;
	}

	public List<Medios> findAll() {
		return getRepository().findAll();
	}

	public List<Medios> findAll(String stringFilter) {
		if (stringFilter == null || stringFilter.isEmpty()) {
			return getRepository().findAll();
		} else {
			return getRepository().search(stringFilter) ;
		}
	}
	
	public Optional<Medios> findById(Integer id) {
			return getRepository().findById(id)  ;
	}
	

}
