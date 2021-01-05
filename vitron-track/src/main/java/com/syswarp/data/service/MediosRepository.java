package com.syswarp.data.service;

import com.syswarp.data.entity.Medios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MediosRepository extends JpaRepository<Medios, Integer> {

	@Query("select c from Medios c " +
		      "where lower(c.medio) like lower(concat('%', :searchTerm, '%')) ") // 


		    List<Medios> search(@Param("searchTerm") String searchTerm); // 


}