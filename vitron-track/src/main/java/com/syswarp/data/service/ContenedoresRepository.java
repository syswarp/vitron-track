package com.syswarp.data.service;

import com.syswarp.data.entity.Contenedores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContenedoresRepository extends JpaRepository<Contenedores, Integer> {
	//List < Contenedores > findAll();

}