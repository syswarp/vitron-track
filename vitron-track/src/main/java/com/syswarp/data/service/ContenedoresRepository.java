package com.syswarp.data.service;

import com.syswarp.data.entity.Contenedores;
import com.syswarp.data.entity.Medios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContenedoresRepository extends JpaRepository<Contenedores, Integer> {
	//List < Contenedores > findAll();
	@Query("select c from Contenedores c " +
		      "where lower(c.contenedor) like lower(concat('%', :searchTerm, '%') )  or cast( c.id as string ) = :searchTerm    ") // 


		    List<Contenedores> search(@Param("searchTerm") String searchTerm); // 


}