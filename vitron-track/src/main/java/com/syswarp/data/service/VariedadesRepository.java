package com.syswarp.data.service;

import com.syswarp.data.entity.Variedades;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VariedadesRepository extends JpaRepository<Variedades, Integer> {

	@Query("select c from Variedades c " +
		      "where lower(c.variedad) like lower(concat('%', :searchTerm, '%') )  or cast( c.id as string ) = :searchTerm    ") // 


		    List<Variedades> search(@Param("searchTerm") String searchTerm); // 


	
}