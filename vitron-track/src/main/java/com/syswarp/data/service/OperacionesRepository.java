package com.syswarp.data.service;

import com.syswarp.data.entity.Operaciones;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;  
 
import org.springframework.data.jpa.repository.Modifying;  
import org.springframework.data.jpa.repository.Query;

public interface OperacionesRepository extends JpaRepository<Operaciones, Integer> {

}