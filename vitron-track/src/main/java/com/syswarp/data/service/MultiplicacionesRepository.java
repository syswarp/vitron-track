package com.syswarp.data.service;


import com.syswarp.data.entity.Multiplicaciones;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MultiplicacionesRepository extends JpaRepository<Multiplicaciones, Integer> {
	@Query("select c from Multiplicaciones c " +
		      "where lower(c.descripcion) like lower(concat('%', :searchTerm, '%') )  or cast( c.id as string ) = :searchTerm    ") // 


		    List<Multiplicaciones> search(@Param("searchTerm") String searchTerm); // 


}