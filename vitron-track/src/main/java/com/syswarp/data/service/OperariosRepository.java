package com.syswarp.data.service;


import com.syswarp.data.entity.Medios;
import com.syswarp.data.entity.Operarios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OperariosRepository extends JpaRepository<Operarios, Integer> {
	@Query("select c from Operarios c " +
		      "where lower(c.operario) like lower(concat('%', :searchTerm, '%') )  or cast( c.id as string ) = :searchTerm    ") // 

	List<Operarios> search(@Param("searchTerm") String searchTerm); // 
 
	@Query("select c from Operarios c " +
		      "where lower(c.usuario)  = :usuario  and  lower(c.clave)  = :clave ") // 

	List<Operarios> findUser(@Param("usuario") String usuario, @Param("clave") String clave); // 

	

}