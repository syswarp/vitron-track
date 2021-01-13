package com.syswarp.data.service;


import com.syswarp.data.entity.Operaciones;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;  
 
import org.springframework.data.jpa.repository.Modifying;  
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OperacionesRepository extends JpaRepository<Operaciones, Integer> {
/*
	@Query("select c from Operaciones c  " +
		      "where lower(c.observaciones) like lower(concat('%', :searchTerm, '%') )  or cast( c.id as string ) = :searchTerm    ") // 
*/

	@Query("select c from Operaciones c "
			   + " INNER JOIN c.operarios o " +
			     " INNER JOIN c.variedades v " +
			     " INNER JOIN c.contenedores co " +
			     " INNER JOIN c.medios me " +
			     " INNER JOIN c.multiplicaciones mu " +
		   " where lower(c.observaciones) like lower(concat('%', :searchTerm, '%') )  "
		    +  " or lower(o.operario) like lower(concat('%', :searchTerm, '%') )  "
		    +  " or lower(v.variedad) like lower(concat('%', :searchTerm, '%') )  "
		    +  " or lower(co.contenedor) like lower(concat('%', :searchTerm, '%') )  "
		    +  " or lower(me.medio) like lower(concat('%', :searchTerm, '%') )  "
		    +  " or lower(mu.descripcion) like lower(concat('%', :searchTerm, '%') )  "
		    +  " or lower( cast (c.fecha as string )) like lower(concat('%', :searchTerm, '%') )  "
		    + " or cast( c.id as string ) = :searchTerm    ") // 

	
	List<Operaciones> search(@Param("searchTerm") String searchTerm); // 


}